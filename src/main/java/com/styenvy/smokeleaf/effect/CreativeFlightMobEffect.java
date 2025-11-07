package com.styenvy.smokeleaf.effect;

import com.styenvy.smokeleaf.SmokeLeafEG;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.NeoForgeMod;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Grants players creative-style flight while active by toggling the CREATIVE_FLIGHT attribute.
 * Uses ResourceLocation-based transient modifiers (1.21+).
 */
@ParametersAreNonnullByDefault
public class CreativeFlightMobEffect extends MobEffect {

    // Stable id for our transient attribute modifier
    public static final ResourceLocation FLIGHT_MOD_ID =
            ResourceLocation.fromNamespaceAndPath(SmokeLeafEG.MODID, "effect.creative_flight");

    public CreativeFlightMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x79c2ff);
    }

    /**
     * Apply/refresh the flight-enabling modifier. Must return true to keep the effect alive
     * when {@link #shouldApplyEffectTickThisTick(int, int)} returns true.
     */
    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!(entity instanceof Player player)) return true;
        if (player.isCreative() || player.isSpectator()) return true;

        AttributeInstance inst = player.getAttribute(NeoForgeMod.CREATIVE_FLIGHT);
        if (inst != null) {
            // +1 ADD_VALUE enables flight; use transient + id so repeated ticks don't stack
            inst.addOrUpdateTransientModifier(new AttributeModifier(
                    FLIGHT_MOD_ID,
                    1.0D,
                    AttributeModifier.Operation.ADD_VALUE
            ));

            // QoL: ensure client starts flying immediately if the player wants to
            if (!player.getAbilities().flying) {
                player.getAbilities().flying = true;
                player.onUpdateAbilities();
            }
        }
        return true;
    }

    /**
     * We tick every server tick; safe because the modifier is idempotent via addOrUpdateTransientModifier.
     */
    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        return true;
    }
}