package com.styenvy.smokeleaf.effect;

import com.styenvy.smokeleaf.SmokeLeafEG;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = SmokeLeafEG.MODID)
public final class ModEffectEvents {
    private ModEffectEvents() {}

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        // 1.21.x: Expired/Remove expose the *instance*, not a MobEffect getter.
        MobEffectInstance inst = event.getEffectInstance();
        if (inst == null) return;

        // Example of safe matching without holder casts:
        // if (inst.is(ModMobEffects.CREATIVE_FLIGHT)) { /* no-op; attribute auto-removed */ }
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        MobEffectInstance inst = event.getEffectInstance();
        if (inst == null) return;
    }
}
