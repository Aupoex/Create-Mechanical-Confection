package com.upo.createmechanicalconfection.content;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.upo.createmechanicalconfection.CreateMechanicalConfection;
import com.upo.createmechanicalconfection.content.blocks.batter.TankCakeBatterBlock;
import com.upo.createmechanicalconfection.content.blocks.batter.TubeCakeBatterBlock;
import com.upo.createmechanicalconfection.content.blocks.cake.CogCakeBlock;
import com.upo.createmechanicalconfection.content.blocks.cake.TankCakeBlock;
import com.upo.createmechanicalconfection.content.blocks.cake.TubeCakeBlock;
import com.upo.createmechanicalconfection.content.blocks.filled.FilledCogCakeBatterBlock;
import com.upo.createmechanicalconfection.content.blocks.MechanicalOvenBlock;
import com.upo.createmechanicalconfection.content.blocks.batter.CogCakeBatterBlock;
import com.upo.createmechanicalconfection.content.blocks.batter.RawCakeBatterBlock;
import com.upo.createmechanicalconfection.content.blocks.filled.FilledTankCakeBatterBlock;
import com.upo.createmechanicalconfection.content.blocks.filled.FilledTubeCakeBatterBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.upo.createmechanicalconfection.content.items.MechanicalOvenBlockItem;

import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;


public class CMCBlocks {
    private static final CreateRegistrate REGISTRATE = CreateMechanicalConfection.registrate();

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, CreateMechanicalConfection.MODID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, CreateMechanicalConfection.MODID);

    //烤箱
    public static final BlockEntry<MechanicalOvenBlock> MECHANICAL_OVEN = REGISTRATE
            .block("mechanical_oven", MechanicalOvenBlock::new)
            .properties(p -> p.mapColor(MapColor.METAL)
                    .strength(3.0f, 6.0f)
                    .sound(SoundType.METAL)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> state.getValue(MechanicalOvenBlock.LIT) ? 12 : 0)
                    .noOcclusion()
            )
            .transform(pickaxeOnly())
            .item(MechanicalOvenBlockItem::new)
            .build()
            .register();


    //原始蛋糕胚
    public static final DeferredHolder<Block, Block> RAW_CAKE_BATTER_BLOCK = BLOCKS.register("raw_cake_batter_block",
            () -> new RawCakeBatterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .strength(0.3f)
                    .sound(SoundType.SLIME_BLOCK)
                    .noOcclusion()
            )
    );
    public static final DeferredHolder<Item, Item> RAW_CAKE_BATTER_BLOCK_ITEM = ITEMS.register("raw_cake_batter_block",
            () -> new BlockItem(RAW_CAKE_BATTER_BLOCK.get(), new Item.Properties())
    );


    //齿轮蛋糕
    public static final DeferredHolder<Block, CogCakeBlock> COG_CAKE_BLOCK =
            BLOCKS.register("cog_cake_block",
                    () -> new CogCakeBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .strength(0.7f)
                            .sound(SoundType.WOOL)
                            .noOcclusion()
                    )
            );
    public static final DeferredHolder<Item, BlockItem> COG_CAKE_BLOCK_ITEM =
            ITEMS.register("cog_cake_block",
                    () -> new BlockItem(COG_CAKE_BLOCK.get(), new Item.Properties().stacksTo(16)

            ));
    public static final DeferredHolder<Block, Block> COG_CAKE_BATTER_BLOCK = BLOCKS.register("cog_cake_batter_block",
            () -> new CogCakeBatterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .strength(0.3f)
                    .sound(SoundType.SLIME_BLOCK)
                    .noOcclusion()
            )
    );
    public static final DeferredHolder<Item, Item> COG_CAKE_BATTER_BLOCK_ITEM = ITEMS.register("cog_cake_batter_block",
            () -> new BlockItem(COG_CAKE_BATTER_BLOCK.get(), new Item.Properties())
    );
    public static final DeferredHolder<Block, Block> FILLED_COG_CAKE_BATTER_BLOCK = BLOCKS.register("filled_cog_cake_batter_block",
            () -> new FilledCogCakeBatterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_YELLOW)
                    .strength(0.4f)
                    .sound(SoundType.MUD)
                    .noOcclusion()
            )
    );
    public static final DeferredHolder<Item, Item> FILLED_COG_CAKE_BATTER_BLOCK_ITEM = ITEMS.register("filled_cog_cake_batter_block",
            () -> new BlockItem(FILLED_COG_CAKE_BATTER_BLOCK.get(), new Item.Properties())
    );


    //电子管蛋糕
    public static final DeferredHolder<Block, TubeCakeBlock> TUBE_CAKE_BLOCK =
            BLOCKS.register("tube_cake_block",
                    () -> new TubeCakeBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.WOOD)
                            .strength(0.7f)
                            .sound(SoundType.WOOL)
                            .noOcclusion()
                    )
            );
    public static final DeferredHolder<Item, BlockItem> TUBE_CAKE_BLOCK_ITEM =
            ITEMS.register("tube_cake_block",
                    () -> new BlockItem(TUBE_CAKE_BLOCK.get(), new Item.Properties().stacksTo(16)
                    ));
    public static final DeferredHolder<Block, Block> TUBE_CAKE_BATTER_BLOCK = BLOCKS.register("tube_cake_batter_block",
            () -> new TubeCakeBatterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .strength(0.3f)
                    .sound(SoundType.SLIME_BLOCK)
                    .noOcclusion()
            )
    );
    public static final DeferredHolder<Item, Item> TUBE_CAKE_BATTER_BLOCK_ITEM = ITEMS.register("tube_cake_batter_block",
            () -> new BlockItem(TUBE_CAKE_BATTER_BLOCK.get(), new Item.Properties())
    );
    public static final DeferredHolder<Block, Block> FILLED_TUBE_CAKE_BATTER_BLOCK = BLOCKS.register("filled_tube_cake_batter_block",
            () -> new FilledTubeCakeBatterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_YELLOW)
                    .strength(0.4f)
                    .sound(SoundType.MUD)
                    .noOcclusion()
            )
    );
    public static final DeferredHolder<Item, Item> FILLED_TUBE_CAKE_BATTER_BLOCK_ITEM = ITEMS.register("filled_tube_cake_batter_block",
            () -> new BlockItem(FILLED_TUBE_CAKE_BATTER_BLOCK.get(), new Item.Properties())
    );


    //熔岩储罐蛋糕
    public static final DeferredHolder<Block, TankCakeBlock> TANK_CAKE_BLOCK =
            BLOCKS.register("tank_cake_block",
                    () -> new TankCakeBlock(BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .strength(0.7f)
                            .sound(SoundType.WOOL)
                            .noOcclusion()
                    )
            );
    public static final DeferredHolder<Item, BlockItem> TANK_CAKE_BLOCK_ITEM =
            ITEMS.register("tank_cake_block",
                    () -> new BlockItem(TANK_CAKE_BLOCK.get(), new Item.Properties().stacksTo(16)
                    ));
    public static final DeferredHolder<Block, Block> TANK_CAKE_BATTER_BLOCK = BLOCKS.register("tank_cake_batter_block",
            () -> new TankCakeBatterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(0.3f)
                    .sound(SoundType.SLIME_BLOCK)
                    .noOcclusion()
            )
    );
    public static final DeferredHolder<Item, Item> TANK_CAKE_BATTER_BLOCK_ITEM = ITEMS.register("tank_cake_batter_block",
            () -> new BlockItem(TANK_CAKE_BATTER_BLOCK.get(), new Item.Properties())
    );
    public static final DeferredHolder<Block, Block> FILLED_TANK_CAKE_BATTER_BLOCK = BLOCKS.register("filled_tank_cake_batter_block",
            () -> new FilledTankCakeBatterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(0.4f)
                    .sound(SoundType.MUD)
                    .noOcclusion()
            )
    );
    public static final DeferredHolder<Item, Item> FILLED_TANK_CAKE_BATTER_BLOCK_ITEM = ITEMS.register("filled_tank_cake_batter_block",
            () -> new BlockItem(FILLED_TANK_CAKE_BATTER_BLOCK.get(), new Item.Properties())
    );




    public static void registerAll(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);

    }
    public static void registerAll() {}
}
