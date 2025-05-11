package com.upo.createmechanicalconfection.compat.jei.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class OvenDisplayRecipe {
    private final Ingredient inputBatter;
    private final ItemStack outputCake;

    public OvenDisplayRecipe(Ingredient inputBatter, ItemStack outputCake) {
        this.inputBatter = inputBatter;
        this.outputCake = outputCake;
    }

    public Ingredient getInputBatter() {
        return inputBatter;
    }

    public ItemStack getOutputCake() {
        return outputCake;
    }
}

