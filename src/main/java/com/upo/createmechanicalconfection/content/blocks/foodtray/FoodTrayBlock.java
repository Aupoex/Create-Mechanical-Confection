package com.upo.createmechanicalconfection.content.blocks.foodtray;

import com.mojang.serialization.MapCodec;
import com.upo.createmechanicalconfection.content.CMCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FoodTrayBlock extends BaseEntityBlock {

    public static final MapCodec<FoodTrayBlock> CODEC = simpleCodec(FoodTrayBlock::new);
    @Override protected MapCodec<? extends BaseEntityBlock> codec() { return CODEC; }

    public static final EnumProperty<FoodTrayItemType> ITEM_TYPE =
            EnumProperty.create("item_type", FoodTrayItemType.class);
    public static final IntegerProperty STACK_COUNT =
            IntegerProperty.create("stack_count", 0, 4);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public FoodTrayBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(ITEM_TYPE, FoodTrayItemType.NONE)
                .setValue(STACK_COUNT, 0)
                .setValue(FACING, Direction.NORTH)
        );

    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CMCBlockEntities.FOOD_TRAY_BE.get().create(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (hand != InteractionHand.MAIN_HAND) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof FoodTrayBlockEntity trayBE)) {
            return ItemInteractionResult.FAIL;
        }

        if (!heldStack.isEmpty()) {
            FoodTrayItemType heldItemType = FoodTrayItemType.fromItem(heldStack.getItem());
            FoodTrayItemType currentTrayItemType = state.getValue(ITEM_TYPE);
            int currentStackCount = state.getValue(STACK_COUNT);

            if (heldItemType == FoodTrayItemType.NONE) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }

            int maxStacksForItem = heldItemType.getMaxStacksOnTray();
            if (currentTrayItemType == heldItemType && currentStackCount >= maxStacksForItem) {
                return ItemInteractionResult.FAIL;
            }
            if (currentTrayItemType != FoodTrayItemType.NONE && currentTrayItemType != heldItemType && maxStacksForItem == 0) {
                return ItemInteractionResult.FAIL;
            }
            if (currentTrayItemType == FoodTrayItemType.NONE && maxStacksForItem == 0) {
                return ItemInteractionResult.FAIL;
            }

            if (currentTrayItemType == FoodTrayItemType.NONE || currentTrayItemType == heldItemType) {
                if (currentStackCount < maxStacksForItem) {
                    if (!level.isClientSide) {
                        int newStackCount = currentStackCount + 1;
                        trayBE.setStoredItem(heldItemType, newStackCount);
                        level.setBlock(pos, state.setValue(ITEM_TYPE, heldItemType).setValue(STACK_COUNT, newStackCount), 3);
                        if (!player.getAbilities().instabuild) {
                            heldStack.shrink(1);
                        }
                        level.playSound(null, pos, SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                    }
                    return ItemInteractionResult.sidedSuccess(level.isClientSide);
                }
            } else {
                return ItemInteractionResult.FAIL;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof FoodTrayBlockEntity trayBE)) {
                return InteractionResult.FAIL;
            }

            FoodTrayItemType currentTrayItemType = state.getValue(ITEM_TYPE);
            int currentStackCount = state.getValue(STACK_COUNT);

            if (currentTrayItemType != FoodTrayItemType.NONE && currentStackCount > 0) {
                if (!level.isClientSide) {
                    Item itemToGive = currentTrayItemType.getRepresentativeItem();
                    if (itemToGive != null) {
                        player.getInventory().placeItemBackInInventory(new ItemStack(itemToGive));
                    }

                    int newStackCount = currentStackCount - 1;
                    if (newStackCount == 0) {
                        trayBE.setStoredItem(FoodTrayItemType.NONE, 0);
                        level.setBlock(pos, state.setValue(ITEM_TYPE, FoodTrayItemType.NONE).setValue(STACK_COUNT, 0), 3);
                    } else {
                        trayBE.setStoredItem(currentTrayItemType, newStackCount);
                        level.setBlock(pos, state.setValue(STACK_COUNT, newStackCount), 3);
                    }
                    level.playSound(null, pos, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ITEM_TYPE, STACK_COUNT, FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof FoodTrayBlockEntity trayBE) {
                FoodTrayItemType itemType = trayBE.getStoredItemType();
                int count = trayBE.getStoredCount();
                Item item = itemType.getRepresentativeItem();

                if (item != null && count > 0) {
                    for (int i = 0; i < count; i++) {
                        level.addFreshEntity(new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(item)));
                    }
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D);
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}

