package com.upo.createmechanicalconfection.data;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.CMCItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import java.util.concurrent.CompletableFuture;

public class ModPressingRecipeProvider extends ProcessingRecipeGen {

    public ModPressingRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.COMPACTING;
    }

    GeneratedRecipe PRESSING_COG = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "pressing/cog"),
            b -> b.require(AllItems.IRON_SHEET.get())
                    .require(AllItems.IRON_SHEET.get())
                    .require(AllItems.IRON_SHEET.get())
                    .require(AllItems.IRON_SHEET.get())
                    .require(AllBlocks.COGWHEEL.get())
                    .output(CMCItems.COG_MOLD_ITEM.get())
    );

    GeneratedRecipe PRESSING_TUB = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "pressing/tub"),
            b -> b.require(AllItems.IRON_SHEET.get())
                    .require(AllItems.IRON_SHEET.get())
                    .require(AllItems.IRON_SHEET.get())
                    .require(AllItems.IRON_SHEET.get())
                    .require(AllItems.ELECTRON_TUBE.get())
                    .output(CMCItems.TUBE_MOLD_ITEM.get())
    );

    GeneratedRecipe PRESSING_TANK = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "pressing/tank"),
            b -> b.require(AllItems.IRON_SHEET.get())
                    .require(AllItems.IRON_SHEET.get())
                    .require(AllItems.IRON_SHEET.get())
                    .require(AllItems.IRON_SHEET.get())
                    .require(AllBlocks.FLUID_TANK.get())
                    .output(CMCItems.TANK_MOLD_ITEM.get())
    );

}


