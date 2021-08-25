package com.spu.futurearmour.setup;

import com.spu.futurearmour.content.ModItemModelsProperties;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class Registration {
    public static void register(){
        ModBlockStateProperties.register();
        BlockRegistry.register();
        ContainerTypeRegistry.register();
        TileEntityTypeRegistry.register();
        ItemRegistry.register();
    }

    public static void registerClientOnly(FMLClientSetupEvent event){
        ModItemModelsProperties.register();
        ContainerTypeRegistry.registerScreens(event);
    }
}
