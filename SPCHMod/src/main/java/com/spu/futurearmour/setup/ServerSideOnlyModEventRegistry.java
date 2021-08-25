package com.spu.futurearmour.setup;

import net.minecraftforge.eventbus.api.IEventBus;

public class ServerSideOnlyModEventRegistry {
    private final IEventBus eventBus;

    public ServerSideOnlyModEventRegistry(IEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void registerServerOnlyEvents() {

    }
}
