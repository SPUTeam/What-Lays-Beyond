package com.spu.futurearmour.setup;

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
        ContainerTypeRegistry.registerScreens(event);
    }
}
