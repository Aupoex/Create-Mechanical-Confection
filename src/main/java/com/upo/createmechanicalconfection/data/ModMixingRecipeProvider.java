package com.upo.createmechanicalconfection.data;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.CMCBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import net.neoforged.neoforge.fluids.FluidStack;

import java.util.concurrent.CompletableFuture;

public class ModMixingRecipeProvider extends ProcessingRecipeGen {

    public ModMixingRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MIXING;
    }

    GeneratedRecipe MIXING_RAW_CAKE_BATTER = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "mixing/raw_cake_batter"),
            b -> b.require(AllItems.DOUGH.get())
                    .require(Items.EGG)
                    .require(FluidIngredient.fromFluidStack(new FluidStack(AllFluids.HONEY.get().getSource(), 50)))
                    .output(CMCBlocks.RAW_CAKE_BATTER_BLOCK.get(), 1)
                    .requiresHeat(HeatCondition.NONE)

    );
}

