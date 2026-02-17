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
        public final ForgeConfigSpec.BooleanValue letItSnowEnabled;
        public final ForgeConfigSpec.BooleanValue valuableLootRainEnabled;
        public final ForgeConfigSpec.BooleanValue foodRainEnabled;
        public final ForgeConfigSpec.BooleanValue sharpDiamondSwordEnabled;
        public final ForgeConfigSpec.BooleanValue xpRainEnabled;
        public final ForgeConfigSpec.BooleanValue chickenRainEnabled;
        public final ForgeConfigSpec.BooleanValue hotbarShuffleEnabled;
        public final ForgeConfigSpec.BooleanValue pumpkinHeadEnabled;
        public final ForgeConfigSpec.BooleanValue randomTeleportEnabled;
        public final ForgeConfigSpec.BooleanValue thunderstrikesEnabled;
        public final ForgeConfigSpec.BooleanValue tntRunEnabled;
        public final ForgeConfigSpec.BooleanValue endermanWatchersEnabled;
        public final ForgeConfigSpec.BooleanValue waterSurroundEnabled;
        public final ForgeConfigSpec.BooleanValue fireUnderFeetEnabled;
        public final ForgeConfigSpec.BooleanValue blindTeleportEnabled;
        public final ForgeConfigSpec.BooleanValue ringOfLavaEnabled;
        public final ForgeConfigSpec.BooleanValue anvilRainEnabled;
        public final ForgeConfigSpec.BooleanValue skeletonArmyEnabled;
        public final ForgeConfigSpec.BooleanValue lavaUnderFeetEnabled;
        public final ForgeConfigSpec.BooleanValue phantomDescendsEnabled;
        public final ForgeConfigSpec.BooleanValue babyZombieRushEnabled;
        public final ForgeConfigSpec.BooleanValue blindBanditEnabled;
        public final ForgeConfigSpec.BooleanValue creeperAwwManEnabled;
        public final ForgeConfigSpec.BooleanValue heavyArmsEnabled;
        public final ForgeConfigSpec.BooleanValue upUpAndAwayEnabled;
        public final ForgeConfigSpec.BooleanValue extendedFlightEnabled;
        public final ForgeConfigSpec.BooleanValue iceSkatingEnabled;
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
            letItSnowEnabled = builder.define("letItSnowEnabled", true);
            valuableLootRainEnabled = builder.define("valuableLootRainEnabled", true);
            foodRainEnabled = builder.define("foodRainEnabled", true);
            sharpDiamondSwordEnabled = builder.define("sharpDiamondSwordEnabled", true);
            xpRainEnabled = builder.define("xpRainEnabled", true);
            chickenRainEnabled = builder.define("chickenRainEnabled", true);
            hotbarShuffleEnabled = builder.define("hotbarShuffleEnabled", true);
            pumpkinHeadEnabled = builder.define("pumpkinHeadEnabled", true);
            randomTeleportEnabled = builder.define("randomTeleportEnabled", true);
            thunderstrikesEnabled = builder.define("thunderstrikesEnabled", true);
            tntRunEnabled = builder.define("tntFeetEnabled", true);
            endermanWatchersEnabled = builder.define("endermanWatchersEnabled", true);
            waterSurroundEnabled = builder.define("waterSurroundEnabled", true);
            fireUnderFeetEnabled = builder.define("fireUnderFeetEnabled", true);
            blindTeleportEnabled = builder.define("blindTeleportEnabled", true);
            ringOfLavaEnabled = builder.define("ringOfLavaEnabled", true);
            anvilRainEnabled = builder.define("anvilRainEnabled", true);
            skeletonArmyEnabled = builder.define("skeletonArmyEnabled", true);
            lavaUnderFeetEnabled = builder.define("lavaUnderFeetEnabled", true);
            phantomDescendsEnabled = builder.define("phantomDescendsEnabled", true);
            babyZombieRushEnabled = builder.define("babyZombieSpawnEnabled", true);
            blindBanditEnabled = builder.define("blindBanditEnabled", true);
            creeperAwwManEnabled = builder.define("creeperAwwManEnabled", true);
            heavyArmsEnabled = builder.define("heavyArmsEnabled", true);
            upUpAndAwayEnabled = builder.define("upUpAndAwayEnabled", true);
            extendedFlightEnabled = builder.define("extendedFlightEnabled", true);
            iceSkatingEnabled = builder.define("iceSkatingEnabled", true);
            witherSpawnEnabled = builder.define("witherSpawnEnabled", true);
            groundBreakEnabled = builder.define("groundBreakEnabled", true);
            tntRainEnabled = builder.define("tntRainEnabled", true);
            waveAttackEnabled = builder.define("waveAttackEnabled", true);
            harmPotionRainEnabled = builder.define("harmPotionRainEnabled", true);

            builder.pop();
        }
    }
}