package com.upo.createmechanicalconfection.content.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


public abstract class BaseCakeBlock extends Block {

    public static final IntegerProperty BITES = BlockStateProperties.BITES;

    protected final int maxBites;
    protected final VoxelShape[] shapes;

    protected BaseCakeBlock(BlockBehaviour.Properties properties, int maxBites, VoxelShape[] shapes) {
        super(properties);
        if (shapes.length != maxBites + 1) {
            throw new IllegalArgumentException("Shapes array length must be equal to maxBites + 1");
        }
        this.maxBites = maxBites;
        this.shapes = shapes;
        this.registerDefaultState(this.stateDefinition.any().setValue(BITES, 0));
    }


    public int getTotalStates() {
        return this.maxBites + 1;
    }



    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int biteIndex = Math.min(state.getValue(BITES), this.maxBites);
        return (biteIndex >= 0 && biteIndex < this.shapes.length) ? this.shapes[biteIndex] : this.shapes[0];
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return player.canEat(false) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
        return consumeBite(level, pos, state, player);
    }

    protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        }

        player.awardStat(Stats.EAT_CAKE_SLICE);
        player.getFoodData().eat(getHungerPerBite(), getSaturationPerBite());
        level.playSound(null, pos, getEatSound(), SoundSource.PLAYERS, 0.8F, 0.8F);
        level.gameEvent(player, GameEvent.EAT, pos);

        int currentBites = state.getValue(BITES);
        if (currentBites == this.maxBites -1) {
            // 直接移除方块
            level.removeBlock(pos, false);
            level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        } else if (currentBites < this.maxBites) {
            level.setBlock(pos, state.setValue(BITES, currentBites + 1), 3);
        } else {
            return InteractionResult.PASS;
        }

        return InteractionResult.SUCCESS;
    }


    protected int getHungerPerBite() {
        return 2;
    }
    protected float getSaturationPerBite() {
        return 0.1F;
    }

    protected SoundEvent getEatSound() {
        return SoundEvents.GENERIC_EAT;
    }


    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !stateIn.canSurvive(level, currentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        return (getTotalStates() - blockState.getValue(BITES)) * 2;
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}

