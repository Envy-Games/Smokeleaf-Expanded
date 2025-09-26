package com.styenvy.smokeleaf.item;

import com.styenvy.smokeleaf.SmokeLeafEG;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SmokeLeafEG.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SMOKELEAF_TAB =
            CREATIVE_MODE_TABS.register("smokeleaf_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.DREAMBLOOM.get()))
                    .title(Component.translatable("creativetab.smokeleaf_tab"))
                    .displayItems((parameters, output) -> {
                        // Dreambloom items
                        output.accept(ModItems.DREAMBLOOM_SEEDS.get());
                        output.accept(ModItems.DREAMBLOOM.get());

                        // Amethyst Haze items
                        output.accept(ModItems.AMETHYST_HAZE_SEEDS.get());
                        output.accept(ModItems.AMETHYST_HAZE.get());

                        // Cloudpetal items
                        output.accept(ModItems.CLOUDPETAL_SEEDS.get());
                        output.accept(ModItems.CLOUDPETAL.get());

                        // Crimson Emberleaf items
                        output.accept(ModItems.CRIMSON_EMBERLEAF_SEEDS.get());
                        output.accept(ModItems.CRIMSON_EMBERLEAF.get());

                        // Daydream Bloom items
                        output.accept(ModItems.DAYDREAM_BLOOM_SEEDS.get());
                        output.accept(ModItems.DAYDREAM_BLOOM.get());

                        // Runegrass items
                        output.accept(ModItems.RUNEGRASS_SEEDS.get());
                        output.accept(ModItems.RUNEGRASS.get());

                        // Skybud items
                        output.accept(ModItems.SKYBUD_SEEDS.get());
                        output.accept(ModItems.SKYBUD.get());

                        // Driftleaf items
                        output.accept(ModItems.DRIFTLEAF_SEEDS.get());
                        output.accept(ModItems.DRIFTLEAF.get());

                        // Midnight Glow items
                        output.accept(ModItems.MIDNIGHT_GLOW_SEEDS.get());
                        output.accept(ModItems.MIDNIGHT_GLOW.get());

                        // Alchemist Ash items
                        output.accept(ModItems.ALCHEMIST_ASH_SEEDS.get());
                        output.accept(ModItems.ALCHEMIST_ASH.get());
                    })
                    .build());
}