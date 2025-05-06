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
import net.minecraft.world.item.Items;
import java.util.concurrent.CompletableFuture;

public class ModDeployingRecipeProvider extends ProcessingRecipeGen {

    public ModDeployingRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.DEPLOYING;
    }

    GeneratedRecipe DEPLOYING_COG_CAKE_BATTER = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/cog_cake_batter"),
            b -> b.require(CMCBlocks.RAW_CAKE_BATTER_BLOCK.get())
                    .require(CMCItems.COG_MOLD_ITEM.get())
                    .output(CMCBlocks.COG_CAKE_BATTER_BLOCK.get())
                    .toolNotConsumed()
    );
    GeneratedRecipe DEPLOYING_FILLED_COG_CAKE_BATTER = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/filled_cog_cake_batter"),
            b -> b.require(CMCBlocks.COG_CAKE_BATTER_BLOCK.get())
                    .require(Items.SWEET_BERRIES)
                    .output(CMCBlocks.FILLED_COG_CAKE_BATTER_BLOCK.get())
    );

    GeneratedRecipe DEPLOYING_FILLED_TUBE_CAKE_BATTER_MOLD = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/filled_tube_cake_batter_mold"),
            b -> b.require(CMCBlocks.RAW_CAKE_BATTER_BLOCK.get())
                    .require(CMCItems.TUBE_MOLD_ITEM.get() )
                    .output(CMCBlocks.TUBE_CAKE_BATTER_BLOCK.get())
                    .toolNotConsumed()
    );
    GeneratedRecipe DEPLOYING_FILLED_TUBE_CAKE_BATTER = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/filled_tube_cake_batter"),
            b -> b.require(CMCBlocks.TUBE_CAKE_BATTER_BLOCK.get())
                    .require(Items.BAKED_POTATO )
                    .output(CMCBlocks.FILLED_TUBE_CAKE_BATTER_BLOCK.get())
    );
    GeneratedRecipe DEPLOYING_FILLED_TUBE_CAKE_BATTER_P = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/filled_tube_cake_batterp"),
            b -> b.require(CMCBlocks.TUBE_CAKE_BATTER_BLOCK.get())
                    .require(Items.POTATO )
                    .output(CMCBlocks.FILLED_TUBE_CAKE_BATTER_BLOCK.get())
    );

}


