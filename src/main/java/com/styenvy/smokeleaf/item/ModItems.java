// src/main/java/com/styenvy/smokeleaf/item/ModItems.java
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

    // Dreambloom items (existing)
    public static final DeferredHolder<Item, ItemNameBlockItem> DREAMBLOOM_SEEDS = ITEMS.register("dreambloom_seeds",
            () -> new ItemNameBlockItem(ModBlocks.DREAMBLOOM_CROP.get(),
                    new Item.Properties()));

    private static final FoodProperties DREAMBLOOM_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            // "good vibes"
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION,      20 * 45, 0), 0.85F) // 45s, 85%
            .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING,      20 * 20, 0), 0.70F) // 20s, 70%
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING,           20 * 20, 0), 0.60F) // 20s, 60%
            // "side effects"
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 10, 0), 0.45F) // 10s, 45%
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER,            20 * 15, 0), 0.50F) // 15s munchies, 50%
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> DREAMBLOOM = ITEMS.register("dreambloom",
            () -> new Item(new Item.Properties().food(DREAMBLOOM_FOOD)));

    // Amethyst Haze items
    public static final DeferredHolder<Item, ItemNameBlockItem> AMETHYST_HAZE_SEEDS = ITEMS.register("amethyst_haze_seeds",
            () -> new ItemNameBlockItem(ModBlocks.AMETHYST_HAZE_CROP.get(),
                    new Item.Properties()));

    private static final FoodProperties AMETHYST_HAZE_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> AMETHYST_HAZE = ITEMS.register("amethyst_haze",
            () -> new Item(new Item.Properties().food(AMETHYST_HAZE_FOOD)));

    // Cloudpetal items
    public static final DeferredHolder<Item, ItemNameBlockItem> CLOUDPETAL_SEEDS = ITEMS.register("cloudpetal_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CLOUDPETAL_CROP.get(),
                    new Item.Properties()));

    private static final FoodProperties CLOUDPETAL_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> CLOUDPETAL = ITEMS.register("cloudpetal",
            () -> new Item(new Item.Properties().food(CLOUDPETAL_FOOD)));

    // Crimson Emberleaf items
    public static final DeferredHolder<Item, ItemNameBlockItem> CRIMSON_EMBERLEAF_SEEDS = ITEMS.register("crimson_emberleaf_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CRIMSON_EMBERLEAF_CROP.get(),
                    new Item.Properties()));

    private static final FoodProperties CRIMSON_EMBERLEAF_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> CRIMSON_EMBERLEAF = ITEMS.register("crimson_emberleaf",
            () -> new Item(new Item.Properties().food(CRIMSON_EMBERLEAF_FOOD)));

    // Daydream Bloom items
    public static final DeferredHolder<Item, ItemNameBlockItem> DAYDREAM_BLOOM_SEEDS = ITEMS.register("daydream_bloom_seeds",
            () -> new ItemNameBlockItem(ModBlocks.DAYDREAM_BLOOM_CROP.get(),
                    new Item.Properties()));

    private static final FoodProperties DAYDREAM_BLOOM_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> DAYDREAM_BLOOM = ITEMS.register("daydream_bloom",
            () -> new Item(new Item.Properties().food(DAYDREAM_BLOOM_FOOD)));

    // Runegrass items
    public static final DeferredHolder<Item, ItemNameBlockItem> RUNEGRASS_SEEDS = ITEMS.register("runegrass_seeds",
            () -> new ItemNameBlockItem(ModBlocks.RUNEGRASS_CROP.get(),
                    new Item.Properties()));

    private static final FoodProperties RUNEGRASS_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> RUNEGRASS = ITEMS.register("runegrass",
            () -> new Item(new Item.Properties().food(RUNEGRASS_FOOD)));

    // Skybud items
    public static final DeferredHolder<Item, ItemNameBlockItem> SKYBUD_SEEDS = ITEMS.register("skybud_seeds",
            () -> new ItemNameBlockItem(ModBlocks.SKYBUD_CROP.get(),
                    new Item.Properties()));

    private static final FoodProperties SKYBUD_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> SKYBUD = ITEMS.register("skybud",
            () -> new Item(new Item.Properties().food(SKYBUD_FOOD)));

    // Driftleaf items
    public static final DeferredHolder<Item, ItemNameBlockItem> DRIFTLEAF_SEEDS = ITEMS.register("driftleaf_seeds",
            () -> new ItemNameBlockItem(ModBlocks.DRIFTLEAF_CROP.get(),
                    new Item.Properties()));

    private static final FoodProperties DRIFTLEAF_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> DRIFTLEAF = ITEMS.register("driftleaf",
            () -> new Item(new Item.Properties().food(DRIFTLEAF_FOOD)));

    // Midnight Glow items
    public static final DeferredHolder<Item, ItemNameBlockItem> MIDNIGHT_GLOW_SEEDS = ITEMS.register("midnight_glow_seeds",
            () -> new ItemNameBlockItem(ModBlocks.MIDNIGHT_GLOW_CROP.get(),
                    new Item.Properties()));

    private static final FoodProperties MIDNIGHT_GLOW_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> MIDNIGHT_GLOW = ITEMS.register("midnight_glow",
            () -> new Item(new Item.Properties().food(MIDNIGHT_GLOW_FOOD)));

    // Alchemist Ash items
    public static final DeferredHolder<Item, ItemNameBlockItem> ALCHEMIST_ASH_SEEDS = ITEMS.register("alchemist_ash_seeds",
            () -> new ItemNameBlockItem(ModBlocks.ALCHEMIST_ASH_CROP.get(),
                    new Item.Properties()));

    private static final FoodProperties ALCHEMIST_ASH_FOOD = new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.3F)
            .alwaysEdible()
            .build();

    public static final DeferredHolder<Item, Item> ALCHEMIST_ASH = ITEMS.register("alchemist_ash",
            () -> new Item(new Item.Properties().food(ALCHEMIST_ASH_FOOD)));
}
