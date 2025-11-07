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
                        // Seeds Section
                        output.accept(ModItems.DREAMBLOOM_SEEDS.get());
                        output.accept(ModItems.AMETHYST_HAZE_SEEDS.get());
                        output.accept(ModItems.CLOUDPETAL_SEEDS.get());
                        output.accept(ModItems.CRIMSON_EMBERLEAF_SEEDS.get());
                        output.accept(ModItems.DAYDREAM_BLOOM_SEEDS.get());
                        output.accept(ModItems.RUNEGRASS_SEEDS.get());
                        output.accept(ModItems.SKYBUD_SEEDS.get());
                        output.accept(ModItems.DRIFTLEAF_SEEDS.get());
                        output.accept(ModItems.MIDNIGHT_GLOW_SEEDS.get());
                        output.accept(ModItems.ALCHEMIST_ASH_SEEDS.get());

                        // Harvested Smokeleaf Section
                        output.accept(ModItems.DREAMBLOOM.get());
                        output.accept(ModItems.AMETHYST_HAZE.get());
                        output.accept(ModItems.CLOUDPETAL.get());
                        output.accept(ModItems.CRIMSON_EMBERLEAF.get());
                        output.accept(ModItems.DAYDREAM_BLOOM.get());
                        output.accept(ModItems.RUNEGRASS.get());
                        output.accept(ModItems.SKYBUD.get());
                        output.accept(ModItems.DRIFTLEAF.get());
                        output.accept(ModItems.MIDNIGHT_GLOW.get());
                        output.accept(ModItems.ALCHEMIST_ASH.get());

                        // Cookie Tray
                        output.accept(ModItems.COOKIE_TRAY.get());

                        // Cookies Section
                        output.accept(ModItems.DREAMBLOOM_COOKIE.get());
                        output.accept(ModItems.AMETHYST_HAZE_COOKIE.get());
                        output.accept(ModItems.CLOUDPETAL_COOKIE.get());
                        output.accept(ModItems.CRIMSON_EMBERLEAF_COOKIE.get());
                        output.accept(ModItems.DAYDREAM_BLOOM_COOKIE.get());
                        output.accept(ModItems.RUNEGRASS_COOKIE.get());
                        output.accept(ModItems.SKYBUD_COOKIE.get());
                        output.accept(ModItems.DRIFTLEAF_COOKIE.get());
                        output.accept(ModItems.MIDNIGHT_GLOW_COOKIE.get());
                        output.accept(ModItems.ALCHEMIST_ASH_COOKIE.get());
                    })
                    .build());
}