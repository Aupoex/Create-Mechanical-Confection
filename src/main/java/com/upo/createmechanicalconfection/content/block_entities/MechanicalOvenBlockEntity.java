package com.upo.createmechanicalconfection.content.block_entities;

import com.upo.createmechanicalconfection.content.CMCBlockEntities;
import com.upo.createmechanicalconfection.content.blocks.BaseFilledCakeBatterBlock;
import com.upo.createmechanicalconfection.content.blocks.MechanicalOvenBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;

public class MechanicalOvenBlockEntity extends BlockEntity {

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;
    private static final int INVENTORY_SIZE = 2;
    private static final int DEFAULT_TOTAL_TICKS = 200; // 默认烘烤时间

    public final ItemStackHandler inventory = createInventory();

    protected int processingTicks = 0;
    protected int totalTicksRequired = DEFAULT_TOTAL_TICKS;
    protected boolean heated = false;

    public MechanicalOvenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(CMCBlockEntities.MECHANICAL_OVEN_BE.get(), pos, blockState);
    }

    protected ItemStackHandler createInventory() {
        return new ItemStackHandler(INVENTORY_SIZE) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if (level != null && !level.isClientSide()) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
                }
            }
            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if (slot == OUTPUT_SLOT) return false;
                return slot == INPUT_SLOT &&
                        stack.getItem() instanceof BlockItem blockItem &&
                        blockItem.getBlock() instanceof BaseFilledCakeBatterBlock;
            }
            @NotNull @Override
            public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                if (slot == OUTPUT_SLOT) return stack;
                if (!isItemValid(slot, stack)) return stack;
                return super.insertItem(slot, stack, simulate);
            }
            @NotNull @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                if (slot == INPUT_SLOT) return ItemStack.EMPTY;
                return super.extractItem(slot, amount, simulate);
            }
            @Override public int getSlotLimit(int slot) { return 64; }
        };
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MechanicalOvenBlockEntity be) {
        if (level == null || level.isClientSide || be.isRemoved()) {
            return;
        }
        if (be.inventory == null) {
            return;
        }

        boolean changedNBT = false;
        boolean changedState = false;

        boolean wasHeated = be.heated;
        be.updateHeatSource(level, pos);
        if (wasHeated != be.heated) changedNBT = true;

        boolean canProcessNow = be.canProcess();
        boolean shouldPause = !be.heated || !canProcessNow;
        boolean currentLitState = state.getValue(MechanicalOvenBlock.LIT);

        if (!shouldPause) {

            if (!currentLitState) {
                level.setBlock(pos, state.setValue(MechanicalOvenBlock.LIT, true), 3);
                state = level.getBlockState(pos);
                changedState = true;
            }

            if (be.processingTicks == 0) {
                ItemStack inputStack = be.inventory.getStackInSlot(INPUT_SLOT);

                BaseFilledCakeBatterBlock filledCakeBatterBlock = null;
                if (inputStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BaseFilledCakeBatterBlock cbb) {
                    filledCakeBatterBlock = cbb;
                }


                if (filledCakeBatterBlock != null) {

                    be.totalTicksRequired = filledCakeBatterBlock.getBakingDuration();
                    be.processingTicks = 1;
                    changedNBT = true;
                }
            } else {
                be.processingTicks++;
                if (be.processingTicks >= be.totalTicksRequired) {
                    be.processItem();
                    be.processingTicks = 0;
                    changedNBT = true;

                }
            }
        } else {
            if (be.processingTicks > 0) {
                be.processingTicks = 0;
                changedNBT = true;
            }

            if (currentLitState) {
                level.setBlock(pos, state.setValue(MechanicalOvenBlock.LIT, false), 3);
                state = level.getBlockState(pos);
                changedState = true;
            }
        }

        if (changedNBT) {
            be.setChanged();
        }
        if (changedNBT || changedState) {
            level.sendBlockUpdated(pos, be.getBlockState(), state, Block.UPDATE_CLIENTS);
        }
    }


    protected void updateHeatSource(Level level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        HeatLevel heatLevel = BlazeBurnerBlock.getHeatLevelOf(belowState);
        this.heated = heatLevel.isAtLeast(HeatLevel.KINDLED);
    }

    protected boolean canProcess() {

        ItemStack inputStack = inventory.getStackInSlot(INPUT_SLOT);
        if (inputStack.isEmpty()) {
            return false;
        }

        BaseFilledCakeBatterBlock filledCakeBatterBlock = null;
        if (inputStack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BaseFilledCakeBatterBlock cbb) {
            filledCakeBatterBlock = cbb;
        } else {
            return false;
        }

        Item resultItem = filledCakeBatterBlock.getBakedResultItem();
        if (resultItem == Items.AIR) {
            return false;
        }
        ItemStack resultStack = new ItemStack(resultItem); // 假设配方输出数量为1

        int inputNeeded = 1;
        if (inputStack.getCount() < inputNeeded) {
            return false;
        }

        boolean canOutputResult = canOutput(resultStack);
        if (!canOutputResult) {
            return false;
        }

        return true;
    }


    protected boolean canOutput(ItemStack resultStack) {
        ItemStack outputSlot = inventory.getStackInSlot(OUTPUT_SLOT);
        if (outputSlot.isEmpty()) {
            return true;
        }
        boolean itemsMatch = ItemStack.isSameItemSameComponents(outputSlot, resultStack);
        if (!itemsMatch) {
            return false;
        }
        boolean spaceAvailable = outputSlot.getCount() + resultStack.getCount() <= outputSlot.getMaxStackSize();
        if (!spaceAvailable) {
            return false;
        }
        return true;
    }



    protected void processItem() {

        ItemStack inputStackForProcessing = inventory.getStackInSlot(INPUT_SLOT).copy();

        if (inputStackForProcessing.isEmpty()) {
            processingTicks = 0; setChanged(); if(level != null && !level.isClientSide) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            return;
        }
        BaseFilledCakeBatterBlock filledCakeBatterBlock = null;
        if (inputStackForProcessing.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof BaseFilledCakeBatterBlock cbb) {
            filledCakeBatterBlock = cbb;
        } else {
            processingTicks = 0; setChanged(); if(level != null && !level.isClientSide) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            return;
        }
        Item resultItem = filledCakeBatterBlock.getBakedResultItem();
        if (resultItem == Items.AIR) {
            processingTicks = 0; setChanged(); if(level != null && !level.isClientSide) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            return;
        }
        ItemStack resultStack = new ItemStack(resultItem);
        int inputNeeded = 1;
        if (inputStackForProcessing.getCount() < inputNeeded) {
            processingTicks = 0; setChanged(); if(level != null && !level.isClientSide) level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            return;
        }
        boolean canOutputAfterRecheck = canOutput(resultStack);
        if (!canOutputAfterRecheck) {
            return;
        }


        ItemStack remainingInput = inventory.getStackInSlot(INPUT_SLOT).copy();
        remainingInput.shrink(inputNeeded);
        inventory.setStackInSlot(INPUT_SLOT, remainingInput);


        ItemStack outputSlot = inventory.getStackInSlot(OUTPUT_SLOT);
        if (outputSlot.isEmpty()) {
            inventory.setStackInSlot(OUTPUT_SLOT, resultStack.copy());
        } else if (ItemStack.isSameItemSameComponents(outputSlot, resultStack)) {
            int amountToGrow = Math.min(resultStack.getCount(), outputSlot.getMaxStackSize() - outputSlot.getCount());
            if (amountToGrow > 0) {
                ItemStack newOutput = outputSlot.copy();
                newOutput.grow(amountToGrow);
                inventory.setStackInSlot(OUTPUT_SLOT, newOutput);
            }
        }
    }


    public ItemStack takeResultItem() {
        if (!inventory.getStackInSlot(OUTPUT_SLOT).isEmpty()) {
            return inventory.extractItem(OUTPUT_SLOT, inventory.getSlotLimit(OUTPUT_SLOT), false);
        }
        return ItemStack.EMPTY;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("ProcessingTicks", processingTicks);
        tag.putBoolean("Heated", heated);
        tag.put("Inventory", inventory.serializeNBT(registries));
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        processingTicks = tag.getInt("ProcessingTicks");
        heated = tag.getBoolean("Heated");
        if (tag.contains("Inventory", CompoundTag.TAG_COMPOUND)) {
            inventory.deserializeNBT(registries, tag.getCompound("Inventory"));
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Nullable @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    public IItemHandler getItemHandlerCapability(@Nullable Direction side) {
        if (this.isRemoved()) {
            return null;
        }
        if (side == Direction.DOWN) {
            return null;
        }
        return new ItemHandlerWrapper(inventory);
    }

    private static class ItemHandlerWrapper implements IItemHandler {
        private final ItemStackHandler internal;
        public ItemHandlerWrapper(ItemStackHandler internal) { this.internal = internal; }
        @Override public int getSlots() { return internal.getSlots(); }
        @Override public @NotNull ItemStack getStackInSlot(int slot) { return internal.getStackInSlot(slot); }
        @Override public int getSlotLimit(int slot) { return internal.getSlotLimit(slot); }
        @Override public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot == INPUT_SLOT &&
                    stack.getItem() instanceof BlockItem blockItem &&
                    blockItem.getBlock() instanceof BaseFilledCakeBatterBlock &&
                    internal.isItemValid(slot, stack);
        }
        @Override public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if (slot != INPUT_SLOT || !isItemValid(slot, stack)) return stack;
            return internal.insertItem(slot, stack, simulate);
        }
        @Override public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot != OUTPUT_SLOT) return ItemStack.EMPTY;
            return internal.extractItem(slot, amount, simulate);
        }
    }
}


