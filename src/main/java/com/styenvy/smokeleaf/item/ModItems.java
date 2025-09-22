package com.styenvy.smokeleaf.item;

import com.styenvy.smokeleaf.SmokeLeafEG;
import com.styenvy.smokeleaf.block.ModBlocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, SmokeLeafEG.MODID);

    public static final DeferredHolder<Item, ItemNameBlockItem> DREAMBLOOM_SEEDS = ITEMS.register("dreambloom_seeds",
            () -> new ItemNameBlockItem(ModBlocks.DREAMBLOOM_CROP.get(),
                    new Item.Properties()));

    public static final DeferredHolder<Item, Item> DREAMBLOOM = ITEMS.register("dreambloom",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationModifier(0.3F)
                            .build())));

}