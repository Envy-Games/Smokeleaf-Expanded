package com.styenvy.smokeleaf;

import com.styenvy.smokeleaf.block.ModBlocks;
import com.styenvy.smokeleaf.item.ModCreativeTabs;
import com.styenvy.smokeleaf.item.ModItems;
import com.styenvy.smokeleaf.potion.ModPotions;
import com.styenvy.smokeleaf.villager.ModPOITypes;
import com.styenvy.smokeleaf.villager.ModVillagers;
import com.styenvy.smokeleaf.villager.ModVillagerTrades;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SmokeLeafEG.MODID)
public class SmokeLeafEG {
    public static final String MODID = "smokeleafeg";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public SmokeLeafEG(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        // Deferred registers
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModPOITypes.POI_TYPES.register(modEventBus);
        ModVillagers.VILLAGER_PROFESSIONS.register(modEventBus);
        ModPotions.POTIONS.register(modEventBus);

        // Lifecycle
        modEventBus.addListener(this::commonSetup);
        if (dist.isClient()) modEventBus.addListener(this::clientSetup);

        // GAME-bus listeners (no annotations needed)
        NeoForge.EVENT_BUS.addListener(ModVillagerTrades::onVillagerTrades);

        LOGGER.info("SmokeLeaf Expanded mod initialized!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("SmokeLeaf Expanded common setup complete!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("SmokeLeaf Expanded client setup complete!");
    }
}