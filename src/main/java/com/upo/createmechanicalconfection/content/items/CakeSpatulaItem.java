package com.upo.createmechanicalconfection.content.items;

import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.CreateLang;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import com.upo.createmechanicalconfection.data.ModBlockTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class CakeSpatulaItem extends Item {

    public CakeSpatulaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack heldStack = context.getItemInHand();
        BlockState blockState = level.getBlockState(pos);

        if (!level.isClientSide && player != null) {
            if (blockState.is(ModBlockTagsProvider.CAKES)) {

                if (blockState.hasProperty(BlockStateProperties.BITES)) {
                    int bites = blockState.getValue(BlockStateProperties.BITES);
                    if (bites == 0) {
                        Item cakeItem = blockState.getBlock().asItem();
                        if (cakeItem != Items.AIR) {
                            ItemStack cakeItemStack = new ItemStack(cakeItem);

                            level.removeBlock(pos, false);

                            boolean added = player.addItem(cakeItemStack);
                            if (!added) {
                                player.drop(cakeItemStack, false);
                            }
                            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.8F, 1.0F);

                            return InteractionResult.SUCCESS;
                        }
                    } else {
                        return InteractionResult.FAIL;
                    }
                } else {
                    Item cakeItem = blockState.getBlock().asItem();
                    if (cakeItem != Items.AIR) {
                        ItemStack cakeItemStack = new ItemStack(cakeItem);
                        level.removeBlock(pos, false);
                        boolean added = player.addItem(cakeItemStack);
                        if (!added) { player.drop(cakeItemStack, false); }
                        level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.8F, 1.0F);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(CreateLang.translateDirect("tooltip." + CreateMechanicalConfection.MODID + ".spatula.summary")
                .withStyle(ChatFormatting.GRAY));

        if (Screen.hasShiftDown()) {
            tooltip.add(Component.literal(""));

            tooltip.addAll(TooltipHelper.cutStringTextComponent(
                    CreateLang.translateDirect("tooltip." + CreateMechanicalConfection.MODID + ".spatula.condition1").getString(),
                    FontHelper.Palette.STANDARD_CREATE
            ));
        }
    }
}


