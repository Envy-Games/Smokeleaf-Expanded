package com.styenvy.smokeleaf.item;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/**
 * Custom potion item that
 * 1) ensures its stack carries the correct PotionContents based on a registry key
 * 2) applies effects via the vanilla PotionItem flow
 * 3) swaps the empty glass bottle remainder for our custom empty bottle in Survival
 *
 * For NeoForge / Minecraft 1.21.1.
 */
public class SmokeleafPotionItem extends PotionItem {

    private final Supplier<ResourceKey<Potion>> potionKey;

    public SmokeleafPotionItem(Supplier<ResourceKey<Potion>> potionKey, Item.Properties props) {
        super(props);
        this.potionKey = potionKey;
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        Holder<Potion> holder = BuiltInRegistries.POTION.getHolderOrThrow(potionKey.get());
        stack.set(DataComponents.POTION_CONTENTS, new PotionContents(holder));
        return stack;
    }

    /**
     * Before delegating to vanilla, make sure the stack actually has PotionContents set.
     * After vanilla handles drinking/effects, swap any glass bottle outputs to our custom empty bottle.
     */
    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull net.minecraft.world.entity.LivingEntity entity) {
        // Defensive: if some recipe/command produced this item without contents, inject them now.
        if (stack.get(DataComponents.POTION_CONTENTS) == null) {
            Holder<Potion> holder = BuiltInRegistries.POTION.getHolderOrThrow(potionKey.get());
            stack.set(DataComponents.POTION_CONTENTS, new PotionContents(holder));
        }

        // Let vanilla apply the effects & do the normal consumption flow.
        ItemStack after = super.finishUsingItem(stack, level, entity);

        // Post-process the remainder to replace glass bottle with our empty bottle.
        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            // Case 1: vanilla returned a single glass bottle (when the stack was size 1)
            if (after.getItem() == Items.GLASS_BOTTLE) {
                return new ItemStack(ModItems.EMPTY_SMOKELEAF_POTION.get());
            }

            // Case 2: vanilla kept the stack and *added* a glass bottle to inventory (when stack size > 1)
            int slot = player.getInventory().findSlotMatchingItem(new ItemStack(Items.GLASS_BOTTLE));
            if (slot >= 0) {
                ItemStack invStack = player.getInventory().getItem(slot);
                if (!invStack.isEmpty()) {
                    invStack.shrink(1);
                    ItemStack customEmpty = new ItemStack(ModItems.EMPTY_SMOKELEAF_POTION.get());
                    if (!player.getInventory().add(customEmpty)) {
                        player.drop(customEmpty, false);
                    }
                }
            }
        }

        return after;
    }

    /** Helper to create a ResourceKey<Potion> for this mod's potion ids. */
    public static ResourceKey<Potion> key(String modid, String path) {
        return ResourceKey.create(
                Registries.POTION,
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(modid, path)
        );
    }
}