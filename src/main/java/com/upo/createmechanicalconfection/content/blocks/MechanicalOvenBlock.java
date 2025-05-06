package com.upo.createmechanicalconfection.content.blocks;

import com.mojang.serialization.MapCodec;
import com.upo.createmechanicalconfection.content.CMCBlockEntities;
import com.upo.createmechanicalconfection.content.block_entities.MechanicalOvenBlockEntity;
import com.upo.createmechanicalconfection.data.ModBlockTagsProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class MechanicalOvenBlock extends BaseEntityBlock {

    public static final MapCodec<MechanicalOvenBlock> CODEC = simpleCodec(MechanicalOvenBlock::new);
    @Override protected MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }

    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MechanicalOvenBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(FACING, Direction.NORTH));
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CMCBlockEntities.MECHANICAL_OVEN_BE.get().create(pos, state);
    }

    @Nullable @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, CMCBlockEntities.MECHANICAL_OVEN_BE.get(), MechanicalOvenBlockEntity::serverTick);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (hand != InteractionHand.MAIN_HAND) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        boolean isCakeItem = false;
        Item heldItem = stack.getItem();
        if (heldItem instanceof BlockItem blockItem) {
            Block associatedBlock = blockItem.getBlock();
            BlockState associatedState = associatedBlock.defaultBlockState();

            if (associatedState.is(ModBlockTagsProvider.CAKES)) {
                isCakeItem = true;
            }
        }

        if (isCakeItem) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof MechanicalOvenBlockEntity ovenBE) {
                ItemStackHandler inventory = ovenBE.inventory;
                ItemStack remainder = inventory.insertItem(MechanicalOvenBlockEntity.INPUT_SLOT, stack.copy(), false);
                if (remainder.getCount() < stack.getCount()) {
                    if (!level.isClientSide) {
                        if (!player.getAbilities().instabuild) {
                            player.setItemInHand(hand, remainder);
                        }
                        level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 0.7f, 1.0f);
                    }
                    return ItemInteractionResult.sidedSuccess(level.isClientSide);
                } else {
                    return ItemInteractionResult.FAIL;
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof MechanicalOvenBlockEntity ovenBE) {
            ItemStack extracted = ovenBE.takeResultItem();
            if (!extracted.isEmpty()) {
                if (!level.isClientSide) {
                    player.getInventory().placeItemBackInInventory(extracted);
                    level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.5f, 1f);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

}

