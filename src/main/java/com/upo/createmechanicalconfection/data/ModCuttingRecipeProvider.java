package com.upo.createmechanicalconfection.data;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.CMCBlocks;
import com.upo.createmechanicalconfection.content.CMCItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class ModCuttingRecipeProvider extends ProcessingRecipeGen {

    public ModCuttingRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.CUTTING;
    }

    GeneratedRecipe CUTTING_COG_CAKE = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "cutting/cog"),
            b -> b.require(CMCBlocks.COG_CAKE_BLOCK.get())
                    .output(CMCItems.COG_CAKE_SLICE.get(), 6)
    );
    GeneratedRecipe CUTTING_TUBE_CAKE = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "cutting/tube"),
            b -> b.require(CMCBlocks.TUBE_CAKE_BLOCK.get())
                    .output(CMCItems.TUBE_CAKE_SLICE.get(), 4)
    );
    GeneratedRecipe CUTTING_TANK_CAKE = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "cutting/tank"),
            b -> b.require(CMCBlocks.TANK_CAKE_BLOCK.get())
                    .output(CMCItems.TANK_CAKE_SLICE.get(), 4)
    );
}

