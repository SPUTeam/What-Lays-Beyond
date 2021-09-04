package com.spu.futurearmour.content.network.messages.fabricator;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CTSMessageToggleFabricatorCrafting {
    private boolean messageIsValid;
    private boolean nextStateIsOn;
    private Vector3i fabricatorPosition;

    public CTSMessageToggleFabricatorCrafting(Vector3i fabricatorPosition, boolean nextStateIsOn){
        this.fabricatorPosition = fabricatorPosition;
        this.nextStateIsOn = nextStateIsOn;
        this.messageIsValid = true;
    }

    private CTSMessageToggleFabricatorCrafting() {
        messageIsValid = false;
    }

    public Vector3i getFabricatorPosition(){
        return fabricatorPosition;
    }

    public boolean getNextStateIsOn(){
        return nextStateIsOn;
    }

    public static CTSMessageToggleFabricatorCrafting decode(PacketBuffer buffer) {
        CTSMessageToggleFabricatorCrafting result = new CTSMessageToggleFabricatorCrafting();
        try {
            result.nextStateIsOn = buffer.readBoolean();
            int x = buffer.readInt();
            int y = buffer.readInt();
            int z = buffer.readInt();
            result.fabricatorPosition = new Vector3i(x,y,z);
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
        buffer.writeInt(fabricatorPosition.getX());
        buffer.writeInt(fabricatorPosition.getY());
        buffer.writeInt(fabricatorPosition.getZ());
    }

    public boolean isMessageValid(){
        return messageIsValid;
    }

    @Override
    public String toString() {
        return "CTSMessageToggleFabricatorCrafting[nextStateIsOn=" + nextStateIsOn + "]";
    }

    private static final Logger LOGGER = LogManager.getLogger();
}
