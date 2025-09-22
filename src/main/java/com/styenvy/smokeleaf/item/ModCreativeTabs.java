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
                        // Add all mod items to the creative tab
                        output.accept(ModItems.DREAMBLOOM_SEEDS.get());
                        output.accept(ModItems.DREAMBLOOM.get());
                    })
                    .build());
}