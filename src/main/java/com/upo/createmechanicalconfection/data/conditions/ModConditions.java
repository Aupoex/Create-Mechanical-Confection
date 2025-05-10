package com.upo.createmechanicalconfection.data.conditions;

import com.mojang.serialization.MapCodec;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.data.conditions.ModLoadedCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.DeferredHolder; // 使用 DeferredHolder

public class ModConditions {

    // 使用正确的注册表键: NeoForgeRegistries.CONDITION_CODECS
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS =
            DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, CreateMechanicalConfection.MODID);

    // 注册 ModLoadedCondition 的 Codec
    // DeferredHolder 的第二个泛型是注册表的值类型，所以是 MapCodec<? extends ICondition>
    // 但我们注册的是具体的 MapCodec<ModLoadedCondition>
    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<ModLoadedCondition>> MOD_LOADED_CONDITION_CODEC =
            CONDITION_CODECS.register("mod_loaded", () -> ModLoadedCondition.CODEC);
    // "mod_loaded" 将是 JSON 中 "type": "yourmodid:mod_loaded" 的 "mod_loaded" 部分

    public static void register(IEventBus modEventBus) {
        CONDITION_CODECS.register(modEventBus);
    }
}

