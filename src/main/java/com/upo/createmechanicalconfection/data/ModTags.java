package com.upo.createmechanicalconfection.data;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static final class Blocks {
        // 农夫乐事的热源
        public static final TagKey<Block> FARMERS_DELIGHT_HEAT_SOURCES =
                TagKey.create(Registries.BLOCK, ResourceLocation.parse("farmersdelight:heat_sources"));

        private static TagKey<Block> createBlockTag(String namespace, String path) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(namespace, path));
        }
    }

    public static final class Items {
    }
}
