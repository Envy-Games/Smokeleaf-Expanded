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

public class RunegrassCropBlock extends CropBlock {
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

    public RunegrassCropBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AGE, 0)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(IS_DOUBLE, false));
    }

    @Override protected @NotNull ItemLike getBaseSeedId() { return ModItems.RUNEGRASS_SEEDS.get(); }
    @Override public @NotNull IntegerProperty getAgeProperty() { return AGE; }
    @Override public int getMaxAge() { return MAX_AGE; }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> b) {
        b.add(AGE, HALF, IS_DOUBLE);
    }

    @Override
    public @NotNull VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
        int age = this.getAge(state);
        if (age >= 2 && state.getValue(HALF) == DoubleBlockHalf.UPPER) return SHAPE_BY_AGE_TOP[age - 2];
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
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
        return super.canSurvive(state, level, pos);
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

    @Override protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
        return state.is(Blocks.FARMLAND);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!level.isClientSide && state.getValue(IS_DOUBLE)) {
            DoubleBlockHalf half = state.getValue(HALF);
            BlockPos otherPos = (half == DoubleBlockHalf.LOWER) ? pos.above() : pos.below();
            BlockState other = level.getBlockState(otherPos);
            if (other.is(this) && other.getValue(HALF) != half) {
                level.setBlock(otherPos, Blocks.AIR.defaultBlockState(), 35);
                level.levelEvent(player, 2001, otherPos, Block.getId(other));
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
        return state.getValue(HALF) != DoubleBlockHalf.UPPER && !this.isMaxAge(state);
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return;
        int age = this.getAge(state);
        int newAge = Math.min(age + this.getBonemealAgeIncrease(level), this.getMaxAge());
        if (newAge != age) applyAge(level, pos, state, newAge);
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, @NotNull LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        int age = this.getAge(state);
        RandomSource rnd = builder.getOptionalParameter(LootContextParams.THIS_ENTITY) != null
                ? Objects.requireNonNull(builder.getOptionalParameter(LootContextParams.THIS_ENTITY)).getRandom()
                : RandomSource.create();

        if (state.getValue(HALF) == DoubleBlockHalf.LOWER || !state.getValue(IS_DOUBLE)) {
            drops.add(new ItemStack(this.getBaseSeedId()));
            if (age >= this.getMaxAge()) {
                int count = 1 + rnd.nextInt(3);
                if (count > 0) drops.add(new ItemStack(ModItems.RUNEGRASS.get(), count));
                if (rnd.nextFloat() < 0.5F) drops.add(new ItemStack(this.getBaseSeedId(), rnd.nextInt(2) + 1));
            }
        }
        return drops;
    }

    private void applyAge(Level level, BlockPos pos, BlockState state, int newAge) {
        newAge = Math.min(newAge, this.getMaxAge());

        // Single-block stages (0–1)
        if (newAge < 2) {
            level.setBlock(pos, this.getStateForAge(newAge)
                    .setValue(HALF, DoubleBlockHalf.LOWER)
                    .setValue(IS_DOUBLE, false), 3);
            return;
        }

        // Double-block stages (2–3)
        BlockPos upperPos = pos.above();
        BlockState upperState = level.getBlockState(upperPos);
        boolean hasUpper = upperState.is(this) && upperState.getValue(HALF) == DoubleBlockHalf.UPPER;

        if (!hasUpper) {
            if (!level.isEmptyBlock(upperPos) && !upperState.is(this)) {
                if (level instanceof ServerLevel sl) sl.scheduleTick(pos, this, 2);
                return;
            }
            level.setBlock(upperPos, this.defaultBlockState()
                    .setValue(AGE, newAge)
                    .setValue(HALF, DoubleBlockHalf.UPPER)
                    .setValue(IS_DOUBLE, true), 3);
        } else {
            level.setBlock(upperPos, upperState
                    .setValue(AGE, newAge)
                    .setValue(IS_DOUBLE, true), 3);
        }

        level.setBlock(pos, state
                .setValue(AGE, newAge)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(IS_DOUBLE, true), 3);

        if (level instanceof ServerLevel sl) {
            sl.getChunkSource().getLightEngine().checkBlock(pos);
            sl.getChunkSource().getLightEngine().checkBlock(upperPos);
        }
    }

    protected static float getGrowthSpeed(Block block, BlockGetter level, BlockPos pos) {
        float speed = 1.0F;
        BlockPos ground = pos.below();

        for (int x = -1; x <= 1; ++x) for (int z = -1; z <= 1; ++z) {
            float bonus = 0.0F;
            BlockState soil = level.getBlockState(ground.offset(x, 0, z));
            if (soil.is(Blocks.FARMLAND)) {
                bonus = 1.0F;
                if (soil.getValue(FarmBlock.MOISTURE) > 0) bonus = 3.0F;
            }
            if (x != 0 || z != 0) bonus /= 4.0F;
            speed += bonus;
        }

        boolean xAdj = level.getBlockState(pos.west()).is(block) || level.getBlockState(pos.east()).is(block);
        boolean zAdj = level.getBlockState(pos.north()).is(block) || level.getBlockState(pos.south()).is(block);

        if (xAdj && zAdj) speed /= 2.0F;
        else {
            boolean diag = level.getBlockState(pos.west().north()).is(block)
                    || level.getBlockState(pos.east().north()).is(block)
                    || level.getBlockState(pos.east().south()).is(block)
                    || level.getBlockState(pos.west().south()).is(block);
            if (diag) speed /= 2.0F;
        }
        return speed;
    }
}