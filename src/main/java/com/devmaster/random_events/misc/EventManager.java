package com.devmaster.random_events.misc;

import com.devmaster.random_events.config.REConfig;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;

public class EventManager {
    private int tickCounter = 0;
    private boolean warningGiven = false;
    private final Random random = new Random();
    private final Map<UUID, List<Timer>> activeTimers = new HashMap<>();
    private final Map<BlockPos, PendingContainer> pendingContainerData = new HashMap<>();
    private static final Map<UUID, ServerWorld> trackedEndermanWorlds = new HashMap<>();
    private static final Map<UUID, BlockState>  endermanLastCarried   = new HashMap<>();

    public static void trackEnderman(EndermanEntity enderman, ServerWorld world) {
        trackedEndermanWorlds.put(enderman.getUniqueID(), world);
        endermanLastCarried.put(enderman.getUniqueID(), enderman.getHeldBlockState());
    }

    public void trackTimer(ServerPlayerEntity player, Timer timer) {
        activeTimers.computeIfAbsent(player.getUniqueID(), k -> new ArrayList<>()).add(timer);
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntityLiving() instanceof ServerPlayerEntity)) return;

        UUID deadPlayer = event.getEntityLiving().getUniqueID();
        List<Timer> timers = activeTimers.remove(deadPlayer);
        if (timers != null) {
            timers.forEach(Timer::cancel);
        }
    }

    @SubscribeEvent
    public void onEndermanDrop(LivingDropsEvent event) {
        if (!(event.getEntityLiving() instanceof EndermanEntity)) return;

        EndermanEntity enderman = (EndermanEntity) event.getEntityLiving();
        CompoundNBT data = enderman.getPersistentData();

        if (!data.contains("FilledContainer")) return;

        ItemStack container = ItemStack.read(data.getCompound("FilledContainer"));
        if (container.isEmpty()) return;

        // Remove the vanilla empty block drop before adding our filled one
        event.getDrops().removeIf(drop -> {
            ItemStack stack = drop.getItem();
            return stack.getItem() == container.getItem();
        });

        event.getDrops().add(new ItemEntity(
                enderman.world,
                enderman.getPosX(),
                enderman.getPosY(),
                enderman.getPosZ(),
                container
        ));
    }

/*** Searches outward from the enderman's position to find the block they
 * just placed.  PlaceBlockGoal puts the block within ~2 blocks of the
 * enderman's feet*/
private static BlockPos findPlacedBlock(ServerWorld world, EndermanEntity enderman, Block target) {
    BlockPos origin = enderman.getPosition();
    for (int radius = 0; radius <= 3; radius++) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {
                    BlockPos pos = origin.add(x, y, z);
                    if (world.getBlockState(pos).getBlock() == target) {
                        return pos;
                    }
                }
            }
        }
    }
    return null; // shouldn't happen, but handled gracefully by the caller
}

    private static class PendingContainer {
        final ServerWorld world;
        final CompoundNBT nbt;

        PendingContainer(ServerWorld world, CompoundNBT nbt) {
            this.world = world;
            this.nbt = nbt;
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.side != LogicalSide.SERVER || event.phase != TickEvent.Phase.END) {
            return;
        }

        tickCounter++;

        int eventInterval = REConfig.CONFIG.eventIntervalMinutes.get() * 60 * 20; // Convert to ticks
        int warningTime = REConfig.CONFIG.warningTimeSeconds.get() * 20;
        int warningTick = eventInterval - warningTime;

        // Give warning
        if (tickCounter >= warningTick && !warningGiven) {
            warningGiven = true;
            for (ServerPlayerEntity player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                player.sendMessage(new StringTextComponent(TextFormatting.RED + "A random event will happen in " + REConfig.CONFIG.warningTimeSeconds.get() + " seconds!"), player.getUniqueID());
            }
        }

        // Trigger event
        if (tickCounter >= eventInterval) {
            tickCounter = 0;
            warningGiven = false;

            List<ServerPlayerEntity> players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers();
            if (!players.isEmpty()) {
                ServerPlayerEntity targetPlayer = players.get(random.nextInt(players.size()));
                triggerRandomEvent(targetPlayer);
            }
        }
        if (!trackedEndermanWorlds.isEmpty()) {
            Iterator<Map.Entry<UUID, ServerWorld>> it = trackedEndermanWorlds.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<UUID, ServerWorld> entry = it.next();
                UUID        uuid  = entry.getKey();
                ServerWorld world = entry.getValue();

                Entity entity = world.getEntityByUuid(uuid);

                // Gone or dead — the drop handler covers the item drop case
                if (entity == null || !entity.isAlive()) {
                    it.remove();
                    endermanLastCarried.remove(uuid);
                    continue;
                }

                EndermanEntity enderman    = (EndermanEntity) entity;
                BlockState lastCarried = endermanLastCarried.get(uuid);
                BlockState     nowCarried  = enderman.getHeldBlockState();

                if (lastCarried != null && nowCarried == null) {
                    // The enderman just placed its block this tick
                    CompoundNBT data = enderman.getPersistentData();
                    if (data.contains("FilledContainer")) {
                        ItemStack container = ItemStack.read(data.getCompound("FilledContainer"));
                        if (!container.isEmpty() && container.hasTag()
                                && container.getTag().contains("BlockEntityTag")) {

                            BlockPos placed = findPlacedBlock(world, enderman, lastCarried.getBlock());
                            if (placed != null) {
                                CompoundNBT bet = container.getTag().getCompound("BlockEntityTag").copy();
                                bet.putInt("x", placed.getX());
                                bet.putInt("y", placed.getY());
                                bet.putInt("z", placed.getZ());
                                // Queue for next tick so the TileEntity is fully created
                                pendingContainerData.put(placed, new PendingContainer(world, bet));
                                // NOTE: we intentionally keep "FilledContainer" in persistent data
                                // so onEndermanDrop still works if this enderman is killed later
                            }
                        }
                    }
                    it.remove();
                    endermanLastCarried.remove(uuid);
                    continue;
                }

                // Update last-known carried state for next tick comparison
                endermanLastCarried.put(uuid, nowCarried);
            }
        }

        // ── Apply deferred TileEntity data ────────────────────────────────────
        if (!pendingContainerData.isEmpty()) {
            Iterator<Map.Entry<BlockPos, PendingContainer>> it = pendingContainerData.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<BlockPos, PendingContainer> entry = it.next();
                PendingContainer pending = entry.getValue();
                TileEntity te = pending.world.getTileEntity(entry.getKey());
                if (te != null) {
                    te.read(pending.world.getBlockState(entry.getKey()), pending.nbt);
                    te.markDirty();
                    it.remove();
                }
            }
        }

        if (!pendingContainerData.isEmpty()) {
            Iterator<Map.Entry<BlockPos, PendingContainer>> it = pendingContainerData.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<BlockPos, PendingContainer> entry = it.next();
                PendingContainer pending = entry.getValue();
                TileEntity te = pending.world.getTileEntity(entry.getKey());
                if (te != null) {
                    te.read(pending.world.getBlockState(entry.getKey()), pending.nbt);
                    te.markDirty();
                    it.remove();
                }
            }
        }
    }

    private void triggerRandomEvent(ServerPlayerEntity player) {
        List<RandomEvents.EventType> enabledEvents = getEnabledEvents();
        
        if (enabledEvents.isEmpty()) {
            return;
        }
        
        RandomEvents.EventType eventType = enabledEvents.get(random.nextInt(enabledEvents.size()));
        RandomEvents.executeEvent(eventType, player, this);
    }
    
    private List<RandomEvents.EventType> getEnabledEvents() {
        List<RandomEvents.EventType> events = new ArrayList<>();
        
        if (REConfig.CONFIG.arrowRainEnabled.get())
            events.add(RandomEvents.EventType.ARROW_RAIN);
        if (REConfig.CONFIG.mlgTeleportEnabled.get())
            events.add(RandomEvents.EventType.MLG_TELEPORT);
        if (REConfig.CONFIG.timeChangeEnabled.get())
            events.add(RandomEvents.EventType.TIME_CHANGE);
        if (REConfig.CONFIG.snowParticlesEnabled.get())
            events.add(RandomEvents.EventType.SNOW_PARTICLES);
        if (REConfig.CONFIG.valuableLootRainEnabled.get())
            events.add(RandomEvents.EventType.VALUABLE_LOOT_RAIN);
        if (REConfig.CONFIG.foodRainEnabled.get())
            events.add(RandomEvents.EventType.FOOD_RAIN);
        if (REConfig.CONFIG.diamondSwordEnabled.get())
            events.add(RandomEvents.EventType.DIAMOND_SWORD);
        if (REConfig.CONFIG.xpRainEnabled.get())
            events.add(RandomEvents.EventType.XP_RAIN);
        if (REConfig.CONFIG.chickenRainEnabled.get())
            events.add(RandomEvents.EventType.CHICKEN_RAIN);
        if (REConfig.CONFIG.hotbarShuffleEnabled.get())
            events.add(RandomEvents.EventType.HOTBAR_SHUFFLE);
        if (REConfig.CONFIG.pumpkinHeadEnabled.get())
            events.add(RandomEvents.EventType.PUMPKIN_HEAD);
        if (REConfig.CONFIG.randomTeleportEnabled.get())
            events.add(RandomEvents.EventType.RANDOM_TELEPORT);
        if (REConfig.CONFIG.thunderstrikesEnabled.get())
            events.add(RandomEvents.EventType.THUNDERSTRIKES);
        if (REConfig.CONFIG.tntFeetEnabled.get())
            events.add(RandomEvents.EventType.TNT_FEET);
        if (REConfig.CONFIG.endermanSpawnEnabled.get())
            events.add(RandomEvents.EventType.ENDERMAN_SPAWN);
        if (REConfig.CONFIG.waterSurroundEnabled.get())
            events.add(RandomEvents.EventType.WATER_SURROUND);
        if (REConfig.CONFIG.fireUnderFeetEnabled.get())
            events.add(RandomEvents.EventType.FIRE_UNDER_FEET);
        if (REConfig.CONFIG.blindnessTeleportEnabled.get())
            events.add(RandomEvents.EventType.BLINDNESS_TELEPORT);
        if (REConfig.CONFIG.lavaSurroundEnabled.get())
            events.add(RandomEvents.EventType.LAVA_SURROUND);
        if (REConfig.CONFIG.anvilRainEnabled.get())
            events.add(RandomEvents.EventType.ANVIL_RAIN);
        if (REConfig.CONFIG.skeletonSpawnEnabled.get())
            events.add(RandomEvents.EventType.SKELETON_SPAWN);
        if (REConfig.CONFIG.lavaUnderFeetEnabled.get())
            events.add(RandomEvents.EventType.LAVA_UNDER_FEET);
        if (REConfig.CONFIG.phantomSpawnEnabled.get())
            events.add(RandomEvents.EventType.PHANTOM_SPAWN);
        if (REConfig.CONFIG.babyZombieSpawnEnabled.get())
            events.add(RandomEvents.EventType.BABY_ZOMBIE_SPAWN);
        if (REConfig.CONFIG.blindnessEnabled.get())
            events.add(RandomEvents.EventType.BLINDNESS);
        if (REConfig.CONFIG.chargedCreeperSpawnEnabled.get())
            events.add(RandomEvents.EventType.CHARGED_CREEPER_SPAWN);
        if (REConfig.CONFIG.miningFatigueEnabled.get())
            events.add(RandomEvents.EventType.MINING_FATIGUE);
        if (REConfig.CONFIG.levitationSlowfallEnabled.get())
            events.add(RandomEvents.EventType.LEVITATION_SLOWFALL);
        if (REConfig.CONFIG.longLevitationEnabled.get())
            events.add(RandomEvents.EventType.LONG_LEVITATION);
        if (REConfig.CONFIG.iceUnderFeetEnabled.get())
            events.add(RandomEvents.EventType.ICE_UNDER_FEET);
        if (REConfig.CONFIG.witherSpawnEnabled.get())
            events.add(RandomEvents.EventType.WITHER_SPAWN);
        if (REConfig.CONFIG.groundBreakEnabled.get())
            events.add(RandomEvents.EventType.GROUND_BREAK);
        if (REConfig.CONFIG.tntRainEnabled.get())
            events.add(RandomEvents.EventType.TNT_RAIN);
        if (REConfig.CONFIG.waveAttackEnabled.get())
            events.add(RandomEvents.EventType.WAVE_ATTACK);
        if (REConfig.CONFIG.harmPotionRainEnabled.get())
            events.add(RandomEvents.EventType.HARM_POTION_RAIN);
        
        return events;
    }
}