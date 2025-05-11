package com.upo.createmechanicalconfection.content.blocks.cake;

import com.upo.createmechanicalconfection.content.blocks.BaseCakeBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CogCakeBlock extends BaseCakeBlock {

    private static final int COG_CAKE_MAX_BITES = 4;

    private static final VoxelShape[] COG_CAKE_SHAPES = new VoxelShape[]{
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D), // bites = 0
            Block.box(3.0D, 0.0D, 1.0D, 13.0D, 8.0D, 13.0D), // bites = 1
            Block.box(5.0D, 0.0D, 1.0D, 13.0D, 8.0D, 13.0D), // bites = 2
            Block.box(7.0D, 0.0D, 1.0D, 13.0D, 8.0D, 13.0D), // bites = 3
            Block.box(9.0D, 0.0D, 1.0D, 13.0D, 8.0D, 13.0D)  // bites = 4
    };


    public CogCakeBlock(BlockBehaviour.Properties properties) {
        super(properties, COG_CAKE_MAX_BITES, COG_CAKE_SHAPES);
    }



    @Override
    protected int getHungerPerBite() {
        return 6;
    }

    @Override
    protected float getSaturationPerBite() {
        return 1F;
    }
}

