package com.upo.createmechanicalconfection.content;

import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.items.CakeSpatulaItem;
import com.upo.createmechanicalconfection.content.items.CogMoldItem;
import com.upo.createmechanicalconfection.content.items.TubeMoldItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CMCItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.createItems(CreateMechanicalConfection.MODID);

    public static final DeferredHolder<Item, Item> CAKE_SPATULA = ITEMS.register("cake_spatula",
            () -> new CakeSpatulaItem(new Item.Properties().stacksTo(1))
    );

    public static final DeferredHolder<Item, Item> COG_CAKE_ICON = ITEMS.register("cog_cake_icon", () -> new Item(new Item.Properties()));



    public static final DeferredHolder<Item, Item> COG_MOLD_ITEM = ITEMS.register("cog_mold_item",
            () -> new CogMoldItem(new Item.Properties()
                    .stacksTo(1)
            )
    );

    public static final DeferredHolder<Item, Item> TUBE_MOLD_ITEM = ITEMS.register("tube_mold_item",
            () -> new TubeMoldItem(new Item.Properties()
                    .stacksTo(1)
            )
    );


    public static final FoodProperties COG_CAKE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(6)
            .fast()
            .build();
    public static final DeferredHolder<Item, Item> COG_CAKE_SLICE = ITEMS.register("cog_cake_slice",
            () -> new Item(new Item.Properties()
                    .food(COG_CAKE_SLICE_FOOD)
            )
    );

    public static final FoodProperties ET_CAKE_SLICE_FOOD = new FoodProperties.Builder()
            .nutrition(9)
            .fast()
            .build();
    public static final DeferredHolder<Item, Item> TUBE_CAKE_SLICE = ITEMS.register("tube_cake_slice",
            () -> new Item(new Item.Properties()
                    .food(ET_CAKE_SLICE_FOOD)
            )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
