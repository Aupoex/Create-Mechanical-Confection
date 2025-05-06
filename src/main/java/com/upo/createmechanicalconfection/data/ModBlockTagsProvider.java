package com.upo.createmechanicalconfection.data;

import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.CMCBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {


    public static final TagKey<Block> CAKES = BlockTags.create(ResourceLocation.fromNamespaceAndPath(CreateMechanicalConfection.MODID, "cakes"));

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateMechanicalConfection.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(CAKES)
                .add(CMCBlocks.RAW_CAKE_BATTER_BLOCK.get())

                .add(CMCBlocks.COG_CAKE_BLOCK.get())
                .add(CMCBlocks.COG_CAKE_BATTER_BLOCK.get())
                .add(CMCBlocks.FILLED_COG_CAKE_BATTER_BLOCK.get())

                .add(CMCBlocks.TUBE_CAKE_BLOCK.get())
                .add(CMCBlocks.TUBE_CAKE_BATTER_BLOCK.get())
                .add(CMCBlocks.FILLED_TUBE_CAKE_BATTER_BLOCK.get())

                .addTag(BlockTags.CANDLE_CAKES);

    }

    @Override
    public String getName() {
        return "Create Mechanical Confection Block Tags";
    }
}

