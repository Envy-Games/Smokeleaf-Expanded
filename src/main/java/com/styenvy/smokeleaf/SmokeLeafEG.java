package com.styenvy.smokeleaf;

import com.styenvy.smokeleaf.block.ModBlocks;
import com.styenvy.smokeleaf.item.ModItems;
import com.styenvy.smokeleaf.item.ModCreativeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.api.distmarker.Dist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(SmokeLeafEG.MODID)
public class SmokeLeafEG {
    public static final String MODID = "smokeleafeg";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public SmokeLeafEG(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        // Register all deferred registers
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // Register mod event listeners
        modEventBus.addListener(this::commonSetup);

        // Only register client-side events on client
        if (dist.isClient()) {
            modEventBus.addListener(this::clientSetup);
        }

        // DO NOT register to NeoForge.EVENT_BUS unless you have @SubscribeEvent methods
        LOGGER.info("SmokeLeaf Expanded mod initialized!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("SmokeLeaf Expanded common setup complete!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Client setup - render layers are now handled via block properties or model json
        LOGGER.info("SmokeLeaf Expanded client setup complete!");
    }
}