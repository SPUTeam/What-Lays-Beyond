package com.spu.futurearmour.setup;

import net.minecraftforge.eventbus.api.IEventBus;

public class ClientSideOnlyModEventRegistry {
    private final IEventBus eventBus;

    public ClientSideOnlyModEventRegistry(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void registerClientOnlyEvents(){
        eventBus.register(com.spu.futurearmour.content.blocks.resources.aluminium.StartupClientOnly.class);
        eventBus.register(com.spu.futurearmour.content.blocks.resources.copper.StartupClientOnly.class);
        eventBus.register(com.spu.futurearmour.content.blocks.resources.titanium.StartupClientOnly.class);
        eventBus.register(com.spu.futurearmour.content.blocks.resources.magnesium.StartupClientOnly.class);
        eventBus.register(com.spu.futurearmour.content.blocks.resources.sulfur.StartupClientOnly.class);
        eventBus.register(com.spu.futurearmour.content.blocks.resources.uranium.StartupClientOnly.class);
    }
}
