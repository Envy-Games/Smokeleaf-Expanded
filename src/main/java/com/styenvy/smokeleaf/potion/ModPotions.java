// File: src/main/java/com/styenvy/smokeleaf/potion/ModPotions.java
package com.styenvy.smokeleaf.potion;

import com.styenvy.smokeleaf.SmokeLeafEG;
import com.styenvy.smokeleaf.effect.ModMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(Registries.POTION, SmokeLeafEG.MODID);

    public static final DeferredHolder<Potion, Potion> CLOUDPETAL = POTIONS.register("cloudpetal",
            () -> new Potion(new MobEffectInstance(MobEffects.JUMP, 20 * 360, 2)));

    public static final DeferredHolder<Potion, Potion> DAYDREAM_BLOOM = POTIONS.register("daydream_bloom",
            () -> new Potion(new MobEffectInstance(MobEffects.CONDUIT_POWER, 20 * 360, 0)));

    public static final DeferredHolder<Potion, Potion> CRIMSON_EMBERLEAF = POTIONS.register("crimson_emberleaf",
            () -> new Potion(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 360, 0)));

    public static final DeferredHolder<Potion, Potion> DREAMBLOOM = POTIONS.register("dreambloom",
            () -> new Potion(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 360, 0)));

    public static final DeferredHolder<Potion, Potion> RUNEGRASS = POTIONS.register("runegrass",
            () -> new Potion(
                    new MobEffectInstance(MobEffects.LUCK, 20 * 360, 2),
                    new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 20 * 360, 0)));

    // Invisibility + purple glow (custom) instead of vanilla white outline
    public static final DeferredHolder<Potion, Potion> MIDNIGHT_GLOW = POTIONS.register("midnight_glow",
            () -> new Potion(
                    new MobEffectInstance(MobEffects.INVISIBILITY, 20 * 360, 0),
                    new MobEffectInstance(MobEffects.GLOWING, 20 * 360, 0)));

    // Haste V (amplifier 4)
    public static final DeferredHolder<Potion, Potion> AMETHYST_HAZE = POTIONS.register("amethyst_haze",
            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 360, 4)));

    public static final DeferredHolder<Potion, Potion> DRIFTLEAF = POTIONS.register("driftleaf",
            () -> new Potion(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 360, 2)));

    // Creative-style flight for 15 minutes (pass the DeferredHolder directly; it implements Holder<MobEffect>)
    public static final DeferredHolder<Potion, Potion> SKYBUD = POTIONS.register("skybud",
            () -> new Potion(new MobEffectInstance(ModMobEffects.CREATIVE_FLIGHT, 20 * 60 * 15, 0)));

    public static final DeferredHolder<Potion, Potion> ALCHEMIST_ASH = POTIONS.register("alchemist_ash",
            () -> new Potion(
                    new MobEffectInstance(MobEffects.WATER_BREATHING, 20 * 360, 0),
                    new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 20 * 360, 0)));
}
