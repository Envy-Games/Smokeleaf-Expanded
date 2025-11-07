package com.styenvy.smokeleaf.item;

import com.styenvy.smokeleaf.SmokeLeafEG;
import com.styenvy.smokeleaf.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, SmokeLeafEG.MODID);

    // ==================== SEEDS ====================
    public static final DeferredHolder<Item, ItemNameBlockItem> DREAMBLOOM_SEEDS = ITEMS.register("dreambloom_seeds",
            () -> new ItemNameBlockItem(ModBlocks.DREAMBLOOM_CROP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, ItemNameBlockItem> AMETHYST_HAZE_SEEDS = ITEMS.register("amethyst_haze_seeds",
            () -> new ItemNameBlockItem(ModBlocks.AMETHYST_HAZE_CROP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, ItemNameBlockItem> CLOUDPETAL_SEEDS = ITEMS.register("cloudpetal_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CLOUDPETAL_CROP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, ItemNameBlockItem> CRIMSON_EMBERLEAF_SEEDS = ITEMS.register("crimson_emberleaf_seeds",
            () -> new ItemNameBlockItem(ModBlocks.CRIMSON_EMBERLEAF_CROP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, ItemNameBlockItem> DAYDREAM_BLOOM_SEEDS = ITEMS.register("daydream_bloom_seeds",
            () -> new ItemNameBlockItem(ModBlocks.DAYDREAM_BLOOM_CROP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, ItemNameBlockItem> RUNEGRASS_SEEDS = ITEMS.register("runegrass_seeds",
            () -> new ItemNameBlockItem(ModBlocks.RUNEGRASS_CROP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, ItemNameBlockItem> SKYBUD_SEEDS = ITEMS.register("skybud_seeds",
            () -> new ItemNameBlockItem(ModBlocks.SKYBUD_CROP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, ItemNameBlockItem> DRIFTLEAF_SEEDS = ITEMS.register("driftleaf_seeds",
            () -> new ItemNameBlockItem(ModBlocks.DRIFTLEAF_CROP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, ItemNameBlockItem> MIDNIGHT_GLOW_SEEDS = ITEMS.register("midnight_glow_seeds",
            () -> new ItemNameBlockItem(ModBlocks.MIDNIGHT_GLOW_CROP.get(), new Item.Properties()));
    public static final DeferredHolder<Item, ItemNameBlockItem> ALCHEMIST_ASH_SEEDS = ITEMS.register("alchemist_ash_seeds",
            () -> new ItemNameBlockItem(ModBlocks.ALCHEMIST_ASH_CROP.get(), new Item.Properties()));

    // ==================== HARVESTED SMOKELEAF ====================
    public static final DeferredHolder<Item, Item> DREAMBLOOM = ITEMS.register("dreambloom", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> AMETHYST_HAZE = ITEMS.register("amethyst_haze", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> CLOUDPETAL = ITEMS.register("cloudpetal", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> CRIMSON_EMBERLEAF = ITEMS.register("crimson_emberleaf", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> DAYDREAM_BLOOM = ITEMS.register("daydream_bloom", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> RUNEGRASS = ITEMS.register("runegrass", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> SKYBUD = ITEMS.register("skybud", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> DRIFTLEAF = ITEMS.register("driftleaf", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> MIDNIGHT_GLOW = ITEMS.register("midnight_glow", () -> new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> ALCHEMIST_ASH = ITEMS.register("alchemist_ash", () -> new Item(new Item.Properties()));

    // ==================== WORKSTATION BLOCK ITEMS ====================
    public static final DeferredHolder<Item, Item> COOKIE_OVEN = ITEMS.register("cookie_oven",
            () -> new net.minecraft.world.item.BlockItem(ModBlocks.COOKIE_OVEN.get(), new Item.Properties()));
    public static final DeferredHolder<Item, Item> GERMINATION_TABLE = ITEMS.register("germination_table",
            () -> new net.minecraft.world.item.BlockItem(ModBlocks.GERMINATION_TABLE.get(), new Item.Properties()));

    // ==================== COOKIE TRAY ====================
    public static final DeferredHolder<Item, Item> COOKIE_TRAY = ITEMS.register("cookie_tray",
            () -> new Item(new Item.Properties().stacksTo(16)));

    // ==================== SMOKELEAF POTIONS ====================
    public static final DeferredHolder<Item, Item> EMPTY_SMOKELEAF_POTION = ITEMS.register("empty_smokeleaf_potion",
            () -> new Item(new Item.Properties().stacksTo(16)));

    // Use ResourceKey<Potion> suppliers (matches SmokeleafPotionItem)
    public static final DeferredHolder<Item, Item> CLOUDPETAL_POTION = ITEMS.register("cloudpetal_potion",
            () -> new SmokeleafPotionItem(() -> SmokeleafPotionItem.key(SmokeLeafEG.MODID, "cloudpetal"),
                    new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> DAYDREAM_BLOOM_POTION = ITEMS.register("daydream_bloom_potion",
            () -> new SmokeleafPotionItem(() -> SmokeleafPotionItem.key(SmokeLeafEG.MODID, "daydream_bloom"),
                    new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> CRIMSON_EMBERLEAF_POTION = ITEMS.register("crimson_emberleaf_potion",
            () -> new SmokeleafPotionItem(() -> SmokeleafPotionItem.key(SmokeLeafEG.MODID, "crimson_emberleaf"),
                    new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> DREAMBLOOM_POTION = ITEMS.register("dreambloom_potion",
            () -> new SmokeleafPotionItem(() -> SmokeleafPotionItem.key(SmokeLeafEG.MODID, "dreambloom"),
                    new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> RUNEGRASS_POTION = ITEMS.register("runegrass_potion",
            () -> new SmokeleafPotionItem(() -> SmokeleafPotionItem.key(SmokeLeafEG.MODID, "runegrass"),
                    new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> MIDNIGHT_GLOW_POTION = ITEMS.register("midnight_glow_potion",
            () -> new SmokeleafPotionItem(() -> SmokeleafPotionItem.key(SmokeLeafEG.MODID, "midnight_glow"),
                    new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> AMETHYST_HAZE_POTION = ITEMS.register("amethyst_haze_potion",
            () -> new SmokeleafPotionItem(() -> SmokeleafPotionItem.key(SmokeLeafEG.MODID, "amethyst_haze"),
                    new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> DRIFTLEAF_POTION = ITEMS.register("driftleaf_potion",
            () -> new SmokeleafPotionItem(() -> SmokeleafPotionItem.key(SmokeLeafEG.MODID, "driftleaf"),
                    new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> SKYBUD_POTION = ITEMS.register("skybud_potion",
            () -> new SmokeleafPotionItem(() -> SmokeleafPotionItem.key(SmokeLeafEG.MODID, "skybud"),
                    new Item.Properties().stacksTo(1)));
    public static final DeferredHolder<Item, Item> ALCHEMIST_ASH_POTION = ITEMS.register("alchemist_ash_potion",
            () -> new SmokeleafPotionItem(() -> SmokeleafPotionItem.key(SmokeLeafEG.MODID, "alchemist_ash"),
                    new Item.Properties().stacksTo(1)));

    // ==================== COOKIES WITH EFFECTS (shorter, “tier 2”) ====================
    private static final FoodProperties CLOUDPETAL_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 20 * 60, 1), 1.0F)
            .alwaysEdible().build();
    public static final DeferredHolder<Item, Item> CLOUDPETAL_COOKIE = ITEMS.register("cloudpetal_cookie",
            () -> new Item(new Item.Properties().food(CLOUDPETAL_COOKIE_FOOD).stacksTo(64)));

    private static final FoodProperties DAYDREAM_BLOOM_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.CONDUIT_POWER, 20 * 60, 0), 1.0F)
            .alwaysEdible().build();
    public static final DeferredHolder<Item, Item> DAYDREAM_BLOOM_COOKIE = ITEMS.register("daydream_bloom_cookie",
            () -> new Item(new Item.Properties().food(DAYDREAM_BLOOM_COOKIE_FOOD).stacksTo(64)));

    private static final FoodProperties CRIMSON_EMBERLEAF_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 60, 0), 1.0F)
            .alwaysEdible().build();
    public static final DeferredHolder<Item, Item> CRIMSON_EMBERLEAF_COOKIE = ITEMS.register("crimson_emberleaf_cookie",
            () -> new Item(new Item.Properties().food(CRIMSON_EMBERLEAF_COOKIE_FOOD).stacksTo(64)));

    private static final FoodProperties DREAMBLOOM_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 60, 0), 1.0F)
            .alwaysEdible().build();
    public static final DeferredHolder<Item, Item> DREAMBLOOM_COOKIE = ITEMS.register("dreambloom_cookie",
            () -> new Item(new Item.Properties().food(DREAMBLOOM_COOKIE_FOOD).stacksTo(64)));

    private static final FoodProperties RUNEGRASS_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.LUCK, 20 * 60, 1), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 20 * 60, 0), 1.0F)
            .alwaysEdible().build();
    public static final DeferredHolder<Item, Item> RUNEGRASS_COOKIE = ITEMS.register("runegrass_cookie",
            () -> new Item(new Item.Properties().food(RUNEGRASS_COOKIE_FOOD).stacksTo(64)));

    private static final FoodProperties MIDNIGHT_GLOW_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.INVISIBILITY, 20 * 60, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 20 * 60, 0), 1.0F)
            .alwaysEdible().build();
    public static final DeferredHolder<Item, Item> MIDNIGHT_GLOW_COOKIE = ITEMS.register("midnight_glow_cookie",
            () -> new Item(new Item.Properties().food(MIDNIGHT_GLOW_COOKIE_FOOD).stacksTo(64)));

    private static final FoodProperties AMETHYST_HAZE_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 20 * 60, 1), 1.0F)
            .alwaysEdible().build();
    public static final DeferredHolder<Item, Item> AMETHYST_HAZE_COOKIE = ITEMS.register("amethyst_haze_cookie",
            () -> new Item(new Item.Properties().food(AMETHYST_HAZE_COOKIE_FOOD).stacksTo(64)));

    private static final FoodProperties DRIFTLEAF_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60, 1), 1.0F)
            .alwaysEdible().build();
    public static final DeferredHolder<Item, Item> DRIFTLEAF_COOKIE = ITEMS.register("driftleaf_cookie",
            () -> new Item(new Item.Properties().food(DRIFTLEAF_COOKIE_FOOD).stacksTo(64)));

    private static final FoodProperties SKYBUD_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 20 * 15, 0), 1.0F)
            .alwaysEdible().build();
    public static final DeferredHolder<Item, Item> SKYBUD_COOKIE = ITEMS.register("skybud_cookie",
            () -> new Item(new Item.Properties().food(SKYBUD_COOKIE_FOOD).stacksTo(64)));

    private static final FoodProperties ALCHEMIST_ASH_COOKIE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F)
            .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 20 * 60, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 20 * 60, 0), 1.0F)
            .alwaysEdible().build();
    public static final DeferredHolder<Item, Item> ALCHEMIST_ASH_COOKIE = ITEMS.register("alchemist_ash_cookie",
            () -> new Item(new Item.Properties().food(ALCHEMIST_ASH_COOKIE_FOOD).stacksTo(64)));
}
