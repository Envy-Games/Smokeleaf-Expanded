// File: src/main/java/com/styenvy/smokeleaf/effect/ModEffectEvents.java
package com.styenvy.smokeleaf.effect;

import com.styenvy.smokeleaf.SmokeLeafEG;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

@EventBusSubscriber(modid = SmokeLeafEG.MODID)
public final class ModEffectEvents {

    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEffectInstance() != null) {
            handleRemoval(event.getEntity(), (MobEffect) event.getEffectInstance().getEffect());
        }
    }

    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        handleRemoval(event.getEntity(), (MobEffect) event.getEffect());
    }

    private static void handleRemoval(LivingEntity entity, MobEffect effect) {
        if (effect == ModMobEffects.CREATIVE_FLIGHT.get()) {
            if (!(entity instanceof Player player)) return;
            if (player.isCreative() || player.isSpectator()) return;
            if (player.level().isClientSide) return;

            // Remove our transient +1 modifier from the CREATIVE_FLIGHT attribute
            AttributeInstance inst = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
            if (inst != null) {
                inst.removeModifier(CreativeFlightMobEffect.FLIGHT_MOD_ID);
            }

            // If player no longer has any source of flight, apply Slow Falling and stop flying
            if (!player.mayFly()) {
                // 30 seconds = 600 ticks
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20 * 30, 0));

                if (player.getAbilities().flying) {
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }
            }
        }
    }

    private ModEffectEvents() {}
}
