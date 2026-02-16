package com.devmaster.random_events.misc;

import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.BlastingRecipe;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.SmokingRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.BlastFurnaceTileEntity;
import net.minecraft.tileentity.SmokerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

public class RandomEvents {

    public enum EventType {
        ARROW_RAIN,
        MLG_TELEPORT,
        TIME_CHANGE,
        SNOW_PARTICLES,
        VALUABLE_LOOT_RAIN,
        FOOD_RAIN,
        DIAMOND_SWORD,
        XP_RAIN,
        CHICKEN_RAIN,
        HOTBAR_SHUFFLE,
        PUMPKIN_HEAD,
        RANDOM_TELEPORT,
        THUNDERSTRIKES,
        TNT_FEET,
        ENDERMAN_SPAWN,
        WATER_SURROUND,
        FIRE_UNDER_FEET,
        BLINDNESS_TELEPORT,
        LAVA_SURROUND,
        ANVIL_RAIN,
        SKELETON_SPAWN,
        LAVA_UNDER_FEET,
        PHANTOM_SPAWN,
        BABY_ZOMBIE_SPAWN,
        BLINDNESS,
        CHARGED_CREEPER_SPAWN,
        MINING_FATIGUE,
        LEVITATION_SLOWFALL,
        LONG_LEVITATION,
        ICE_UNDER_FEET,
        WITHER_SPAWN,
        GROUND_BREAK,
        TNT_RAIN,
        WAVE_ATTACK,
        HARM_POTION_RAIN
    }

    private static final Random rand = new Random();

    public static void executeEvent(EventType eventType, ServerPlayerEntity player, EventManager manager) {
        ServerWorld world = (ServerWorld) player.world;

        switch (eventType) {
            case ARROW_RAIN:            arrowRain(player, world );          break;
            case MLG_TELEPORT:          mlgTeleport(player, world);         break;
            case TIME_CHANGE:           timeChange(player, world);          break;
            case SNOW_PARTICLES:        snowParticles(player, world);       break;
            case VALUABLE_LOOT_RAIN:    valuableLootRain(player, world);    break;
            case FOOD_RAIN:             foodRain(player, world);            break;
            case DIAMOND_SWORD:         diamondSword(player, world);        break;
            case XP_RAIN:               xpRain(player, world);              break;
            case CHICKEN_RAIN:          chickenRain(player, world);         break;
            case HOTBAR_SHUFFLE:        hotbarShuffle(player);              break;
            case PUMPKIN_HEAD:          pumpkinHead(player);                break;
            case RANDOM_TELEPORT:       randomTeleport(player);             break;
            case THUNDERSTRIKES:        thunderstrikes(player, world);      break;
            case TNT_FEET:              tntFeet(player, world, manager);    break;
            case ENDERMAN_SPAWN:        endermanSpawn(player, world);       break;
            case WATER_SURROUND:        waterSurround(player, world);       break;
            case FIRE_UNDER_FEET:       fireUnderFeet(player, world, manager); break;
            case BLINDNESS_TELEPORT:    blindnessTeleport(player);          break;
            case LAVA_SURROUND:         lavaSurround(player, world);        break;
            case ANVIL_RAIN:            anvilRain(player, world);           break;
            case SKELETON_SPAWN:        skeletonSpawn(player, world);       break;
            case LAVA_UNDER_FEET:       lavaUnderFeet(player, world);       break;
            case PHANTOM_SPAWN:         phantomSpawn(player, world);        break;
            case BABY_ZOMBIE_SPAWN:     babyZombieSpawn(player, world);     break;
            case BLINDNESS:             blindness(player);                  break;
            case CHARGED_CREEPER_SPAWN: chargedcreeperSpawn(player, world); break;
            case MINING_FATIGUE:        miningFatigue(player);              break;
            case LEVITATION_SLOWFALL:   levitationSlowfall(player);         break;
            case LONG_LEVITATION:       longLevitation(player);             break;
            case ICE_UNDER_FEET:        iceUnderFeet(player, world);        break;
            case WITHER_SPAWN:          witherSpawn(player, world);         break;
            case GROUND_BREAK:          groundBreak(player, world);         break;
            case TNT_RAIN:              tntRain(player, world);             break;
            case WAVE_ATTACK:           waveAttack(player, world);          break;
            case HARM_POTION_RAIN:      harmPotionRain(player, world);      break;
        }
    }

    // ==================== EVENT IMPLEMENTATIONS ====================

    private static void arrowRain(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.YELLOW + "Arrow Rain!"), player.getUniqueID());
        int arrowCount = 10 + rand.nextInt(20);

        for (int i = 0; i < arrowCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 10;
            double offsetZ = (rand.nextDouble() - 0.5) * 10;

            ArrowEntity arrow = new ArrowEntity(world, player.getPosX() + offsetX, player.getPosY() + 20, player.getPosZ() + offsetZ);
            arrow.setDamage(2.0);
            world.addEntity(arrow);
        }

        int lightningCount = 1 + rand.nextInt(3);
        for (int i = 0; i < lightningCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 15;
            double offsetZ = (rand.nextDouble() - 0.5) * 15;

            LightningBoltEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
            lightning.setPosition(player.getPosX() + offsetX, player.getPosY(), player.getPosZ() + offsetZ);
            world.addEntity(lightning);
        }
    }

    private static void mlgTeleport(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.RED + "MLG or die!"), player.getUniqueID());
        player.setPositionAndUpdate(player.getPosX(), player.getPosY() + 100, player.getPosZ());

        ItemStack waterBucket = new ItemStack(Items.WATER_BUCKET);
        if (!player.inventory.addItemStackToInventory(waterBucket)) {
            ItemEntity itemEntity = new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), waterBucket);
            world.addEntity(itemEntity);
        }
    }

    private static void timeChange(ServerPlayerEntity player, ServerWorld world) {
        long currentTime = world.getDayTime() % 24000;

        if (currentTime < 12000) {
            world.setDayTime((world.getDayTime() / 24000) * 24000 + 13000);
            player.sendMessage(new StringTextComponent(TextFormatting.DARK_BLUE + "Night falls..."), player.getUniqueID());
        } else {
            world.setDayTime((world.getDayTime() / 24000 + 1) * 24000);
            player.sendMessage(new StringTextComponent(TextFormatting.GOLD + "Dawn breaks..."), player.getUniqueID());
        }
        world.setWeather(0, 6000, true, true);
    }

    private static void snowParticles(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.WHITE + "Let it snow!"), player.getUniqueID());

        for (int x = -5; x <= 5; x++) {
            for (int z = -5; z <= 5; z++) {
                BlockPos pos = new BlockPos(player.getPosX() + x, player.getPosY(), player.getPosZ() + z);
                BlockPos below = pos.down();

                if (world.getBlockState(below).getMaterial().isSolid() && world.isAirBlock(pos)) {
                    world.setBlockState(pos, Blocks.SNOW.getDefaultState(), 3);

                    // Spawn particles at this exact block as it's placed
                    world.spawnParticle(ParticleTypes.ITEM_SNOWBALL, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, 10, // count
                            0.3,  // xOffset
                            0.3,  // yOffset
                            0.3,  // zOffset
                            0.05  // speed
                    );
                }
            }
        }
    }

    private static void valuableLootRain(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.AQUA + "Valuable loot is raining!"), player.getUniqueID());

        List<ItemStack> valuables = new ArrayList<>();

        net.minecraft.tags.ITag<net.minecraft.item.Item> gemsTag = ItemTags.createOptional(new ResourceLocation("forge", "gems"));
        net.minecraft.tags.ITag<net.minecraft.item.Item> ingotsTag = ItemTags.createOptional(new ResourceLocation("forge", "ingots"));
        net.minecraft.tags.ITag<net.minecraft.item.Item> nuggetsTag = ItemTags.createOptional(new ResourceLocation("forge", "nuggets"));
        net.minecraft.tags.ITag<net.minecraft.item.Item> blocksTag = ItemTags.createOptional(new ResourceLocation("forge", "storage_blocks"));

        gemsTag.getAllElements().forEach(item -> {
            valuables.add(new ItemStack(item, 1 + rand.nextInt(3)));

        });

        ingotsTag.getAllElements().forEach(item -> {
            valuables.add(new ItemStack(item, 1 + rand.nextInt(5)));
        });

        nuggetsTag.getAllElements().forEach(item -> {
            valuables.add(new ItemStack(item, 1 + rand.nextInt(3)));

        });

        blocksTag.getAllElements().forEach(item -> {
            valuables.add(new ItemStack(item, 1 + rand.nextInt(3)));

        });

        int lootCount = 5 + rand.nextInt(10);
        for (int i = 0; i < lootCount; i++) {
            ItemStack loot = valuables.get(rand.nextInt(valuables.size())).copy();
            double offsetX = (rand.nextDouble() - 0.5) * 8;
            double offsetZ = (rand.nextDouble() - 0.5) * 8;

            ItemEntity itemEntity = new ItemEntity(world, player.getPosX() + offsetX, player.getPosY() + 15, player.getPosZ() + offsetZ, loot);
            world.addEntity(itemEntity);
        }
    }

    private static void foodRain(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Food is falling from the sky!"), player.getUniqueID());

        List<ItemStack> foods = new ArrayList<>();
        foods.add(new ItemStack(Items.BAKED_POTATO));
        foods.add(new ItemStack(Items.GOLDEN_APPLE));
        foods.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));

        // Only items with "cooked" in their registry name
        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            String itemName = item.getRegistryName().toString().toLowerCase();
            if (item.isFood() && itemName.contains("cooked")) {
                foods.add(new ItemStack(item));
            }
        });

        int foodCount = 10 + rand.nextInt(15);
        for (int i = 0; i < foodCount; i++) {
            ItemStack food = foods.get(rand.nextInt(foods.size())).copy();
            double offsetX = (rand.nextDouble() - 0.5) * 8;
            double offsetZ = (rand.nextDouble() - 0.5) * 8;

            ItemEntity itemEntity = new ItemEntity(world, player.getPosX() + offsetX, player.getPosY() + 15, player.getPosZ() + offsetZ, food);
            world.addEntity(itemEntity);
        }
    }

    private static void diamondSword(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.AQUA + "A legendary sword appears!"), player.getUniqueID());

        ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
        sword.addEnchantment(Enchantments.SHARPNESS, 50);

        if (!player.inventory.addItemStackToInventory(sword)) {
            ItemEntity itemEntity = new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), sword);
            world.addEntity(itemEntity);
        }
    }

    private static void xpRain(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.GREEN + "Experience is raining!"), player.getUniqueID());

        int orbCount = 20 + rand.nextInt(30);
        for (int i = 0; i < orbCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 10;
            double offsetZ = (rand.nextDouble() - 0.5) * 10;

            ExperienceOrbEntity orb = new ExperienceOrbEntity(world,
                    player.getPosX() + offsetX, player.getPosY() + 15, player.getPosZ() + offsetZ,
                    5 + rand.nextInt(20));
            world.addEntity(orb);
        }
    }

    private static void chickenRain(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.YELLOW + "The chickens are falling!"), player.getUniqueID());

        int chickenCount = 10 + rand.nextInt(20);
        for (int i = 0; i < chickenCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 10;
            double offsetZ = (rand.nextDouble() - 0.5) * 10;

            ChickenEntity chicken = EntityType.CHICKEN.create(world);
            chicken.setPosition(player.getPosX() + offsetX, player.getPosY() + 20, player.getPosZ() + offsetZ);
            world.addEntity(chicken);
        }
    }

    private static void hotbarShuffle(ServerPlayerEntity player) {
        player.sendMessage(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Your hotbar has been shuffled!"), player.getUniqueID());

        List<ItemStack> hotbarItems = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            hotbarItems.add(player.inventory.getStackInSlot(i).copy());
        }

        Collections.shuffle(hotbarItems);

        for (int i = 0; i < 9; i++) {
            player.inventory.setInventorySlotContents(i, hotbarItems.get(i));
        }
    }

    private static void pumpkinHead(ServerPlayerEntity player) {
        player.sendMessage(new StringTextComponent(TextFormatting.GOLD + "You've been pumpkin'd!"), player.getUniqueID());
        player.setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Items.CARVED_PUMPKIN));
    }

    private static void randomTeleport(ServerPlayerEntity player) {
        player.sendMessage(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Teleporting!"), player.getUniqueID());

        double newX = player.getPosX() + (rand.nextDouble() - 0.5) * 200;
        double newZ = player.getPosZ() + (rand.nextDouble() - 0.5) * 200;
        double newY = player.world.getHeight(Heightmap.Type.MOTION_BLOCKING, (int) newX, (int) newZ);

        player.setPositionAndUpdate(newX, newY, newZ);
    }

    private static void thunderstrikes(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.YELLOW + "Thunder strikes!"), player.getUniqueID());

        int strikeCount = 5 + rand.nextInt(10);
        for (int i = 0; i < strikeCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 20;
            double offsetZ = (rand.nextDouble() - 0.5) * 20;

            LightningBoltEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
            lightning.setPosition(player.getPosX() + offsetX, player.getPosY(), player.getPosZ() + offsetZ);
            world.addEntity(lightning);
        }
    }

    private static void tntFeet(ServerPlayerEntity player, ServerWorld world, EventManager manager) {
        player.sendMessage(new StringTextComponent(TextFormatting.RED + "TNT beneath your feet!"), player.getUniqueID());

        Timer timer = new Timer();
        manager.trackTimer(player, timer);  // register it before scheduling
        timer.scheduleAtFixedRate(new TimerTask() {
            int ticks = 0;
            @Override
            public void run() {
                if (ticks >= 100) {
                    cancel();
                    return;
                }

                BlockPos pos = new BlockPos(player.getPosX(), player.getPosY() - 1, player.getPosZ());
                world.setBlockState(pos, Blocks.TNT.getDefaultState(), 3);
                TNTEntity tnt = new TNTEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, player);
                tnt.setFuse(40);
                world.addEntity(tnt);
                ticks++;
            }
        }, 0, 50);
    }

    private static void endermanSpawn(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.DARK_PURPLE + "Endermen are watching..."), player.getUniqueID());

        int endermanCount = 3 + rand.nextInt(7);
        List<net.minecraft.block.Block> possibleBlocks = new ArrayList<>();
        ForgeRegistries.BLOCKS.getValues().forEach(block -> {
            if (block.getDefaultState().getMaterial().isSolid()) {
                possibleBlocks.add(block);
            }
        });

        for (int i = 0; i < endermanCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 10;
            double offsetZ = (rand.nextDouble() - 0.5) * 10;

            EndermanEntity enderman = EntityType.ENDERMAN.create(world);
            enderman.setPosition(player.getPosX() + offsetX, player.getPosY(), player.getPosZ() + offsetZ);

            if (!possibleBlocks.isEmpty()) {
                net.minecraft.block.Block block = possibleBlocks.get(rand.nextInt(possibleBlocks.size()));
                enderman.setHeldBlockState(block.getDefaultState());

                // Store filled container in persistent data for both drop logic AND placement logic
                if (isContainer(block)) {
                    ItemStack filled = createFilledContainer(block, world);
                    enderman.getPersistentData().put("FilledContainer", filled.write(new CompoundNBT()));
                } else if (block == Blocks.SPAWNER) {
                    ItemStack filled = createFilledSpawner();
                    enderman.getPersistentData().put("FilledContainer", filled.write(new CompoundNBT()));
                }

                world.addEntity(enderman);

                if (enderman.getPersistentData().contains("FilledContainer")) {
                    EventManager.trackEnderman(enderman, world);
                }
            } else {
                world.addEntity(enderman);
            }
        }
    }

    private static void waterSurround(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.BLUE + "Water surrounds you!"), player.getUniqueID());

        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                if (Math.abs(x) == 3 || Math.abs(z) == 3) {
                    for (int y = 0; y <= 2; y++) {
                        BlockPos pos = new BlockPos(player.getPosX() + x, player.getPosY() + y, player.getPosZ() + z);
                        if (world.isAirBlock(pos)) {
                            world.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
                        }
                    }
                }
            }
        }
    }

    private static void fireUnderFeet(ServerPlayerEntity player, ServerWorld world, EventManager manager) {
        player.sendMessage(new StringTextComponent(TextFormatting.RED + "The floor is lava... well, fire!"), player.getUniqueID());

        Timer timer = new Timer();
        manager.trackTimer(player, timer);  // register it before scheduling
        timer.scheduleAtFixedRate(new TimerTask() {
            int ticks = 0;

            @Override
            public void run() {
                if (ticks >= 100) {
                    cancel();
                    return;
                }

                for (int x = -2; x <= 2; x++) {
                    for (int z = -2; z <= 2; z++) {
                        BlockPos pos = new BlockPos(player.getPosX() + x, player.getPosY() - 1, player.getPosZ() + z);
                        if (world.getBlockState(pos).getMaterial().isSolid()) {
                            world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState(), 3);
                        }
                    }
                }
                ticks++;
            }
        }, 0, 50);
    }

    private static void blindnessTeleport(ServerPlayerEntity player) {
        player.sendMessage(new StringTextComponent(TextFormatting.DARK_GRAY + "Where are you?"), player.getUniqueID());

        player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 1200, 0));

        double newX = player.getPosX() + (rand.nextDouble() - 0.5) * 100;
        double newZ = player.getPosZ() + (rand.nextDouble() - 0.5) * 100;
        double newY = player.world.getHeight(Heightmap.Type.MOTION_BLOCKING, (int) newX, (int) newZ);

        player.setPositionAndUpdate(newX, newY, newZ);
    }

    private static void lavaSurround(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.RED + "A ring of lava!"), player.getUniqueID());

        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                double distance = Math.sqrt(x * x + z * z);
                if (distance >= 3 && distance <= 4) {
                    BlockPos pos = new BlockPos(player.getPosX() + x, player.getPosY() - 1, player.getPosZ() + z);
                    world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 3);
                }
            }
        }
    }

    private static void anvilRain(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.GRAY + "Anvils are falling!"), player.getUniqueID());

        int anvilCount = 5 + rand.nextInt(10);
        for (int i = 0; i < anvilCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 8;
            double offsetZ = (rand.nextDouble() - 0.5) * 8;

            FallingBlockEntity anvil = new FallingBlockEntity(world,
                    player.getPosX() + offsetX, player.getPosY() + 20, player.getPosZ() + offsetZ,
                    Blocks.ANVIL.getDefaultState());
            anvil.fallTime = 1; // skip the first tick self-removal check
            world.addEntity(anvil);
        }
    }

    private static void skeletonSpawn(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.WHITE + "Skeleton army!"), player.getUniqueID());

        String[] names = {"Bonehead", "Rattles", "Clicky", "Spooky", "Scary", "Mr. Bones", "Pit", "Pat", "Jen", "Dev", "Jelly Bean", "Dog Treat", "Horseman", "Skele-Ton", "Bonnie", "Elena", "Caroline"};

        int skeletonCount = 3 + rand.nextInt(5);
        for (int i = 0; i < skeletonCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 10;
            double offsetZ = (rand.nextDouble() - 0.5) * 10;

            SkeletonEntity skeleton = EntityType.SKELETON.create(world);
            skeleton.setPosition(player.getPosX() + offsetX, player.getPosY(), player.getPosZ() + offsetZ);
            skeleton.setCustomName(new StringTextComponent(names[rand.nextInt(names.length)]));

            skeleton.setItemStackToSlot(EquipmentSlotType.HEAD,  getRandomArmor(EquipmentSlotType.HEAD));
            skeleton.setItemStackToSlot(EquipmentSlotType.CHEST, getRandomArmor(EquipmentSlotType.CHEST));
            skeleton.setItemStackToSlot(EquipmentSlotType.LEGS,  getRandomArmor(EquipmentSlotType.LEGS));
            skeleton.setItemStackToSlot(EquipmentSlotType.FEET,  getRandomArmor(EquipmentSlotType.FEET));

            ItemStack bow = new ItemStack(Items.BOW);
            bow.addEnchantment(Enchantments.POWER, 1 + rand.nextInt(5));
            if (rand.nextBoolean()) {
                bow.addEnchantment(Enchantments.FLAME, 1);
            }
            skeleton.setItemStackToSlot(EquipmentSlotType.MAINHAND, bow);

            world.addEntity(skeleton);
        }
    }

    private static void lavaUnderFeet(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.RED + "Lava beneath!"), player.getUniqueID());

        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos pos = new BlockPos(player.getPosX() + x, player.getPosY() - 1, player.getPosZ() + z);
                world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 3);
            }
        }
    }

    private static void phantomSpawn(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.DARK_PURPLE + "Phantoms descend!"), player.getUniqueID());

        int phantomCount = 3 + rand.nextInt(6);
        for (int i = 0; i < phantomCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 10;
            double offsetZ = (rand.nextDouble() - 0.5) * 10;

            PhantomEntity phantom = EntityType.PHANTOM.create(world);
            phantom.setPosition(player.getPosX() + offsetX, player.getPosY() + 15, player.getPosZ() + offsetZ);
            phantom.setPhantomSize(1 + rand.nextInt(64));

            world.addEntity(phantom);
        }
    }

    private static void babyZombieSpawn(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.GREEN + "Baby zombie rush!"), player.getUniqueID());

        int zombieCount = 5 + rand.nextInt(10);
        for (int i = 0; i < zombieCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 8;
            double offsetZ = (rand.nextDouble() - 0.5) * 8;

            ZombieEntity zombie = EntityType.ZOMBIE.create(world);
            zombie.setPosition(player.getPosX() + offsetX, player.getPosY(), player.getPosZ() + offsetZ);
            zombie.setChild(true);
            zombie.addPotionEffect(new EffectInstance(Effects.SPEED, 999999, 2));

            world.addEntity(zombie);
        }
    }

    private static void blindness(ServerPlayerEntity player) {
        player.sendMessage(new StringTextComponent(TextFormatting.DARK_GRAY + "Darkness falls!"), player.getUniqueID());
        player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 2400, 0));
    }

    private static void chargedcreeperSpawn(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.GREEN + "Creeper.... Aww Man."), player.getUniqueID());

        int creeperCount = 3 + rand.nextInt(7);
        for (int i = 0; i < creeperCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 8;
            double offsetZ = (rand.nextDouble() - 0.5) * 8;

            CreeperEntity creeper = EntityType.CREEPER.create(world);
            creeper.setPosition(player.getPosX() + offsetX, player.getPosY(), player.getPosZ() + offsetZ);

            CompoundNBT nbt = new CompoundNBT();
            nbt.putBoolean("powered", true);
            creeper.readAdditional(nbt);

            world.addEntity(creeper);
        }
    }

    private static void miningFatigue(ServerPlayerEntity player) {
        player.sendMessage(new StringTextComponent(TextFormatting.GRAY + "Your arms feel heavy..."), player.getUniqueID());
        player.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 1200, 2));
    }

    private static void levitationSlowfall(ServerPlayerEntity player) {
        player.sendMessage(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Up, up, and away!"), player.getUniqueID());

        player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 400, 1));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, 1200, 0));
            }
        }, 400);
    }

    private static void longLevitation(ServerPlayerEntity player) {
        player.sendMessage(new StringTextComponent(TextFormatting.LIGHT_PURPLE + "Extended flight!"), player.getUniqueID());
        player.addPotionEffect(new EffectInstance(Effects.LEVITATION, 3600, 0));
    }

    private static void iceUnderFeet(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.AQUA + "Ice skating time!"), player.getUniqueID());

        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                BlockPos pos = new BlockPos(player.getPosX() + x, player.getPosY() - 1, player.getPosZ() + z);
                if (world.getBlockState(pos).getMaterial().isSolid()) {
                    world.setBlockState(pos, Blocks.BLUE_ICE.getDefaultState(), 3);
                }
            }
        }
    }

    private static void witherSpawn(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.DARK_GRAY + "The Withers have arrived!"), player.getUniqueID());

        int witherCount = 2 + rand.nextInt(5);
        for (int i = 0; i < witherCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 15;
            double offsetZ = (rand.nextDouble() - 0.5) * 15;

            WitherEntity wither = EntityType.WITHER.create(world);
            wither.setPosition(player.getPosX() + offsetX, player.getPosY() + 5, player.getPosZ() + offsetZ);
            world.addEntity(wither);
        }
    }

    private static void groundBreak(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.RED + "The ground is crumbling!"), player.getUniqueID());

        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                for (int y = -1; y >= -3; y--) {
                    BlockPos pos = new BlockPos(player.getPosX() + x, player.getPosY() + y, player.getPosZ() + z);
                    world.destroyBlock(pos, true);
                }
            }
        }
    }

    private static void tntRain(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.RED + "TNT is raining!"), player.getUniqueID());

        int tntCount = 10 + rand.nextInt(15);
        for (int i = 0; i < tntCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 10;
            double offsetZ = (rand.nextDouble() - 0.5) * 10;

            TNTEntity tnt = new TNTEntity(world, player.getPosX() + offsetX, player.getPosY() + 20, player.getPosZ() + offsetZ, null);
            tnt.setFuse(60 + rand.nextInt(40));
            world.addEntity(tnt);
        }
    }

    private static void waveAttack(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.RED + "Three waves incoming!"), player.getUniqueID());

        // Wave 1: Diamond armored zombies
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                player.sendMessage(new StringTextComponent(TextFormatting.YELLOW + "Wave 1: Diamond Zombies!"), player.getUniqueID());

                for (int i = 0; i < 5; i++) {
                    double offsetX = (rand.nextDouble() - 0.5) * 12;
                    double offsetZ = (rand.nextDouble() - 0.5) * 12;

                    ZombieEntity zombie = EntityType.ZOMBIE.create(world);
                    zombie.setPosition(player.getPosX() + offsetX, player.getPosY(), player.getPosZ() + offsetZ);

                    zombie.setItemStackToSlot(EquipmentSlotType.HEAD,     new ItemStack(Items.DIAMOND_HELMET));
                    zombie.setItemStackToSlot(EquipmentSlotType.CHEST,    new ItemStack(Items.DIAMOND_CHESTPLATE));
                    zombie.setItemStackToSlot(EquipmentSlotType.LEGS,     new ItemStack(Items.DIAMOND_LEGGINGS));
                    zombie.setItemStackToSlot(EquipmentSlotType.FEET,     new ItemStack(Items.DIAMOND_BOOTS));
                    zombie.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));

                    world.addEntity(zombie);
                }
            }
        }, 2000);

        // Wave 2: Charged Creepers
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                player.sendMessage(new StringTextComponent(TextFormatting.YELLOW + "Wave 2: Charged Creepers!"), player.getUniqueID());

                for (int i = 0; i < 4; i++) {
                    double offsetX = (rand.nextDouble() - 0.5) * 12;
                    double offsetZ = (rand.nextDouble() - 0.5) * 12;

                    CreeperEntity creeper = EntityType.CREEPER.create(world);
                    creeper.setPosition(player.getPosX() + offsetX, player.getPosY(), player.getPosZ() + offsetZ);

                    CompoundNBT nbt = new CompoundNBT();
                    nbt.putBoolean("powered", true);
                    creeper.readAdditional(nbt);

                    world.addEntity(creeper);
                }
            }
        }, 15000);

        // Wave 3: Netherite Skeletons with Power 4 bow
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                player.sendMessage(new StringTextComponent(TextFormatting.YELLOW + "Wave 3: Netherite Skeletons!"), player.getUniqueID());

                for (int i = 0; i < 3; i++) {
                    double offsetX = (rand.nextDouble() - 0.5) * 12;
                    double offsetZ = (rand.nextDouble() - 0.5) * 12;

                    SkeletonEntity skeleton = EntityType.SKELETON.create(world);
                    skeleton.setPosition(player.getPosX() + offsetX, player.getPosY(), player.getPosZ() + offsetZ);

                    skeleton.setItemStackToSlot(EquipmentSlotType.HEAD,  new ItemStack(Items.NETHERITE_HELMET));
                    skeleton.setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(Items.NETHERITE_CHESTPLATE));
                    skeleton.setItemStackToSlot(EquipmentSlotType.LEGS,  new ItemStack(Items.NETHERITE_LEGGINGS));
                    skeleton.setItemStackToSlot(EquipmentSlotType.FEET,  new ItemStack(Items.NETHERITE_BOOTS));

                    ItemStack bow = new ItemStack(Items.BOW);
                    bow.addEnchantment(Enchantments.POWER, 4);
                    skeleton.setItemStackToSlot(EquipmentSlotType.MAINHAND, bow);

                    world.addEntity(skeleton);
                }
            }
        }, 30000);
    }

    private static void harmPotionRain(ServerPlayerEntity player, ServerWorld world) {
        player.sendMessage(new StringTextComponent(TextFormatting.DARK_RED + "Deadly potions falling!"), player.getUniqueID());

        int potionCount = 5 + rand.nextInt(10);
        for (int i = 0; i < potionCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * 10;
            double offsetZ = (rand.nextDouble() - 0.5) * 10;

            PotionEntity potion = new PotionEntity(world, player.getPosX() + offsetX, player.getPosY() + 15, player.getPosZ() + offsetZ);

            ItemStack potionStack = new ItemStack(Items.SPLASH_POTION);
            potionStack = PotionUtils.addPotionToItemStack(potionStack, Potions.STRONG_HARMING);
            potion.setItem(potionStack);

            world.addEntity(potion);
        }
    }

    // ==================== HELPERS ====================

    private static ItemStack getRandomArmor(EquipmentSlotType slot) {
        if (rand.nextInt(3) == 0) return ItemStack.EMPTY;

        List<ItemStack> armors = new ArrayList<>();
        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            if (item instanceof ArmorItem) {
                ArmorItem armor = (ArmorItem) item;
                if (armor.getEquipmentSlot() == slot) {
                    armors.add(new ItemStack(item));
                }
            }
        });

        if (armors.isEmpty()) return ItemStack.EMPTY;
        return armors.get(rand.nextInt(armors.size()));
    }

    private static boolean isContainer(Block block) {
        return block instanceof ShulkerBoxBlock
                || block instanceof ChestBlock
                || block instanceof AbstractFurnaceBlock
                || block instanceof HopperBlock
                || block instanceof BarrelBlock
                || block == Blocks.DISPENSER
                || block == Blocks.DROPPER
                || block instanceof CampfireBlock
                || block == Blocks.BREWING_STAND
                || block == Blocks.JUKEBOX;
    }

    private static ItemStack createFilledContainer(Block block, ServerWorld world) {
        if (block == Blocks.DISPENSER) return createFilledDispenser();
        if (block instanceof CampfireBlock) return createFilledCampfire();
        if (block == Blocks.BREWING_STAND) return createFilledBrewingStand();
        if (block == Blocks.JUKEBOX)      return createFilledJukebox();
        if (block instanceof HopperBlock) return createFilledHopper();
        if (block == Blocks.DROPPER)      return createFilledDropper();

        // Handles vanilla AND modded furnaces
        if (block.hasTileEntity(block.getDefaultState())) {
            TileEntity te = block.createTileEntity(block.getDefaultState(), world);
            if (te instanceof AbstractFurnaceTileEntity) {
                return createFilledFurnaceLike(block, (AbstractFurnaceTileEntity) te);
            }
        }

        ItemStack stack = new ItemStack(block);
        List<Item> allItems = new ArrayList<>(ForgeRegistries.ITEMS.getValues());

        ListNBT itemList = new ListNBT();
        Set<Integer> usedSlots = new HashSet<>();
        int itemCount = 3 + rand.nextInt(10);

        for (int i = 0; i < itemCount; i++) {
            int slot;
            int attempts = 0;
            do {
                slot = rand.nextInt(27);
                attempts++;
            } while (usedSlots.contains(slot) && attempts < 100);
            if (usedSlots.contains(slot)) break;
            usedSlots.add(slot);

            Item item = allItems.get(rand.nextInt(allItems.size()));
            int count = 1 + rand.nextInt(Math.min(item.getMaxStackSize(), 64));

            CompoundNBT itemNBT = new CompoundNBT();
            itemNBT.putByte("Slot", (byte) slot);
            itemNBT.putString("id", item.getRegistryName().toString());
            itemNBT.putByte("Count", (byte) count);
            itemList.add(itemNBT);
        }

        CompoundNBT blockEntityTag = new CompoundNBT();
        blockEntityTag.put("Items", itemList);
        CompoundNBT stackTag = new CompoundNBT();
        stackTag.put("BlockEntityTag", blockEntityTag);
        stack.setTag(stackTag);

        return stack;
    }

    private static ItemStack createFilledFurnaceLike(Block block, AbstractFurnaceTileEntity te) {
        List<Item> ingredients = new ArrayList<>();

        ServerLifecycleHooks.getCurrentServer().getRecipeManager().getRecipes().forEach(recipe -> {
            if (te instanceof BlastFurnaceTileEntity && recipe instanceof BlastingRecipe) {
                recipe.getIngredients().get(0).getMatchingStacks();
                for (ItemStack match : recipe.getIngredients().get(0).getMatchingStacks()) {
                    if (!match.isEmpty()) ingredients.add(match.getItem());
                }
            } else if (te instanceof SmokerTileEntity && recipe instanceof SmokingRecipe) {
                for (ItemStack match : recipe.getIngredients().get(0).getMatchingStacks()) {
                    if (!match.isEmpty()) ingredients.add(match.getItem());
                }
            } else if (!(te instanceof BlastFurnaceTileEntity) && !(te instanceof SmokerTileEntity)
                    && recipe instanceof FurnaceRecipe) {
                // Covers vanilla furnace AND any modded furnace that extends AbstractFurnaceTileEntity
                for (ItemStack match : recipe.getIngredients().get(0).getMatchingStacks()) {
                    if (!match.isEmpty() && !match.getItem().isFood()) {
                        ingredients.add(match.getItem());
                    }
                }
            }
        });

        return buildCookingContainer(new ItemStack(block), ingredients, 1);
    }

    private static ItemStack createFilledCampfire() {
        List<Item> rawFoods = new ArrayList<>();

        ServerLifecycleHooks.getCurrentServer().getRecipeManager().getRecipes().forEach(recipe -> {
            if (recipe instanceof CampfireCookingRecipe) {
                for (ItemStack match : recipe.getIngredients().get(0).getMatchingStacks()) {
                    if (!match.isEmpty()) {
                        rawFoods.add(match.getItem());
                    }
                }
            }
        });

        // Campfire has 4 slots, fill 1-4 of them
        ListNBT itemList = new ListNBT();
        int slotCount = 1 + rand.nextInt(4);
        for (int i = 0; i < slotCount; i++) {
            if (rawFoods.isEmpty()) break;
            Item item = rawFoods.get(rand.nextInt(rawFoods.size()));
            CompoundNBT itemNBT = new CompoundNBT();
            itemNBT.putByte("Slot", (byte) i);
            itemNBT.putString("id", item.getRegistryName().toString());
            itemNBT.putByte("Count", (byte) 1);
            itemList.add(itemNBT);
        }

        CompoundNBT blockEntityTag = new CompoundNBT();
        blockEntityTag.put("Items", itemList);
        CompoundNBT stackTag = new CompoundNBT();
        stackTag.put("BlockEntityTag", blockEntityTag);
        ItemStack stack = new ItemStack(Blocks.CAMPFIRE);
        stack.setTag(stackTag);
        return stack;
    }

    // Shared helper for furnace/blast furnace/smoker - single item in slot 0, fuel slot stays empty
    private static ItemStack buildCookingContainer(ItemStack stack, List<Item> items, int count) {
        if (items.isEmpty()) return stack;

        ListNBT itemList = new ListNBT();
        for (int i = 0; i < count; i++) {
            Item item = items.get(rand.nextInt(items.size()));
            CompoundNBT itemNBT = new CompoundNBT();
            itemNBT.putByte("Slot", (byte) 0);
            itemNBT.putString("id", item.getRegistryName().toString());
            itemNBT.putByte("Count", (byte) 1);
            itemList.add(itemNBT);
        }

        CompoundNBT blockEntityTag = new CompoundNBT();
        blockEntityTag.put("Items", itemList);
        CompoundNBT stackTag = new CompoundNBT();
        stackTag.put("BlockEntityTag", blockEntityTag);
        stack.setTag(stackTag);
        return stack;
    }

    private static ItemStack createFilledDispenser() {
        ItemStack stack = new ItemStack(Blocks.DISPENSER);

        // Build projectile/spawnegg pool
        List<ItemStack> projectiles = new ArrayList<>();

        // Spawn eggs
        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            if (item instanceof SpawnEggItem) {
                projectiles.add(new ItemStack(item));
            }
        });

        // Modded arrows
        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            if (item instanceof ArrowItem) {
                projectiles.add(new ItemStack(item, 16));
            }
        });

        // Vanilla projectiles and throwables
        projectiles.add(new ItemStack(Items.SPECTRAL_ARROW));
        projectiles.add(new ItemStack(Items.SNOWBALL));
        projectiles.add(new ItemStack(Items.ENDER_PEARL));
        projectiles.add(new ItemStack(Items.EGG));
        projectiles.add(new ItemStack(Items.FIRE_CHARGE));
        projectiles.add(new ItemStack(Items.FIREWORK_ROCKET));

        // Tipped arrows and potions using ForgeRegistries.POTION_TYPES
        List<Potion> potions = new ArrayList<>(ForgeRegistries.POTION_TYPES.getValues());

        if (!potions.isEmpty()) {
            Potion randomPotion = potions.get(rand.nextInt(potions.size()));

            ItemStack tippedArrow = PotionUtils.addPotionToItemStack(
                    new ItemStack(Items.TIPPED_ARROW, 16),
                    randomPotion);
            projectiles.add(tippedArrow);

            ItemStack splash = PotionUtils.addPotionToItemStack(
                    new ItemStack(Items.SPLASH_POTION),
                    potions.get(rand.nextInt(potions.size())));
            ItemStack lingering = PotionUtils.addPotionToItemStack(
                    new ItemStack(Items.LINGERING_POTION),
                    potions.get(rand.nextInt(potions.size())));
            projectiles.add(splash);
            projectiles.add(lingering);
        }

        // Fill 9 dispenser slots
        ListNBT itemList = new ListNBT();
        Set<Integer> usedSlots = new HashSet<>();
        int slotCount = 3 + rand.nextInt(7); // 3-9 slots filled

        for (int i = 0; i < slotCount; i++) {
            int slot;
            int attempts = 0;
            do {
                slot = rand.nextInt(9);
                attempts++;
            } while (usedSlots.contains(slot) && attempts < 100);
            if (usedSlots.contains(slot)) break;
            usedSlots.add(slot);

            ItemStack chosen = projectiles.get(rand.nextInt(projectiles.size())).copy();
            int maxStack = chosen.getMaxStackSize();
            chosen.setCount(1 + rand.nextInt(Math.max(1, Math.min(maxStack, 64))));

            CompoundNBT itemNBT = new CompoundNBT();
            itemNBT.putByte("Slot", (byte) slot);
            itemNBT.putString("id", chosen.getItem().getRegistryName().toString());
            itemNBT.putByte("Count", (byte) chosen.getCount());
            if (chosen.hasTag()) {
                itemNBT.put("tag", chosen.getTag());
            }
            itemList.add(itemNBT);
        }

        CompoundNBT blockEntityTag = new CompoundNBT();
        blockEntityTag.put("Items", itemList);
        CompoundNBT stackTag = new CompoundNBT();
        stackTag.put("BlockEntityTag", blockEntityTag);
        stack.setTag(stackTag);

        return stack;
    }

    private static ItemStack createFilledSpawner() {
        ItemStack stack = new ItemStack(Blocks.SPAWNER);

        // Filter out non-mob entities like projectiles, items, falling blocks etc.
        List<EntityType<?>> entityTypes = ForgeRegistries.ENTITIES.getValues().stream()
                .filter(et -> et.getClassification() != EntityClassification.MISC)
                .collect(Collectors.toList());

        if (entityTypes.isEmpty()) return stack;

        EntityType<?> entityType = entityTypes.get(rand.nextInt(entityTypes.size()));

        CompoundNBT spawnData = new CompoundNBT();
        spawnData.putString("id", entityType.getRegistryName().toString());

        CompoundNBT blockEntityTag = new CompoundNBT();
        blockEntityTag.put("SpawnData", spawnData);

        CompoundNBT stackTag = new CompoundNBT();
        stackTag.put("BlockEntityTag", blockEntityTag);
        stack.setTag(stackTag);

        return stack;
    }

    private static ItemStack createFilledDropper() {
        // Dropper holds any item, random like a chest but only 9 slots
        ItemStack stack = new ItemStack(Blocks.DROPPER);
        List<Item> allItems = new ArrayList<>(ForgeRegistries.ITEMS.getValues());

        ListNBT itemList = new ListNBT();
        Set<Integer> usedSlots = new HashSet<>();
        int slotCount = 2 + rand.nextInt(8);

        for (int i = 0; i < slotCount; i++) {
            int slot;
            int attempts = 0;
            do {
                slot = rand.nextInt(9);
                attempts++;
            } while (usedSlots.contains(slot) && attempts < 100);
            if (usedSlots.contains(slot)) break;
            usedSlots.add(slot);

            Item item = allItems.get(rand.nextInt(allItems.size()));
            CompoundNBT itemNBT = new CompoundNBT();
            itemNBT.putByte("Slot", (byte) slot);
            itemNBT.putString("id", item.getRegistryName().toString());
            itemNBT.putByte("Count", (byte)(1 + rand.nextInt(Math.min(item.getMaxStackSize(), 64))));
            itemList.add(itemNBT);
        }

        CompoundNBT blockEntityTag = new CompoundNBT();
        blockEntityTag.put("Items", itemList);
        CompoundNBT stackTag = new CompoundNBT();
        stackTag.put("BlockEntityTag", blockEntityTag);
        stack.setTag(stackTag);
        return stack;
    }

    private static ItemStack createFilledHopper() {
        // Hopper has 5 slots
        ItemStack stack = new ItemStack(Blocks.HOPPER);
        List<Item> allItems = new ArrayList<>(ForgeRegistries.ITEMS.getValues());

        ListNBT itemList = new ListNBT();
        Set<Integer> usedSlots = new HashSet<>();
        int slotCount = 1 + rand.nextInt(5);

        for (int i = 0; i < slotCount; i++) {
            int slot;
            int attempts = 0;
            do {
                slot = rand.nextInt(5);
                attempts++;
            } while (usedSlots.contains(slot) && attempts < 100);
            if (usedSlots.contains(slot)) break;
            usedSlots.add(slot);

            Item item = allItems.get(rand.nextInt(allItems.size()));
            CompoundNBT itemNBT = new CompoundNBT();
            itemNBT.putByte("Slot", (byte) slot);
            itemNBT.putString("id", item.getRegistryName().toString());
            itemNBT.putByte("Count", (byte)(1 + rand.nextInt(Math.min(item.getMaxStackSize(), 64))));
            itemList.add(itemNBT);
        }

        CompoundNBT blockEntityTag = new CompoundNBT();
        blockEntityTag.put("Items", itemList);
        CompoundNBT stackTag = new CompoundNBT();
        stackTag.put("BlockEntityTag", blockEntityTag);
        stack.setTag(stackTag);
        return stack;
    }

    private static ItemStack createFilledBrewingStand() {
        ItemStack stack = new ItemStack(Blocks.BREWING_STAND);
        List<Potion> potions = new ArrayList<>(ForgeRegistries.POTION_TYPES.getValues());
        if (potions.isEmpty()) return stack;

        ListNBT itemList = new ListNBT();

        // Slots 0-2 are the three potion bottle slots
        for (int slot = 0; slot <= 2; slot++) {
            if (rand.nextBoolean()) continue; // randomly leave some slots empty
            Potion potion = potions.get(rand.nextInt(potions.size()));

            // Randomly choose between regular, splash and lingering
            Item bottleType;
            int r = rand.nextInt(3);
            if (r == 0)      bottleType = Items.SPLASH_POTION;
            else if (r == 1) bottleType = Items.LINGERING_POTION;
            else             bottleType = Items.POTION;

            ItemStack potionStack = PotionUtils.addPotionToItemStack(new ItemStack(bottleType), potion);

            CompoundNBT itemNBT = new CompoundNBT();
            itemNBT.putByte("Slot", (byte) slot);
            itemNBT.putString("id", potionStack.getItem().getRegistryName().toString());
            itemNBT.putByte("Count", (byte) 1);
            if (potionStack.hasTag()) {
                itemNBT.put("tag", potionStack.getTag());
            }
            itemList.add(itemNBT);
        }

        CompoundNBT blockEntityTag = new CompoundNBT();
        blockEntityTag.put("Items", itemList);
        CompoundNBT stackTag = new CompoundNBT();
        stackTag.put("BlockEntityTag", blockEntityTag);
        stack.setTag(stackTag);
        return stack;
    }

    private static ItemStack createFilledJukebox() {
        ItemStack stack = new ItemStack(Blocks.JUKEBOX);

        List<Item> discs = new ArrayList<>();
        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            if (item instanceof MusicDiscItem) {
                discs.add(item);
            }
        });

        if (discs.isEmpty()) return stack;

        Item disc = discs.get(rand.nextInt(discs.size()));

        // Jukebox stores its disc directly in the block NBT as RecordItem
        CompoundNBT blockEntityTag = new CompoundNBT();
        CompoundNBT discNBT = new CompoundNBT();
        discNBT.putString("id", disc.getRegistryName().toString());
        discNBT.putByte("Count", (byte) 1);
        blockEntityTag.put("RecordItem", discNBT);

        CompoundNBT stackTag = new CompoundNBT();
        stackTag.put("BlockEntityTag", blockEntityTag);
        stack.setTag(stackTag);
        return stack;
    }
}