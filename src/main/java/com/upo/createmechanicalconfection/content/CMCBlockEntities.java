package com.upo.createmechanicalconfection.content;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.block_entities.MechanicalOvenBlockEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.upo.createmechanicalconfection.client.render.blockentity.MechanicalOvenRenderer;
import com.upo.createmechanicalconfection.content.blocks.foodtray.FoodTrayBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CMCBlockEntities {

    private static final CreateRegistrate REGISTRATE = CreateMechanicalConfection.registrate();

    public static final BlockEntityEntry<MechanicalOvenBlockEntity> MECHANICAL_OVEN_BE = REGISTRATE
            .blockEntity("mechanical_oven", MechanicalOvenBlockEntity::new)
            .validBlocks(CMCBlocks.MECHANICAL_OVEN)
            .renderer(() -> MechanicalOvenRenderer::new)
            .register();



    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateMechanicalConfection.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FoodTrayBlockEntity>> FOOD_TRAY_BE =
            BLOCK_ENTITIES.register("food_tray_be",
                    () -> BlockEntityType.Builder.of(
                                    FoodTrayBlockEntity::new,
                                    CMCBlocks.FOOD_TRAY.get()
                            )
                            .build(null)
            );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static void register() {
    }
}


