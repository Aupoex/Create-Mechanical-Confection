package com.upo.createmechanicalconfection.content.items;

import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.utility.CreateLang;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class FoodTrayBlockItem extends BlockItem {
    public FoodTrayBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {

        tooltip.add(CreateLang.translateDirect("tooltip." + CreateMechanicalConfection.MODID + ".tray.summary")
                .withStyle(ChatFormatting.GRAY));

        if (Screen.hasShiftDown()) {

            tooltip.add(Component.literal(""));

            tooltip.addAll(TooltipHelper.cutStringTextComponent(
                    CreateLang.translateDirect("tooltip." + CreateMechanicalConfection.MODID + ".tray.condition1").getString(),
                    FontHelper.Palette.STANDARD_CREATE
            ));

            tooltip.addAll(TooltipHelper.cutStringTextComponent(
                    CreateLang.translateDirect("tooltip." + CreateMechanicalConfection.MODID + ".tray.usage").getString(),
                    FontHelper.Palette.STANDARD_CREATE
            ));
        }
    }
}
