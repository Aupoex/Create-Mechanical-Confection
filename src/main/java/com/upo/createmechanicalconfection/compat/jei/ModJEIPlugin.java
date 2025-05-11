package com.upo.createmechanicalconfection.compat.jei;

import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.compat.jei.recipe.OvenDisplayRecipe;
import com.upo.createmechanicalconfection.content.CMCBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin {

    private static final ResourceLocation PLUGIN_ID = CreateMechanicalConfection.asResource("jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new MechanicalOvenRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<OvenDisplayRecipe> bakingDisplayRecipes = new ArrayList<>();

        //齿轮蛋糕的显示配方
        Ingredient gearBatterInput = Ingredient.of(CMCBlocks.FILLED_COG_CAKE_BATTER_BLOCK.get());
        ItemStack cogCakeOutput = new ItemStack(CMCBlocks.COG_CAKE_BLOCK.get());
        bakingDisplayRecipes.add(new OvenDisplayRecipe(gearBatterInput, cogCakeOutput));

        //电子管蛋糕的显示配方
        Ingredient etBatterInput = Ingredient.of(CMCBlocks.FILLED_TUBE_CAKE_BATTER_BLOCK.get());
        ItemStack etCakeOutput = new ItemStack(CMCBlocks.TUBE_CAKE_BLOCK.get());
        bakingDisplayRecipes.add(new OvenDisplayRecipe(etBatterInput, etCakeOutput));


        registration.addRecipes(MechanicalOvenRecipeCategory.TYPE, bakingDisplayRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(CMCBlocks.MECHANICAL_OVEN.get()),
                MechanicalOvenRecipeCategory.TYPE
        );
    }
}
