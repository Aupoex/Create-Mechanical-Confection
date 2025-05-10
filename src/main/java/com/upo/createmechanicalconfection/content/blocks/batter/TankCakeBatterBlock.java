package com.upo.createmechanicalconfection.content.blocks.batter;

import com.upo.createmechanicalconfection.content.CMCBlocks;
import com.upo.createmechanicalconfection.content.blocks.BaseCakeBatterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TankCakeBatterBlock extends BaseCakeBatterBlock {
    public TankCakeBatterBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    @Override
    protected ItemInteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hit) {
        if (heldStack.is(Items.MAGMA_CREAM)) {
            Block filledBlock = CMCBlocks.FILLED_TANK_CAKE_BATTER_BLOCK.get();

            if (filledBlock != null) {
                if (!level.isClientSide) {
                    level.setBlock(pos, filledBlock.defaultBlockState(), Block.UPDATE_ALL);
                    if (!player.getAbilities().instabuild) {
                        heldStack.shrink(1);
                    }
                    level.playSound(null, pos, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return ItemInteractionResult.sidedSuccess(true);
                } else {
                    return ItemInteractionResult.sidedSuccess(true);
                }
            } else {
                return ItemInteractionResult.FAIL;
            }
        }
        return super.useItemOn(heldStack, state, level, pos, player, hand, hit);
    }
}
