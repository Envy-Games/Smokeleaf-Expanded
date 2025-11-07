package com.styenvy.smokeleaf.villager;

import com.google.common.collect.ImmutableSet;
import com.styenvy.smokeleaf.SmokeLeafEG;
import com.styenvy.smokeleaf.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;

public class ModPOITypes {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, SmokeLeafEG.MODID);

    public static final DeferredHolder<PoiType, PoiType> COOKIE_OVEN_POI = POI_TYPES.register("cookie_oven_poi",
            () -> new PoiType(
                    getAllStates(ModBlocks.COOKIE_OVEN.get()),
                    1, // max tickets
                    16  // valid range
            ));

    public static final DeferredHolder<PoiType, PoiType> GERMINATION_TABLE_POI = POI_TYPES.register("germination_table_poi",
            () -> new PoiType(
                    getAllStates(ModBlocks.GERMINATION_TABLE.get()),
                    1, // max tickets
                    16  // valid range
            ));

    private static Set<BlockState> getAllStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
