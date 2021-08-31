package com.spu.futurearmour.content.network.messages.fabricator;

import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageToggleFabricatorCrafting {
    private boolean messageIsValid;
    private boolean nextStateIsOn;

    public MessageToggleFabricatorCrafting() {
        messageIsValid = false;
        throw new RuntimeException("not a valid way to construct the message");
    }

    public static MessageToggleFabricatorCrafting decode(PacketBuffer buffer) {
        MessageToggleFabricatorCrafting result = new MessageToggleFabricatorCrafting();
        try {
            result.nextStateIsOn = buffer.readBoolean();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading AirStrikeMessageToServer: " + e);
            return result;
        }
        result.messageIsValid = true;
        return result;
    }

    public void encode(PacketBuffer buffer) {
        if (!messageIsValid) return;
        buffer.writeBoolean(nextStateIsOn);
    }

    public boolean isMessageValid(){
        return messageIsValid;
    }

    @Override
    public String toString() {
        return "MessageToggleFabricatorCrafting[nextStateIsOn=" + nextStateIsOn + "]";
    }

    private static final Logger LOGGER = LogManager.getLogger();
}
