package com.spu.futurearmour.setup;

import net.minecraftforge.eventbus.api.IEventBus;

public class ClientSideOnlyModEventRegistry {
    private final IEventBus eventBus;

    public ClientSideOnlyModEventRegistry(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void registerClientOnlyEvents(){
        eventBus.register(com.spu.futurearmour.content.blocks.aluminium.StartupClientOnly.class);
        eventBus.register(com.spu.futurearmour.content.blocks.copper.StartupClientOnly.class);
        eventBus.register(com.spu.futurearmour.content.blocks.titanium.StartupClientOnly.class);
        eventBus.register(com.spu.futurearmour.content.blocks.magnesium.StartupClientOnly.class);
        eventBus.register(com.spu.futurearmour.content.blocks.sulfur.StartupClientOnly.class);
        eventBus.register(com.spu.futurearmour.content.blocks.uranium.StartupClientOnly.class);
    }
}
