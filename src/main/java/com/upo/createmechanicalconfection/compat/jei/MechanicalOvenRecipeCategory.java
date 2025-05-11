package com.upo.createmechanicalconfection.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.compat.jei.recipe.OvenDisplayRecipe;
import com.upo.createmechanicalconfection.content.CMCBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import com.simibubi.create.compat.jei.category.animations.AnimatedBlazeBurner;

public class MechanicalOvenRecipeCategory implements IRecipeCategory<OvenDisplayRecipe> {

    public static final RecipeType<OvenDisplayRecipe> TYPE =
            RecipeType.create(CreateMechanicalConfection.MODID, "mechanical_baking_display", OvenDisplayRecipe.class);

    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slotDrawable;
    private final AnimatedBlazeBurner blazeBurner;
    private final IDrawable jeiArrow;

    private final int bgWidth = 100;
    private final int bgHeight = 60;

    private final int inputSlotX = 10;
    private final int inputSlotY = 22;

    private final int outputSlotX = 74;
    private final int outputSlotY = 22;

    private final int ovenIconX = 42;
    private final int ovenIconY = 7;

    private final int burnerAnimX = 20;
    private final int burnerAnimY = 13;

    private final int arrowX = 35;
    private final int arrowY = 22;

    public MechanicalOvenRecipeCategory(IGuiHelper guiHelper) {
        this.title = Component.translatable("gui." + CreateMechanicalConfection.MODID + ".jei.category.mechanical_baking");

        this.background = guiHelper.createBlankDrawable(bgWidth, bgHeight);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(CMCBlocks.MECHANICAL_OVEN.get()));
        this.slotDrawable = guiHelper.getSlotDrawable();
        this.blazeBurner = new AnimatedBlazeBurner();

        this.jeiArrow = guiHelper.drawableBuilder(
                        AllGuiTextures.JEI_ARROW.getLocation(),
                        AllGuiTextures.JEI_ARROW.getStartX(),
                        AllGuiTextures.JEI_ARROW.getStartY(),
                        AllGuiTextures.JEI_ARROW.getWidth(),
                        AllGuiTextures.JEI_ARROW.getHeight()
                )
                .build();

    }

    @Override
    public RecipeType<OvenDisplayRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }



    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, OvenDisplayRecipe recipe, IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, inputSlotX, inputSlotY)
                .addIngredients(recipe.getInputBatter())
                .setBackground(slotDrawable, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, outputSlotX, outputSlotY)
                .addItemStack(recipe.getOutputCake())
                .setBackground(slotDrawable, -1, -1);
    }

    @Override
    public void draw(OvenDisplayRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        PoseStack poseStack = guiGraphics.pose();

        poseStack.pushPose();
        float ovenCenterX = ovenIconX + 8f;
        float ovenCenterY = ovenIconY + 8f;
        poseStack.translate(ovenCenterX, ovenCenterY, 0);
        float ovenDisplayScale = 1.4f;
        poseStack.scale(ovenDisplayScale, ovenDisplayScale, 1f);
        poseStack.translate(-ovenCenterX, -ovenCenterY, 0);
        guiGraphics.renderFakeItem(new ItemStack(CMCBlocks.MECHANICAL_OVEN.get()), ovenIconX, ovenIconY);
        poseStack.popPose();

        int dynamicArrowX = inputSlotX + 16 + 2;
        int dynamicArrowY = inputSlotY + (16 - jeiArrow.getHeight()) / 2;
        jeiArrow.draw(guiGraphics, dynamicArrowX, dynamicArrowY);


        poseStack.pushPose();
        float burnerActualWidth = 20;
        float burnerActualHeight = 20;
        float burnerDisplayX = burnerAnimX;
        float burnerDisplayY = burnerAnimY;
        float burnerScale = 0.8f;

        poseStack.translate(burnerDisplayX + (burnerActualWidth * (1 - burnerScale) / 2f),
                burnerDisplayY + (burnerActualHeight * (1 - burnerScale) / 2f), 0);
        poseStack.scale(burnerScale, burnerScale, 1f);

        blazeBurner.withHeat(BlazeBurnerBlock.HeatLevel.KINDLED)
                .draw(guiGraphics, burnerAnimX, burnerAnimY);
        poseStack.popPose();

    }
}

