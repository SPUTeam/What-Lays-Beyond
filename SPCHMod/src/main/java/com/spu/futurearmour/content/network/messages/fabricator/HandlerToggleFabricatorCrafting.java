package com.spu.futurearmour.content.network.messages.fabricator;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Supplier;

public class HandlerToggleFabricatorCrafting {
    public static void onMessageReceived(final MessageToggleFabricatorCrafting message, Supplier<NetworkEvent.Context> ctxSupplier){
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if(!message.isMessageValid())return;

        switch (sideReceived){
            case CLIENT:
                Optional<ClientWorld> clientWorld = LogicalSidedProvider.CLIENTWORLD.get(sideReceived);
                if (!clientWorld.isPresent()) {
                    LOGGER.warn("MessageToggleFabricatorCrafting context could not provide a ClientWorld.");
                    return;
                }

                ctx.enqueueWork(() -> processOnClient(message, clientWorld.get()));
                break;
            case SERVER:
                final ServerPlayerEntity sendingPlayer = ctx.getSender();
                if (sendingPlayer == null) {
                    LOGGER.warn("EntityPlayerMP was null when MessageToggleFabricatorCrafting was received");
                }

                ctx.enqueueWork(() -> processOnServer(message, sendingPlayer));
                break;
        }
    }

    private static void processOnServer(final MessageToggleFabricatorCrafting message, ServerPlayerEntity sendingPlayer){

    }

    private static void processOnClient(final MessageToggleFabricatorCrafting message, ClientWorld clientWorld){

    }

    private static final Logger LOGGER = LogManager.getLogger();
}
