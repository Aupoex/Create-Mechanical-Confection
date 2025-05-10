package com.upo.createmechanicalconfection.interaction;

import com.simibubi.create.api.registry.CreateRegistries;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModArmInteractionPointTypes {

    public static final DeferredRegister<ArmInteractionPointType> TYPES = DeferredRegister.create(
            CreateRegistries.ARM_INTERACTION_POINT_TYPE,
            CreateMechanicalConfection.MODID
    );

    public static final DeferredHolder<ArmInteractionPointType, ArmInteractionPointType> MECHANICAL_OVEN_DEPOSIT_POINT_TYPE = TYPES.register(
            "mechanical_oven_deposit_point",
            () -> new MechanicalOvenArmInteraction.Type(
                    CreateMechanicalConfection.asResource("mechanical_oven_deposit_point")
            )
    );

    public static void register(IEventBus modEventBus) {
        TYPES.register(modEventBus);
    }
}

