package com.upo.createmechanicalconfection.content;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.block_entities.MechanicalOvenBlockEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.upo.createmechanicalconfection.client.render.blockentity.MechanicalOvenRenderer;

public class CMCBlockEntities {

    private static final CreateRegistrate REGISTRATE = CreateMechanicalConfection.registrate();

    public static final BlockEntityEntry<MechanicalOvenBlockEntity> MECHANICAL_OVEN_BE = REGISTRATE
            .blockEntity("mechanical_oven", MechanicalOvenBlockEntity::new)
            .validBlocks(CMCBlocks.MECHANICAL_OVEN)
            .renderer(() -> MechanicalOvenRenderer::new)
            .register();

    public static void register() {
    }
}


