package com.upo.createmechanicalconfection.data;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.CMCBlocks;
import com.upo.createmechanicalconfection.content.CMCItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

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
    GeneratedRecipe DEPLOYING_FILLED_TUBE_CAKE_BATTER_P = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/filled_tube_cake_batterp"),
            b -> b.require(CMCBlocks.TUBE_CAKE_BATTER_BLOCK.get())
                    .require(Ingredient.fromValues(Stream.of(
                            new Ingredient.ItemValue(new ItemStack(Items.POTATO)),
                            new Ingredient.ItemValue(new ItemStack(Items.BAKED_POTATO))
                    )))
                    .output(CMCBlocks.FILLED_TUBE_CAKE_BATTER_BLOCK.get())
    );

    GeneratedRecipe DEPLOYING_TANK_CAKE_BATTER = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/tank_cake_batter"),
            b -> b.require(CMCBlocks.RAW_CAKE_BATTER_BLOCK.get())
                    .require(CMCItems.TANK_MOLD_ITEM.get())
                    .output(CMCBlocks.TANK_CAKE_BATTER_BLOCK.get())
                    .toolNotConsumed()
    );
    GeneratedRecipe DEPLOYING_FILLED_TANK_CAKE_BATTER = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/filled_tank_cake_batter"),
            b -> b.require(CMCBlocks.TANK_CAKE_BATTER_BLOCK.get())
                    .require(Items.MAGMA_CREAM)
                    .output(CMCBlocks.FILLED_TANK_CAKE_BATTER_BLOCK.get())
    );




    //农夫乐事小刀切片
    public static final TagKey<Item> FARMERS_DELIGHT_KNIVES_TAG = TagKey.create(
            Registries.ITEM, ResourceLocation.parse("farmersdelight:tools/knives")
    );
    GeneratedRecipe DEPLOYING_COG_CAKE_SLICE_DF = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/cog_cake_slice_df"),
            b -> b.require(CMCBlocks.COG_CAKE_BLOCK.get())
                    .require(FARMERS_DELIGHT_KNIVES_TAG)
                    .output(CMCItems.COG_CAKE_SLICE.get(),4)
                    .whenModLoaded("farmersdelight")
    );
    GeneratedRecipe DEPLOYING_TUBE_CAKE_SLICE_DF = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/tube_cake_slice_df"),
            b -> b.require(CMCBlocks.TUBE_CAKE_BLOCK.get())
                    .require(FARMERS_DELIGHT_KNIVES_TAG)
                    .output(CMCItems.TUBE_CAKE_SLICE.get(),4)
                    .whenModLoaded("farmersdelight")
    );
    GeneratedRecipe DEPLOYING_TANK_CAKE_SLICE_DF = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "deploying/tank_cake_slice_df"),
            b -> b.require(CMCBlocks.TANK_CAKE_BLOCK.get())
                    .require(FARMERS_DELIGHT_KNIVES_TAG)
                    .output(CMCItems.TANK_CAKE_SLICE.get(),4)
                    .whenModLoaded("farmersdelight")
    );

}


