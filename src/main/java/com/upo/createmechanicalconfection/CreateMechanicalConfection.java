package com.upo.createmechanicalconfection;

import com.upo.createmechanicalconfection.content.CMCBlockEntities;
import com.upo.createmechanicalconfection.data.*;
import com.upo.createmechanicalconfection.interaction.ModArmInteractionPointTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import com.upo.createmechanicalconfection.data.ModCraftingRecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.upo.createmechanicalconfection.content.CMCBlocks;
import com.upo.createmechanicalconfection.content.CMCItems;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import com.upo.createmechanicalconfection.client.render.blockentity.MechanicalOvenRenderer;
import java.util.concurrent.CompletableFuture;

@Mod(CreateMechanicalConfection.MODID)
public class CreateMechanicalConfection {

    public static final String MODID = "createmechanicalconfection";

    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID)
            .defaultCreativeTab(CMCCreativeTabs.MAIN_TAB.getKey());


    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }


    public CreateMechanicalConfection(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        REGISTRATE.registerEventListeners(modEventBus);
        ModArmInteractionPointTypes.register(modEventBus);
        CMCBlocks.registerAll(modEventBus);
        CMCItems.ITEMS.register(modEventBus);
        CMCBlockEntities.register();
        CMCCreativeTabs.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(CreateMechanicalConfection::gatherData);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::clientSetup);

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(CMCBlocks.MECHANICAL_OVEN.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(CMCBlocks.TUBE_CAKE_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(CMCBlocks.TANK_CAKE_BLOCK.get(), RenderType.cutoutMipped());
            BlockEntityRenderers.register(
                    CMCBlockEntities.MECHANICAL_OVEN_BE.get(),
                    MechanicalOvenRenderer::new
            );

        });
    }


    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                CMCBlockEntities.MECHANICAL_OVEN_BE.get(),
                (be, context) -> be.getItemHandlerCapability(context)
        );

    }

    public static CreateRegistrate registrate() {
        return REGISTRATE;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}
    private void addCreative(BuildCreativeModeTabContentsEvent event) {}
    @SubscribeEvent public void onServerStarting(ServerStartingEvent event) {}
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    { @SubscribeEvent public static void onClientSetup(FMLClientSetupEvent event) {}}

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                event.includeServer(),
                new ModCraftingRecipeProvider(packOutput, lookupProvider)
        );
        generator.addProvider(
                event.includeServer(),
                new ModBlockTagsProvider(packOutput, lookupProvider, existingFileHelper)
        );
        generator.addProvider(
                event.includeServer(),
                new ModMixingRecipeProvider(packOutput, lookupProvider)
        );
        generator.addProvider(
                event.includeServer(),
                new ModDeployingRecipeProvider(packOutput, lookupProvider)
        );
        generator.addProvider(
                event.includeServer(),
                new ModPressingRecipeProvider(packOutput, lookupProvider)
        );
        generator.addProvider(
                event.includeServer(),
                new ModCuttingRecipeProvider(packOutput, lookupProvider)
        );

    }

}
