package com.spu.futurearmour.setup;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.client.gui.fabricator.FabricatorScreen;
import com.spu.futurearmour.content.containers.FabricatorControllerContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerTypeRegistry {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, FutureArmour.MOD_ID);

    public static void register() {
        CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event){
        ScreenManager.register(FABRICATOR_CONTROLLER_CONTAINER_TYPE.get(), FabricatorScreen::new);
    }

    public static final RegistryObject<ContainerType<FabricatorControllerContainer>> FABRICATOR_CONTROLLER_CONTAINER_TYPE =
            CONTAINER_TYPES.register("fabricator_controller_ct", () ->
                    IForgeContainerType.create(FabricatorControllerContainer::createForClient));
}
