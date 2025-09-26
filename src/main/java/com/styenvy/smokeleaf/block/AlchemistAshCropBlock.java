// src/main/java/com/styenvy/smokeleaf/block/AlchemistAshCropBlock.java
package com.styenvy.smokeleaf.block;

import com.styenvy.smokeleaf.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlchemistAshCropBlock extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty IS_DOUBLE = BooleanProperty.create("is_double");

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),   // 0
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),  // 1
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D),  // 2 (lower)
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)   // 3 (lower)
    };

    private static final VoxelShape[] SHAPE_BY_AGE_TOP = new VoxelShape[] {
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),  // 2 (upper)
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)   // 3 (upper)
    };

    public AlchemistAshCropBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(IS_DOUBLE, false));
    }

    @Override protected @NotNull ItemLike getBaseSeedId() { return ModItems.ALCHEMIST_ASH_SEEDS.get(); }
    @Override public @NotNull IntegerProperty getAgeProperty() { return AGE; }
    @Override public int getMaxAge() { return MAX_AGE; }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        b.add(AGE, HALF, IS_DOUBLE);
    }

    // Explicitly non-occluding to avoid lighting artifacts.
    @Override
    public @NotNull VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        int age = this.getAge(state);
        boolean isUpper = state.getValue(HALF) == DoubleBlockHalf.UPPER;
        if (isUpper) {
            // Upper half only exists for ages 2–3; map 2->0, 3->1 in top array
            int idx = Math.max(0, Math.min(1, age - 2));
            return SHAPE_BY_AGE_TOP[idx];
        }
        return SHAPE_BY_AGE[Math.min(age, SHAPE_BY_AGE.length - 1)];
    }

    @Override
    public void randomTick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return;

        if (level.getRawBrightness(pos, 0) >= 9) {
            int age = this.getAge(state);
            if (age < this.getMaxAge()) {
                float speed = getGrowthSpeed(this, level, pos);
                if (random.nextInt((int)(25.0F / speed) + 1) == 0) {
                    applyAge(level, pos, state, age + 1);
                }
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER && below.getValue(IS_DOUBLE);
        }
        // Lower half must be on farmland and not submerged
        BlockState below = level.getBlockState(pos.below());
        boolean onFarmland = below.getBlock() instanceof FarmBlock;
        return onFarmland && level.getRawBrightness(pos, 0) >= 8;
    }

    @Override protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.getBlock() instanceof FarmBlock;
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!level.isClientSide) {
            if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
                BlockPos abovePos = pos.above();
                BlockState above = level.getBlockState(abovePos);
                if (above.is(this) && above.getValue(HALF) == DoubleBlockHalf.UPPER) {
                    level.destroyBlock(abovePos, false, player);
                }
            } else {
                BlockPos belowPos = pos.below();
                BlockState below = level.getBlockState(belowPos);
                if (below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER) {
                    level.destroyBlock(belowPos, false, player);
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos pos, BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        // No special placement beyond default crop placement; upper half is created as the plant grows.
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return false;
        return this.getAge(state) < this.getMaxAge();
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return;
        int age = this.getAge(state);
        int inc = 1 + random.nextInt(2); // 1–2
        int newAge = Math.min(this.getMaxAge(), age + inc);
        applyAge(level, pos, state, newAge);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, @NotNull LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        final int age = this.getAge(state);

        RandomSource rnd = builder.getOptionalParameter(LootContextParams.THIS_ENTITY) != null
                ? Objects.requireNonNull(builder.getOptionalParameter(LootContextParams.THIS_ENTITY)).getRandom()
                : RandomSource.create();

        // Always at least one seed (consistent for testing and farming loops)
        drops.add(new ItemStack(this.getBaseSeedId()));

        if (age >= this.getMaxAge()) {
            // Mature: drop harvested item(s) + bonus seed chance mirrored from Dreambloom pattern
            int count = 1 + rnd.nextInt(2); // 1–2
            drops.add(new ItemStack(ModItems.ALCHEMIST_ASH.get(), count));
            if (rnd.nextFloat() < 0.5F) {
                drops.add(new ItemStack(this.getBaseSeedId()));
            }
        }

        return drops;
    }

    private void applyAge(Level level, BlockPos pos, BlockState state, int newAge) {
        boolean wasDouble = state.getValue(IS_DOUBLE);
        boolean shouldBeDouble = newAge >= 2;

        // Update lower
        BlockState newLower = state.setValue(AGE, newAge).setValue(HALF, DoubleBlockHalf.LOWER).setValue(IS_DOUBLE, shouldBeDouble);
        level.setBlock(pos, newLower, Block.UPDATE_ALL);

        BlockPos abovePos = pos.above();
        if (shouldBeDouble) {
            BlockState above = level.getBlockState(abovePos);
            // Place or update upper
            if (!above.is(this) || above.getValue(HALF) != DoubleBlockHalf.UPPER) {
                level.setBlock(abovePos, this.defaultBlockState()
                        .setValue(AGE, newAge)
                        .setValue(HALF, DoubleBlockHalf.UPPER)
                        .setValue(IS_DOUBLE, true), Block.UPDATE_ALL);
            } else {
                level.setBlock(abovePos, above.setValue(AGE, newAge).setValue(IS_DOUBLE, true), Block.UPDATE_ALL);
            }
        } else if (wasDouble) {
            // Remove upper if present
            BlockState above = level.getBlockState(abovePos);
            if (above.is(this) && above.getValue(HALF) == DoubleBlockHalf.UPPER) {
                level.setBlock(abovePos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
            }
        }
    }

    // Copied vanilla logic (slightly adapted) for growth speed weighting
    protected static float getGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
        float f = 1.0F;
        BlockPos belowPos = pos.below();

        for (int dx = -1; dx <= 1; ++dx) {
            for (int dz = -1; dz <= 1; ++dz) {
                float local = 0.0F;
                BlockPos soilPos = belowPos.offset(dx, 0, dz);
                BlockState soil = level.getBlockState(soilPos);

                if (soil.getBlock() instanceof FarmBlock) {
                    local = 1.0F;
                    if (soil.getValue(FarmBlock.MOISTURE) > 0) {
                        local = 3.0F;
                    }
                }

                if (dx != 0 || dz != 0) {
                    local /= 4.0F;
                }

                f += local;
            }
        }

        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
        boolean sameRow = level.getBlockState(west).is(block) || level.getBlockState(east).is(block);
        boolean sameCol = level.getBlockState(north).is(block) || level.getBlockState(south).is(block);
        if (sameRow && sameCol) {
            f /= 2.0F;
        }

        return f;
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction dir, @NotNull BlockState neighbor,
                                           @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (state.getValue(IS_DOUBLE)) {
            if (dir == Direction.UP && half == DoubleBlockHalf.LOWER) {
                if (!neighbor.is(this) || neighbor.getValue(HALF) != DoubleBlockHalf.UPPER) return Blocks.AIR.defaultBlockState();
            }
            if (dir == Direction.DOWN && half == DoubleBlockHalf.UPPER) {
                if (!neighbor.is(this) || neighbor.getValue(HALF) != DoubleBlockHalf.LOWER) return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(state, dir, neighbor, level, pos, neighborPos);
    }
}
