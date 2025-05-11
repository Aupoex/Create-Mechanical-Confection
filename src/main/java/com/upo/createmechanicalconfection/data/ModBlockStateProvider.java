package com.upo.createmechanicalconfection.data;

import com.upo.createmechanicalconfection.content.blocks.foodtray.FoodTrayBlock;
import com.upo.createmechanicalconfection.content.blocks.foodtray.FoodTrayItemType;
import com.upo.createmechanicalconfection.content.CMCBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

//给可放置食物生成BlockState，蛋糕不使用
public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerFoodTrayStates();
    }

    private void registerFoodTrayStates() {
        Block foodTrayBlock = CMCBlocks.FOOD_TRAY.get();
        var variants = getVariantBuilder(foodTrayBlock);
        ModelFile emptyTrayModel = models().getExistingFile(modLoc("block/tray/tray"));
        IntegerProperty stackCountProperty = FoodTrayBlock.STACK_COUNT;

        for (Direction facing : Direction.Plane.HORIZONTAL) {
            for (Integer countValue : stackCountProperty.getPossibleValues()) {
                variants.partialState()
                        .with(FoodTrayBlock.FACING, facing)
                        .with(FoodTrayBlock.ITEM_TYPE, FoodTrayItemType.NONE)
                        .with(FoodTrayBlock.STACK_COUNT, countValue)
                        .addModels(new ConfiguredModel(emptyTrayModel,
                                0, (int) facing.toYRot(), false));
            }

            for (FoodTrayItemType itemType : FoodTrayItemType.values()) {
                if (itemType == FoodTrayItemType.NONE) continue;

                variants.partialState()
                        .with(FoodTrayBlock.FACING, facing)
                        .with(FoodTrayBlock.ITEM_TYPE, itemType)
                        .with(FoodTrayBlock.STACK_COUNT, 0)
                        .addModels(new ConfiguredModel(emptyTrayModel,
                                0, (int) facing.toYRot(), false));

                for (int stackCount = 1; stackCount <= itemType.getMaxStacksOnTray(); stackCount++) {
                    String modelName = "food_tray_" + itemType.getSerializedName() + "_" + stackCount;
                    ModelFile modelFile = models().getExistingFile(modLoc("block/tray/" + modelName));

                    variants.partialState()
                            .with(FoodTrayBlock.FACING, facing)
                            .with(FoodTrayBlock.ITEM_TYPE, itemType)
                            .with(FoodTrayBlock.STACK_COUNT, stackCount)
                            .addModels(new ConfiguredModel(modelFile,
                                    0, (int) facing.toYRot(), false));
                }

                if (itemType.getMaxStacksOnTray() < stackCountProperty.getPossibleValues().stream().mapToInt(Integer::intValue).max().orElse(0) ) {
                    ModelFile fallbackModel;
                    if (itemType.getMaxStacksOnTray() > 0) {
                        String lastValidModelName = "food_tray_" + itemType.getSerializedName() + "_" + itemType.getMaxStacksOnTray();
                        fallbackModel = models().getExistingFile(modLoc("block/tray/" + lastValidModelName));
                    } else {
                        fallbackModel = emptyTrayModel;
                    }

                    for (int invalidStackCount = itemType.getMaxStacksOnTray() + 1;
                         invalidStackCount <= stackCountProperty.getPossibleValues().stream().mapToInt(Integer::intValue).max().orElse(0);
                         invalidStackCount++) {
                        variants.partialState()
                                .with(FoodTrayBlock.FACING, facing)
                                .with(FoodTrayBlock.ITEM_TYPE, itemType)
                                .with(FoodTrayBlock.STACK_COUNT, invalidStackCount)
                                .addModels(new ConfiguredModel(fallbackModel,
                                        0, (int) facing.toYRot(), false));
                    }
                }
            }
        }
    }
}

