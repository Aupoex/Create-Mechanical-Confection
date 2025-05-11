package com.upo.createmechanicalconfection.content.blocks.foodtray;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import com.simibubi.create.AllItems;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum FoodTrayItemType implements StringRepresentable {
    NONE("none",null, 0 ),
    APPLE("honeyed_apple", AllItems.HONEYED_APPLE,4),
    CHOCOLATE("bar_of_chocolate", AllItems.BAR_OF_CHOCOLATE,3),
    TEA("builders_tea", AllItems.BUILDERS_TEA::get,1),
    ROLL("sweet_roll", AllItems.SWEET_ROLL,3),
    BERRIES("chocolate_glazed_berries", AllItems.CHOCOLATE_BERRIES,3);
    // BREAD("bread", () -> Items.BREAD),//以后可能加原版物品，或者其他模组的物品

    private final String name;
    private final Supplier<Item> itemSupplier;
    private final int maxStacksOnTray;
    private Item resolvedItem;
    private boolean resolved = false;

    FoodTrayItemType(String name, @Nullable Supplier<Item> itemSupplier, int maxStacksOnTray) {
        this.name = name;
        this.itemSupplier = itemSupplier;
        this.maxStacksOnTray = maxStacksOnTray;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    @Nullable
    public Item getRepresentativeItem() {
        if (!resolved) {
            if (itemSupplier != null) {
                try {
                    this.resolvedItem = itemSupplier.get();
                } catch (Exception e) {
                    System.err.println("Failed to resolve representative item for FoodTrayItemType." + name + ": " + e.getMessage());
                    this.resolvedItem = null;
                }
            }
            this.resolved = true;
        }
        return this.resolvedItem;
    }

    public int getMaxStacksOnTray() {
        return this.maxStacksOnTray;
    }

    public static FoodTrayItemType fromItem(@Nullable Item item) {
        if (item == null) {
            return NONE;
        }
        for (FoodTrayItemType type : values()) {
            if (type.getRepresentativeItem() == item) {
                return type;
            }
        }
        return NONE;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

