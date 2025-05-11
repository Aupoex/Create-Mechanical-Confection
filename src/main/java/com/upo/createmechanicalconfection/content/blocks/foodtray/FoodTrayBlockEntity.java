package com.upo.createmechanicalconfection.content.blocks.foodtray;

import com.upo.createmechanicalconfection.content.CMCBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FoodTrayBlockEntity extends BlockEntity {

    private FoodTrayItemType storedItemType = FoodTrayItemType.NONE;
    private int storedCount = 0;

    public FoodTrayBlockEntity(BlockPos pos, BlockState blockState) {
        super(CMCBlockEntities.FOOD_TRAY_BE.get(), pos, blockState);
    }

    public FoodTrayItemType getStoredItemType() {
        return storedItemType;
    }

    public int getStoredCount() {
        return storedCount;
    }

    public void setStoredItem(FoodTrayItemType type, int count) {
        this.storedItemType = type;
        int maxForThisType = (type == FoodTrayItemType.NONE) ? 0 : type.getMaxStacksOnTray();
        this.storedCount = Math.max(0, Math.min(count, maxForThisType));
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("StoredItemType", storedItemType.getSerializedName());
        tag.putInt("StoredCount", storedCount);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        String typeName = tag.getString("StoredItemType");
        this.storedItemType = FoodTrayItemType.NONE; // 默认值
        for (FoodTrayItemType type : FoodTrayItemType.values()) {
            if (type.getSerializedName().equals(typeName)) {
                this.storedItemType = type;
                break;
            }
        }
        this.storedCount = tag.getInt("StoredCount");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}

