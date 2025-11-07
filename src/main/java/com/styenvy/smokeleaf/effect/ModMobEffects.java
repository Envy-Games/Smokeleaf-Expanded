package com.styenvy.smokeleaf.effect;

import com.styenvy.smokeleaf.SmokeLeafEG;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, SmokeLeafEG.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> CREATIVE_FLIGHT =
            MOB_EFFECTS.register("creative_flight", CreativeFlightMobEffect::new);

    private ModMobEffects() {}
}