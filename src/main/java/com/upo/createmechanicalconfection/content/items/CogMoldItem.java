package com.upo.createmechanicalconfection.content.items;

import com.upo.createmechanicalconfection.content.CMCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CogMoldItem extends Item {

    public CogMoldItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(pos);
        Player player = context.getPlayer();
        if (clickedState.is(CMCBlocks.RAW_CAKE_BATTER_BLOCK.get())) {
            if (!level.isClientSide) {
                level.setBlock(pos, CMCBlocks.COG_CAKE_BATTER_BLOCK.get().defaultBlockState(), 3);
                level.playSound(null, pos, SoundEvents.SAND_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.useOn(context);
    }
}

