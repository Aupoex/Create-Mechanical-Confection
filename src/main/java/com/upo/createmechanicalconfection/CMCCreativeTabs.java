package com.upo.createmechanicalconfection;

import com.upo.createmechanicalconfection.content.CMCBlocks;
import com.upo.createmechanicalconfection.content.CMCItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;


public class CMCCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateMechanicalConfection.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TABS.register("main_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> createIcon())
                    .title(Component.translatable("creativetab." + CreateMechanicalConfection.MODID + ".main_tab"))
                    .displayItems((displayParameters, output) -> {

                        output.accept(CMCBlocks.RAW_CAKE_BATTER_BLOCK.get());

                        output.accept(CMCItems.CAKE_SPATULA.get());
                        output.accept(CMCBlocks.FOOD_TRAY_ITEM.get());

                        output.accept(CMCItems.COG_CAKE_SLICE.get());
                        output.accept(CMCItems.TUBE_CAKE_SLICE.get());
                        output.accept(CMCItems.TANK_CAKE_SLICE.get());

                        output.accept(CMCItems.COG_MOLD_ITEM.get());
                        output.accept(CMCBlocks.COG_CAKE_BLOCK.get());
                        output.accept(CMCBlocks.COG_CAKE_BATTER_BLOCK.get());
                        output.accept(CMCBlocks.FILLED_COG_CAKE_BATTER_BLOCK.get());

                        output.accept(CMCItems.TUBE_MOLD_ITEM.get());
                        output.accept(CMCBlocks.TUBE_CAKE_BLOCK.get());
                        output.accept(CMCBlocks.TUBE_CAKE_BATTER_BLOCK.get());
                        output.accept(CMCBlocks.FILLED_TUBE_CAKE_BATTER_BLOCK.get());

                        output.accept(CMCItems.TANK_MOLD_ITEM.get());
                        output.accept(CMCBlocks.TANK_CAKE_BLOCK.get());
                        output.accept(CMCBlocks.TANK_CAKE_BATTER_BLOCK.get());
                        output.accept(CMCBlocks.FILLED_TANK_CAKE_BATTER_BLOCK.get());
                    })
                    .build()
    );

    private static ItemStack createIcon() {
        return new ItemStack(CMCItems.COG_CAKE_ICON.get());
    }

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}
