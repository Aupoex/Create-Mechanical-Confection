package com.upo.createmechanicalconfection.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.upo.createmechanicalconfection.content.blocks.MechanicalOvenBlock;
import com.upo.createmechanicalconfection.content.block_entities.MechanicalOvenBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;



public class MechanicalOvenRenderer implements BlockEntityRenderer<MechanicalOvenBlockEntity> {

    private final ItemRenderer itemRenderer;

    public MechanicalOvenRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public void render(MechanicalOvenBlockEntity blockEntity, float partialTicks, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        Level level = blockEntity.getLevel();
        if (level == null) return;

        IItemHandler itemHandler = blockEntity.inventory;
        if (itemHandler == null) return;

        ItemStack stackToRender = itemHandler.getStackInSlot(MechanicalOvenBlockEntity.OUTPUT_SLOT);
        if (stackToRender.isEmpty()) {
            stackToRender = itemHandler.getStackInSlot(MechanicalOvenBlockEntity.INPUT_SLOT);
        }

        if (stackToRender.isEmpty()) {
            return;
        }

        BlockState blockState = blockEntity.getBlockState();
        Direction facing = blockState.getValue(MechanicalOvenBlock.FACING);
        boolean isProcessing = blockState.getValue(MechanicalOvenBlock.LIT);


        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5);
        float yRot = facing.toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(-yRot));
        float renderX = -0.06f;
        float renderY = -0.05f;
        float renderZ = 0.0f;
        poseStack.translate(renderX, renderY, renderZ);

        if (isProcessing) {
            float speed = 1.0f;
            long gameTime = level.getGameTime();
            float angle = (gameTime % (360 / speed) + partialTicks) * speed;
            poseStack.mulPose(Axis.YP.rotationDegrees(angle));
        }

        float scale = 0.8f;
        poseStack.scale(scale, scale, scale);

        this.itemRenderer.renderStatic(
                stackToRender,
                ItemDisplayContext.FIXED,
                packedLight,
                packedOverlay,
                poseStack,
                bufferSource,
                level,
                (int) blockEntity.getBlockPos().asLong()
        );

        poseStack.popPose();
    }


    @Override
    public int getViewDistance() {
        return 64;
    }

}

