package com.spu.futurearmour.content.network;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.network.messages.fabricator.HandlerToggleFabricatorCrafting;
import com.spu.futurearmour.content.network.messages.fabricator.MessageToggleFabricatorCrafting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static final String MESSAGE_PROTOCOL_VERSION = "1.0";
    public static final ResourceLocation simpleChannelRL = new ResourceLocation(FutureArmour.MOD_ID, "wlbchannel");

    public static SimpleChannel simpleChannel;

    public static final byte TOGGLE_FABRICATOR_ID = 47;

    public static void registerMessages(){
        simpleChannel = NetworkRegistry.newSimpleChannel(simpleChannelRL,
                ()-> MESSAGE_PROTOCOL_VERSION,
                Networking::isThisProtocolAcceptedByClient,
                Networking::isThisProtocolAcceptedByServer);

        simpleChannel.registerMessage(TOGGLE_FABRICATOR_ID,
                MessageToggleFabricatorCrafting.class,
                MessageToggleFabricatorCrafting::encode,
                MessageToggleFabricatorCrafting::decode,
                HandlerToggleFabricatorCrafting::onMessageReceived
                );
    }

    public static boolean isThisProtocolAcceptedByServer(String protocolVersion) {
        return MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }

    public static boolean isThisProtocolAcceptedByClient(String protocolVersion) {
        return MESSAGE_PROTOCOL_VERSION.equals(protocolVersion);
    }
}
