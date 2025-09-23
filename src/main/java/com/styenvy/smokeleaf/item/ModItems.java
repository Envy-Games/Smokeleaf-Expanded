package com.styenvy.smokeleaf.item;

import com.styenvy.smokeleaf.SmokeLeafEG;
import com.styenvy.smokeleaf.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, SmokeLeafEG.MODID);

    public static final DeferredHolder<Item, ItemNameBlockItem> DREAMBLOOM_SEEDS = ITEMS.register("dreambloom_seeds",
            () -> new ItemNameBlockItem(ModBlocks.DREAMBLOOM_CROP.get(),
                    new Item.Properties()));

    // 20 ticks = 1 second
    private static final FoodProperties DREAMBLOOM_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            // “good vibes”
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION,      20 * 45, 0), 0.85F) // 45s, 85%
            .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING,      20 * 20, 0), 0.70F) // 20s, 70%
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING,           20 * 20, 0), 0.60F) // 20s, 60%
            // “side effects”
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 10, 0), 0.45F) // 10s, 45%
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER,            20 * 15, 0), 0.50F) // 15s munchies, 50%
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> DREAMBLOOM = ITEMS.register("dreambloom",
            () -> new Item(new Item.Properties().food(DREAMBLOOM_FOOD)));
}