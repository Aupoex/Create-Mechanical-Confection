package com.upo.createmechanicalconfection.data;

import com.simibubi.create.AllBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import com.simibubi.create.AllItems;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.CMCBlocks;
import com.upo.createmechanicalconfection.content.CMCItems;
import java.util.concurrent.CompletableFuture;

public class ModCraftingRecipeProvider extends RecipeProvider {

    public ModCraftingRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, CMCBlocks.MECHANICAL_OVEN.get())
                .pattern("HDH")
                .pattern("T T")
                .pattern("HJH")
                .define('H', AllBlocks.BRASS_CASING.get())
                .define('D', AllItems.ELECTRON_TUBE.get())
                .define('T', AllItems.COPPER_SHEET.get())
                .define('J', AllItems.PRECISION_MECHANISM.get())
                .unlockedBy("has_brass_casing", has(AllBlocks.BRASS_CASING.get()))
                .unlockedBy("has_precision_mechanism", has(AllItems.PRECISION_MECHANISM.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "crafting/mechanical_oven"));


        //蛋糕铲
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, CMCItems.CAKE_SPATULA.get())
                .pattern(" T ")
                .pattern(" T ")
                .pattern(" J ")
                .define('T', AllItems.IRON_SHEET.get())
                .define('J', Items.STICK)
                .unlockedBy("has_iron_sheet", has(AllItems.IRON_SHEET.get()))
                .unlockedBy("has_stick", has(Items.STICK))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "crafting/cake_spatula"));
    }

}


