package com.upo.createmechanicalconfection.content.blocks.filled;
import com.upo.createmechanicalconfection.content.blocks.BaseFilledCakeBatterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FilledTubeCakeBatterBlock extends BaseFilledCakeBatterBlock{
    public FilledTubeCakeBatterBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 11.0D, 11.0D, 11.0D);
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
