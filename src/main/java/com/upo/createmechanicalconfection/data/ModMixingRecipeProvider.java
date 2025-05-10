package com.upo.createmechanicalconfection.data;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.CMCBlocks;
import com.upo.createmechanicalconfection.data.conditions.ModLoadedCondition;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.crafting.ConditionalRecipeOutput; // <-- 核心类
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.concurrent.CompletableFuture;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class ModMixingRecipeProvider extends ProcessingRecipeGen {

    public ModMixingRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    public static final TagKey<Item> FARMERS_DELIGHT_DOUGH_TAG = TagKey.create(
            Registries.ITEM,
            ResourceLocation.parse("c:foods/dough")
    );
    private static final Fluid MILK = BuiltInRegistries.FLUID.get(ResourceLocation.withDefaultNamespace("milk"));

    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MIXING;
    }

    GeneratedRecipe MIXING_RAW_CAKE_BATTER = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "mixing/raw_cake_batter"),
            b -> b
                    .require(AllItems.DOUGH.get())
                    .require(Items.EGG)
                    .require(FluidIngredient.fromFluidStack(new FluidStack(MILK, 100)))
                    .output(CMCBlocks.RAW_CAKE_BATTER_BLOCK.get(), 1)
                    .requiresHeat(HeatCondition.NONE)
    );

    GeneratedRecipe MIXING_RAW_CAKE_BATTER_FG = create(
            ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "mixing/raw_cake_batter_fd"),
            b -> b
                    .require(FARMERS_DELIGHT_DOUGH_TAG)
                    .require(Items.EGG)
                    .require(FluidIngredient.fromFluidStack(new FluidStack(MILK, 100)))
                    .output(CMCBlocks.RAW_CAKE_BATTER_BLOCK.get(), 1)
                    .requiresHeat(HeatCondition.NONE)
                    .whenModLoaded("farmersdelight")//适配农夫乐事的面团
    );


}

