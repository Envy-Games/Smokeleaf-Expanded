package com.styenvy.smokeleaf.block;

import com.styenvy.smokeleaf.SmokeLeafEG;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(BuiltInRegistries.BLOCK, SmokeLeafEG.MODID);

    public static final DeferredHolder<Block, DreambloomCropBlock> DREAMBLOOM_CROP = BLOCKS.register("dreambloom_crop",
            () -> new DreambloomCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredHolder<Block, AmethystHazeCropBlock> AMETHYST_HAZE_CROP = BLOCKS.register("amethyst_haze_crop",
            () -> new AmethystHazeCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredHolder<Block, CloudpetalCropBlock> CLOUDPETAL_CROP = BLOCKS.register("cloudpetal_crop",
            () -> new CloudpetalCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredHolder<Block, CrimsonEmberleafCropBlock> CRIMSON_EMBERLEAF_CROP = BLOCKS.register("crimson_emberleaf_crop",
            () -> new CrimsonEmberleafCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredHolder<Block, DaydreamBloomCropBlock> DAYDREAM_BLOOM_CROP = BLOCKS.register("daydream_bloom_crop",
            () -> new DaydreamBloomCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredHolder<Block, RunegrassCropBlock> RUNEGRASS_CROP = BLOCKS.register("runegrass_crop",
            () -> new RunegrassCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredHolder<Block, SkybudCropBlock> SKYBUD_CROP = BLOCKS.register("skybud_crop",
            () -> new SkybudCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredHolder<Block, DriftleafCropBlock> DRIFTLEAF_CROP = BLOCKS.register("driftleaf_crop",
            () -> new DriftleafCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredHolder<Block, MidnightGlowCropBlock> MIDNIGHT_GLOW_CROP = BLOCKS.register("midnight_glow_crop",
            () -> new MidnightGlowCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));

    public static final DeferredHolder<Block, AlchemistAshCropBlock> ALCHEMIST_ASH_CROP = BLOCKS.register("alchemist_ash_crop",
            () -> new AlchemistAshCropBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT)
                    .noCollission()
                    .noOcclusion()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY)));
}