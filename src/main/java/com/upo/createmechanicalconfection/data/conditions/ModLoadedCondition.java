package com.upo.createmechanicalconfection.data.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.upo.createmechanicalconfection.data.conditions.ModConditions; // 需要导入用于 codec()
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.ICondition;

public record ModLoadedCondition(String modId) implements ICondition {

    // Codec 用于序列化/反序列化到 JSON
    public static final MapCodec<ModLoadedCondition> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.STRING.fieldOf("modid").forGetter(ModLoadedCondition::modId)
            ).apply(instance, ModLoadedCondition::new)
    );

    @Override
    public boolean test(IContext context) {
        return ModList.get().isLoaded(this.modId);
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return ModConditions.MOD_LOADED_CONDITION_CODEC.get();
    }
}

