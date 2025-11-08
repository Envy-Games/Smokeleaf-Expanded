package com.styenvy.smokeleaf.effect;

import com.styenvy.smokeleaf.SmokeLeafEG;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;

/**
 * Grants creative-style flight while active by adding the CREATIVE_FLIGHT attribute.
 * Uses 1.21.x APIs (no Abilities.mayfly writes).
 */
public class CreativeFlightMobEffect extends MobEffect {

    public CreativeFlightMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x9B12AE);

        // 1.21.x: ResourceLocation constructors are private — use fromNamespaceAndPath/parse.
        // Operation enum renamed to ADD_VALUE (not ADDITION).
        this.addAttributeModifier(
                NeoForgeMod.CREATIVE_FLIGHT,
                ResourceLocation.fromNamespaceAndPath(SmokeLeafEG.MODID, "creative_flight_bonus"),
                1.0D,
                AttributeModifier.Operation.ADD_VALUE
        );
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }
}
