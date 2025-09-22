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
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    /**
     * Ensures growth updates both halves when transitioning into/within double stages.
     */
    protected void growCrop(Level level, BlockPos pos, BlockState state) {
        int currentAge = this.getAge(state);
        int newAge = Math.min(currentAge + 1, this.getMaxAge());

        // Single-block stages (0–1)
        if (newAge < 2) {
            level.setBlock(pos, this.getStateForAge(newAge), 2);
            return;
        }

        // Double-block stages (2–3)
        BlockPos upperPos = pos.above();
        BlockState upperState = level.getBlockState(upperPos);
        boolean hasUpper = upperState.is(this) && upperState.getValue(HALF) == DoubleBlockHalf.UPPER;

        if (!hasUpper) {
            if (!level.isEmptyBlock(upperPos)) {
                return; // blocked above; don't partially update
            }
            level.setBlock(upperPos, this.defaultBlockState()
                    .setValue(AGE, newAge)
                    .setValue(HALF, DoubleBlockHalf.UPPER)
                    .setValue(IS_DOUBLE, true), 2);
        } else {
            level.setBlock(upperPos, upperState
                    .setValue(AGE, newAge)
                    .setValue(IS_DOUBLE, true), 2);
        }

        level.setBlock(pos, state
                .setValue(AGE, newAge)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(IS_DOUBLE, true), 2);
    }

    @Override
    public boolean canSurvive(BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
        return super.canSurvive(state, level, pos);
    }

    /**
     * Keep halves synchronized for any neighbor change (pistons, explosions, etc.).
     */
    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState,
                                           @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        int age = this.getAge(state);

        // If we're in a double stage (age >= 2), enforce paired halves.
        if (age >= 2 && state.getValue(IS_DOUBLE)) {
            if (direction == Direction.UP && half == DoubleBlockHalf.LOWER) {
                if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.UPPER) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
            if (direction == Direction.DOWN && half == DoubleBlockHalf.UPPER) {
                if (!neighborState.is(this) || neighborState.getValue(HALF) != DoubleBlockHalf.LOWER) {
                    return Blocks.AIR.defaultBlockState();
                }
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
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
        level.setBlock(pos, state.setValue(AGE, 0).setValue(IS_DOUBLE, false).setValue(HALF, DoubleBlockHalf.LOWER), 3);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return false;
        return !this.isMaxAge(state);
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, BlockState state) {
        // Only act from lower or single-block
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return;

        int currentAge = this.getAge(state);
        int newAge = Math.min(currentAge + this.getBonemealAgeIncrease(level), this.getMaxAge());
        if (newAge == currentAge) return;

        if (newAge < 2) {
            level.setBlock(pos, this.getStateForAge(newAge), 2);
            return;
        }

        BlockPos upperPos = pos.above();
        BlockState upperState = level.getBlockState(upperPos);
        boolean hasUpper = upperState.is(this) && upperState.getValue(HALF) == DoubleBlockHalf.UPPER;

        if (!hasUpper) {
            if (!level.isEmptyBlock(upperPos)) return; // blocked above
            level.setBlock(upperPos, this.defaultBlockState()
                    .setValue(AGE, newAge)
                    .setValue(HALF, DoubleBlockHalf.UPPER)
                    .setValue(IS_DOUBLE, true), 2);
        } else {
            level.setBlock(upperPos, upperState
                    .setValue(AGE, newAge)
                    .setValue(IS_DOUBLE, true), 2);
        }

        level.setBlock(pos, state
                .setValue(AGE, newAge)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(IS_DOUBLE, true), 2);
    }

    // Handle drops in-code (you can swap to a loot table later if preferred).
    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, @NotNull LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        int age = this.getAge(state);
        RandomSource random = builder.getOptionalParameter(LootContextParams.THIS_ENTITY) != null
                ? Objects.requireNonNull(builder.getOptionalParameter(LootContextParams.THIS_ENTITY)).getRandom()
                : RandomSource.create();

        // Only drop from the lower half of double blocks, or from single blocks
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER || !state.getValue(IS_DOUBLE)) {
            // Always drop at least 1 seed
            drops.add(new ItemStack(this.getBaseSeedId()));

            // If fully grown, drop dreambloom flowers
            if (age >= this.getMaxAge()) {
                int count = 1 + random.nextInt(3); // 1-3 dreambloom flowers
                if (count > 0) {
                    drops.add(new ItemStack(ModItems.DREAMBLOOM.get(), count));
                }
                // Chance for extra seeds when fully grown
                if (random.nextFloat() < 0.5F) {
                    drops.add(new ItemStack(this.getBaseSeedId(), random.nextInt(2) + 1));
                }
            }
        }

        return drops;
    }

    /**
     * Vanilla-like growth speed computation (copied/adapted crop logic).
     */
    protected static float getGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
        float speed = 1.0F;
        BlockPos groundPos = pos.below();

        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
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
