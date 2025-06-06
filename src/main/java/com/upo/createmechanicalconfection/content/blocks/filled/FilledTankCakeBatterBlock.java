package com.upo.createmechanicalconfection.content.blocks.filled;

import com.upo.createmechanicalconfection.content.blocks.BaseFilledCakeBatterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


public class FilledTankCakeBatterBlock extends BaseFilledCakeBatterBlock {

    public FilledTankCakeBatterBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override public int getBakingDuration() { return 120; }

    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;}
}
