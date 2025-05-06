package com.upo.createmechanicalconfection.content.blocks.filled;

import com.upo.createmechanicalconfection.content.blocks.BaseFilledCakeBatterBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;


public class FilledCogCakeBatterBlock extends BaseFilledCakeBatterBlock {

    public FilledCogCakeBatterBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override public int getBakingDuration() { return 120; }
}
