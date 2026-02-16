package com.devmaster.random_events.config;

import net.minecraftforge.common.ForgeConfigSpec;

import org.apache.commons.lang3.tuple.Pair;

public class REConfig {

    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final Config CONFIG;

    static {
        Pair<Config, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Config::new);
        COMMON_CONFIG = pair.getRight();
        CONFIG = pair.getLeft();
    }

    public static class Config {
        public final ForgeConfigSpec.IntValue eventIntervalMinutes;
        public final ForgeConfigSpec.IntValue warningTimeSeconds;

        // Event toggles
        public final ForgeConfigSpec.BooleanValue arrowRainEnabled;
        public final ForgeConfigSpec.BooleanValue mlgTeleportEnabled;
        public final ForgeConfigSpec.BooleanValue timeChangeEnabled;
        public final ForgeConfigSpec.BooleanValue snowParticlesEnabled;
        public final ForgeConfigSpec.BooleanValue valuableLootRainEnabled;
        public final ForgeConfigSpec.BooleanValue foodRainEnabled;
        public final ForgeConfigSpec.BooleanValue diamondSwordEnabled;
        public final ForgeConfigSpec.BooleanValue xpRainEnabled;
        public final ForgeConfigSpec.BooleanValue chickenRainEnabled;
        public final ForgeConfigSpec.BooleanValue hotbarShuffleEnabled;
        public final ForgeConfigSpec.BooleanValue pumpkinHeadEnabled;
        public final ForgeConfigSpec.BooleanValue randomTeleportEnabled;
        public final ForgeConfigSpec.BooleanValue thunderstrikesEnabled;
        public final ForgeConfigSpec.BooleanValue tntFeetEnabled;
        public final ForgeConfigSpec.BooleanValue endermanSpawnEnabled;
        public final ForgeConfigSpec.BooleanValue waterSurroundEnabled;
        public final ForgeConfigSpec.BooleanValue fireUnderFeetEnabled;
        public final ForgeConfigSpec.BooleanValue blindnessTeleportEnabled;
        public final ForgeConfigSpec.BooleanValue lavaSurroundEnabled;
        public final ForgeConfigSpec.BooleanValue anvilRainEnabled;
        public final ForgeConfigSpec.BooleanValue skeletonSpawnEnabled;
        public final ForgeConfigSpec.BooleanValue lavaUnderFeetEnabled;
        public final ForgeConfigSpec.BooleanValue phantomSpawnEnabled;
        public final ForgeConfigSpec.BooleanValue babyZombieSpawnEnabled;
        public final ForgeConfigSpec.BooleanValue blindnessEnabled;
        public final ForgeConfigSpec.BooleanValue chargedCreeperSpawnEnabled;
        public final ForgeConfigSpec.BooleanValue miningFatigueEnabled;
        public final ForgeConfigSpec.BooleanValue levitationSlowfallEnabled;
        public final ForgeConfigSpec.BooleanValue longLevitationEnabled;
        public final ForgeConfigSpec.BooleanValue iceUnderFeetEnabled;
        public final ForgeConfigSpec.BooleanValue witherSpawnEnabled;
        public final ForgeConfigSpec.BooleanValue groundBreakEnabled;
        public final ForgeConfigSpec.BooleanValue tntRainEnabled;
        public final ForgeConfigSpec.BooleanValue waveAttackEnabled;
        public final ForgeConfigSpec.BooleanValue harmPotionRainEnabled;

        public Config(ForgeConfigSpec.Builder builder) {
            builder.push("Timers");

            eventIntervalMinutes = builder
                    .comment("Time between random events in minutes")
                    .defineInRange("event", 10, 1, 1440);

            warningTimeSeconds = builder
                    .comment("Warning time before event in seconds")
                    .defineInRange("warning", 60, 0, 300);

            builder.pop();
            builder.push("events");

            arrowRainEnabled = builder.define("arrowRainEnabled", true);
            mlgTeleportEnabled = builder.define("mlgTeleportEnabled", true);
            timeChangeEnabled = builder.define("timeChangeEnabled", true);
            snowParticlesEnabled = builder.define("snowParticlesEnabled", true);
            valuableLootRainEnabled = builder.define("valuableLootRainEnabled", true);
            foodRainEnabled = builder.define("foodRainEnabled", true);
            diamondSwordEnabled = builder.define("diamondSwordEnabled", true);
            xpRainEnabled = builder.define("xpRainEnabled", true);
            chickenRainEnabled = builder.define("chickenRainEnabled", true);
            hotbarShuffleEnabled = builder.define("hotbarShuffleEnabled", true);
            pumpkinHeadEnabled = builder.define("pumpkinHeadEnabled", true);
            randomTeleportEnabled = builder.define("randomTeleportEnabled", true);
            thunderstrikesEnabled = builder.define("thunderstrikesEnabled", true);
            tntFeetEnabled = builder.define("tntFeetEnabled", true);
            endermanSpawnEnabled = builder.define("endermanSpawnEnabled", true);
            waterSurroundEnabled = builder.define("waterSurroundEnabled", true);
            fireUnderFeetEnabled = builder.define("fireUnderFeetEnabled", true);
            blindnessTeleportEnabled = builder.define("blindnessTeleportEnabled", true);
            lavaSurroundEnabled = builder.define("lavaSurroundEnabled", true);
            anvilRainEnabled = builder.define("anvilRainEnabled", true);
            skeletonSpawnEnabled = builder.define("skeletonSpawnEnabled", true);
            lavaUnderFeetEnabled = builder.define("lavaUnderFeetEnabled", true);
            phantomSpawnEnabled = builder.define("phantomSpawnEnabled", true);
            babyZombieSpawnEnabled = builder.define("babyZombieSpawnEnabled", true);
            blindnessEnabled = builder.define("blindnessEnabled", true);
            chargedCreeperSpawnEnabled = builder.define("chargedCreeperSpawnEnabled", true);
            miningFatigueEnabled = builder.define("miningFatigueEnabled", true);
            levitationSlowfallEnabled = builder.define("levitationSlowfallEnabled", true);
            longLevitationEnabled = builder.define("longLevitationEnabled", true);
            iceUnderFeetEnabled = builder.define("iceUnderFeetEnabled", true);
            witherSpawnEnabled = builder.define("witherSpawnEnabled", true);
            groundBreakEnabled = builder.define("groundBreakEnabled", true);
            tntRainEnabled = builder.define("tntRainEnabled", true);
            waveAttackEnabled = builder.define("waveAttackEnabled", true);
            harmPotionRainEnabled = builder.define("harmPotionRainEnabled", true);

            builder.pop();
        }
    }
}