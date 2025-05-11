package com.upo.createmechanicalconfection.content.blocks;

import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import java.util.Optional;

public abstract class BaseFilledCakeBatterBlock extends BaseCakeBatterBlock {


    private static final String FILLED_PREFIX = "filled_";
    private static final String BATTER_SUFFIX = "_cake_batter_block";
    private static final String CAKE_SUFFIX = "_cake_block";

    protected BaseFilledCakeBatterBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }


    public Item getBakedResultItem() {
        ResourceLocation currentLocation = BuiltInRegistries.BLOCK.getKey(this);
        if (currentLocation == null) {
            return Items.AIR;
        }
        String path = currentLocation.getPath();

        if (path.startsWith(FILLED_PREFIX) && path.endsWith(BATTER_SUFFIX)) {

            int startIndex = FILLED_PREFIX.length();

            int endIndex = path.length() - BATTER_SUFFIX.length();

            if (startIndex < endIndex) {
                String cakeType = path.substring(startIndex, endIndex);

                String resultPath = cakeType + CAKE_SUFFIX;

                ResourceLocation resultLocation = ResourceLocation.fromNamespaceAndPath(
                        CreateMechanicalConfection.MODID,
                        resultPath
                );
                Optional<Block> resultBlockOptional = BuiltInRegistries.BLOCK.getOptional(resultLocation);
                if (resultBlockOptional.isPresent()) {
                    Item resultItem = resultBlockOptional.get().asItem();
                    if (resultItem != Items.AIR) {
                        return resultItem;
                    }
                }
            }
        }
        return Items.AIR;
    }
    public int getBakingDuration() {
        return 200;
    }
}

