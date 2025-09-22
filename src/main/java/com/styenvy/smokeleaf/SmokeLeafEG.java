package com.styenvy.smokeleaf;

import com.styenvy.smokeleaf.block.ModBlocks;
import com.styenvy.smokeleaf.item.ModItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
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
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        if (dist.isClient()) {
            modEventBus.addListener(this::clientSetup);
        }

        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("SmokeLeaf Expanded initializing...");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.DREAMBLOOM_CROP.get(), RenderType.cutout());
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModItems.DREAMBLOOM_SEEDS.get());
        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.DREAMBLOOM.get());
            event.accept(ModItems.DREAMBLOOM_SEEDS.get());
        }
    }
}