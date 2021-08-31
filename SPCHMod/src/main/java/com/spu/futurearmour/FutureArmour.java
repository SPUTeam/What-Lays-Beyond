package com.spu.futurearmour;

import com.spu.futurearmour.content.network.Networking;
import com.spu.futurearmour.setup.*;
import com.spu.futurearmour.content.world.OreGeneration;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(FutureArmour.MOD_ID)
public class FutureArmour
{
    public static final String MOD_ID = "futurearmour";

    private static final Logger LOGGER = LogManager.getLogger();

    public static final CreativeItemTab ITEM_GROUP = new CreativeItemTab();

    public FutureArmour() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        Registration.register();

        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, OreGeneration::generateOres);

        MinecraftForge.EVENT_BUS.register(this);

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        final ClientSideOnlyModEventRegistry clientSideOnlyModEventRegistry = new ClientSideOnlyModEventRegistry(modEventBus);
        final ServerSideOnlyModEventRegistry serverSideOnlyModEventRegistry = new ServerSideOnlyModEventRegistry(modEventBus);

        registerCommonEvents(modEventBus);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> clientSideOnlyModEventRegistry::registerClientOnlyEvents);
        DistExecutor.safeRunWhenOn(Dist.DEDICATED_SERVER, () -> serverSideOnlyModEventRegistry::registerServerOnlyEvents);

        RecipeTypesRegistry.RECIPE_SERIALIZER_TYPES.register(modEventBus);
    }

    private void registerCommonEvents(IEventBus eventBus){

    }

    @SubscribeEvent
    private void setup(final FMLCommonSetupEvent event){
        Networking.registerMessages();
    }

    @OnlyIn(Dist.CLIENT)
    private void doClientStuff(final FMLClientSetupEvent event) {
        Registration.registerClientOnly(event);
        RenderTypeLookup.setRenderLayer(BlockRegistry.FABRICATOR_CONTROLLER_BLOCK.get(), RenderType.translucent());
    }
}
