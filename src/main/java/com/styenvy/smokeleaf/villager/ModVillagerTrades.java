package com.styenvy.smokeleaf.villager;

import com.styenvy.smokeleaf.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.ArrayList;
import java.util.List;

public class ModVillagerTrades {

    private static List<VillagerTrades.ItemListing> ensureLevel(Int2ObjectMap<List<VillagerTrades.ItemListing>> trades, int level) {
        List<VillagerTrades.ItemListing> list = trades.get(level);
        if (list == null) {
            list = new ArrayList<>();
            trades.put(level, list);
        }
        return list;
    }

    private static void sellForEmeralds(List<VillagerTrades.ItemListing> list, ItemLike item, int count, int emeraldCost, int maxUses, int xp) {
        list.add((trader, rand) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, emeraldCost),
                new ItemStack(item, count),
                maxUses, xp, 0.02F));
    }

    private static void buyForEmeralds(List<VillagerTrades.ItemListing> list, ItemLike wantedItem, int wantedCount, int emeraldsGiven, int maxUses, int xp) {
        list.add((trader, rand) -> new MerchantOffer(
                new ItemCost(wantedItem, wantedCount),
                new ItemStack(Items.EMERALD, emeraldsGiven),
                maxUses, xp, 0.02F));
    }

    // Listener method (registered from SmokeLeafEG)
    public static void onVillagerTrades(final VillagerTradesEvent event) {

        // Dealer
        if (event.getType() == ModVillagers.SMOKELEAF_DEALER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            sellForEmeralds(ensureLevel(trades, 1), ModItems.COOKIE_TRAY.get(), 1, 3, 12, 2);
            buyForEmeralds(ensureLevel(trades, 1), Items.WHEAT, 20, 1, 16, 2);

            // Level 2
            sellForEmeralds(ensureLevel(trades, 2), ModItems.DREAMBLOOM_COOKIE.get(), 4, 2, 12, 5);
            sellForEmeralds(ensureLevel(trades, 2), ModItems.AMETHYST_HAZE_COOKIE.get(), 4, 2, 12, 5);

            // Level 3
            sellForEmeralds(ensureLevel(trades, 3), ModItems.CLOUDPETAL_COOKIE.get(), 3, 3, 12, 10);
            sellForEmeralds(ensureLevel(trades, 3), ModItems.DRIFTLEAF_COOKIE.get(), 3, 3, 12, 10);
            buyForEmeralds(ensureLevel(trades, 3), ModItems.DREAMBLOOM.get(), 12, 1, 12, 10);

            // Level 4
            sellForEmeralds(ensureLevel(trades, 4), ModItems.CRIMSON_EMBERLEAF_COOKIE.get(), 2, 4, 12, 15);
            sellForEmeralds(ensureLevel(trades, 4), ModItems.MIDNIGHT_GLOW_COOKIE.get(), 2, 4, 12, 15);
            sellForEmeralds(ensureLevel(trades, 4), ModItems.ALCHEMIST_ASH_COOKIE.get(), 2, 5, 12, 15);

            // Level 5
            sellForEmeralds(ensureLevel(trades, 5), ModItems.RUNEGRASS_COOKIE.get(), 1, 8, 3, 30);
            sellForEmeralds(ensureLevel(trades, 5), ModItems.DAYDREAM_BLOOM_COOKIE.get(), 1, 12, 3, 30);
            sellForEmeralds(ensureLevel(trades, 5), ModItems.SKYBUD_COOKIE.get(), 3, 6, 6, 30);
        }

        // Farmer
        if (event.getType() == ModVillagers.SMOKELEAF_FARMER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
            sellForEmeralds(ensureLevel(trades, 1), ModItems.DREAMBLOOM_SEEDS.get(), 4, 1, 16, 2);
            buyForEmeralds(ensureLevel(trades, 1), ModItems.DREAMBLOOM.get(), 20, 1, 16, 2);

            // Level 2
            sellForEmeralds(ensureLevel(trades, 2), ModItems.AMETHYST_HAZE_SEEDS.get(), 3, 1, 12, 5);
            sellForEmeralds(ensureLevel(trades, 2), ModItems.CLOUDPETAL_SEEDS.get(), 3, 1, 12, 5);
            buyForEmeralds(ensureLevel(trades, 2), ModItems.AMETHYST_HAZE.get(), 15, 1, 12, 5);

            // Level 3
            sellForEmeralds(ensureLevel(trades, 3), ModItems.CRIMSON_EMBERLEAF_SEEDS.get(), 2, 2, 12, 10);
            sellForEmeralds(ensureLevel(trades, 3), ModItems.DRIFTLEAF_SEEDS.get(), 2, 2, 12, 10);
            sellForEmeralds(ensureLevel(trades, 3), ModItems.SKYBUD_SEEDS.get(), 2, 2, 12, 10);

            // Level 4
            sellForEmeralds(ensureLevel(trades, 4), ModItems.MIDNIGHT_GLOW_SEEDS.get(), 1, 3, 8, 15);
            sellForEmeralds(ensureLevel(trades, 4), ModItems.ALCHEMIST_ASH_SEEDS.get(), 1, 3, 8, 15);
            buyForEmeralds(ensureLevel(trades, 4), ModItems.MIDNIGHT_GLOW.get(), 8, 1, 12, 15);

            // Level 5
            sellForEmeralds(ensureLevel(trades, 5), ModItems.RUNEGRASS_SEEDS.get(), 1, 5, 3, 30);
            sellForEmeralds(ensureLevel(trades, 5), ModItems.DAYDREAM_BLOOM_SEEDS.get(), 1, 8, 3, 30);
            buyForEmeralds(ensureLevel(trades, 5), ModItems.RUNEGRASS.get(), 3, 2, 12, 30);
            buyForEmeralds(ensureLevel(trades, 5), ModItems.DAYDREAM_BLOOM.get(), 2, 3, 12, 30);
        }
    }
}