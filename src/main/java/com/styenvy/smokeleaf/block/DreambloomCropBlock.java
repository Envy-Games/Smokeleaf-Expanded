package com.styenvy.smokeleaf.block;

import com.styenvy.smokeleaf.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FarmBlock;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DreambloomCropBlock extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty IS_DOUBLE = BooleanProperty.create("is_double");

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),   // Stage 0 - planted
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),  // Stage 1 - middle
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D),  // Stage 2 - tall bottom
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D)   // Stage 3 - flowered bottom
    };

    private static final VoxelShape[] SHAPE_BY_AGE_TOP = new VoxelShape[] {
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),  // Stage 2 - tall top
            Block.box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D)   // Stage 3 - flowered top
    };

    public DreambloomCropBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(IS_DOUBLE, false));
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.DREAMBLOOM_SEEDS.get();
    }

    @Override
    public @NotNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF, IS_DOUBLE);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        int age = this.getAge(state);
        if (age >= 2 && state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return SHAPE_BY_AGE_TOP[age - 2];
        }
        return SHAPE_BY_AGE[Math.min(age, SHAPE_BY_AGE.length - 1)];
    }

    @Override
    public void randomTick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return;

        if (level.getRawBrightness(pos, 0) >= 9) {
            int currentAge = this.getAge(state);
            if (currentAge < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, level, pos);
                if (random.nextInt((int)(25.0F / growthSpeed) + 1) == 0) {
                    this.growCrop(level, pos, state);
                }
            }
        }
    }

    protected void growCrop(Level level, BlockPos pos, BlockState state) {
        int newAge = this.getAge(state) + 1;

        if (newAge >= 2) {
            BlockPos upperPos = pos.above();
            if (level.isEmptyBlock(upperPos)) {
                level.setBlock(upperPos, this.defaultBlockState()
                        .setValue(AGE, newAge)
                        .setValue(HALF, DoubleBlockHalf.UPPER)
                        .setValue(IS_DOUBLE, true), 2);
                level.setBlock(pos, state
                        .setValue(AGE, newAge)
                        .setValue(HALF, DoubleBlockHalf.LOWER)
                        .setValue(IS_DOUBLE, true), 2);
            }
        } else {
            level.setBlock(pos, this.getStateForAge(newAge), 2);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!level.isClientSide) {
            if (state.getValue(IS_DOUBLE)) {
                DoubleBlockHalf half = state.getValue(HALF);
                BlockPos otherPos = half == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
                BlockState otherState = level.getBlockState(otherPos);

                if (otherState.is(this) && otherState.getValue(HALF) != half) {
                    level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
                    level.levelEvent(player, 2001, otherPos, Block.getId(otherState));
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos pos, BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        level.setBlock(pos, state.setValue(AGE, 0).setValue(IS_DOUBLE, false), 3);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return false;
        }
        return !this.isMaxAge(state);
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER || !state.getValue(IS_DOUBLE)) {
            int currentAge = this.getAge(state);
            int bonemealGrowth = this.getBonemealAgeIncrease(level);
            int newAge = Math.min(currentAge + bonemealGrowth, this.getMaxAge());

            if (newAge != currentAge) {
                if (newAge >= 2) {
                    BlockPos upperPos = pos.above();
                    if (level.isEmptyBlock(upperPos)) {
                        level.setBlock(upperPos, this.defaultBlockState()
                                .setValue(AGE, newAge)
                                .setValue(HALF, DoubleBlockHalf.UPPER)
                                .setValue(IS_DOUBLE, true), 2);
                        level.setBlock(pos, state
                                .setValue(AGE, newAge)
                                .setValue(HALF, DoubleBlockHalf.LOWER)
                                .setValue(IS_DOUBLE, true), 2);
                    }
                } else {
                    level.setBlock(pos, this.getStateForAge(newAge), 2);
                }
            }
        }
    }

    protected static float getGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
        float speed = 1.0F;
        BlockPos groundPos = pos.below();

        for(int x = -1; x <= 1; ++x) {
            for(int z = -1; z <= 1; ++z) {
                float bonus = 0.0F;
                BlockState blockstate = level.getBlockState(groundPos.offset(x, 0, z));
                if (blockstate.is(Blocks.FARMLAND)) {
                    bonus = 1.0F;
                    if (blockstate.getValue(FarmBlock.MOISTURE) > 0) {
                        bonus = 3.0F;
                    }
                }

                if (x != 0 || z != 0) {
                    bonus /= 4.0F;
                }

                speed += bonus;
            }
        }

        BlockPos northPos = pos.north();
        BlockPos southPos = pos.south();
        BlockPos westPos = pos.west();
        BlockPos eastPos = pos.east();
        boolean xAxisAdjacent = level.getBlockState(westPos).is(block) || level.getBlockState(eastPos).is(block);
        boolean zAxisAdjacent = level.getBlockState(northPos).is(block) || level.getBlockState(southPos).is(block);

        if (xAxisAdjacent && zAxisAdjacent) {
            speed /= 2.0F;
        } else {
            boolean diagonalAdjacent =
                    level.getBlockState(westPos.north()).is(block) ||
                            level.getBlockState(eastPos.north()).is(block) ||
                            level.getBlockState(eastPos.south()).is(block) ||
                            level.getBlockState(westPos.south()).is(block);
            if (diagonalAdjacent) {
                speed /= 2.0F;
            }
        }

        return speed;
    }
}