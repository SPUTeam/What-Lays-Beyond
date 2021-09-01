package com.spu.futurearmour.content.network.messages.fabricator;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CTSMessageToggleFabricatorCrafting {
    private boolean messageIsValid;
    private boolean nextStateIsOn;
    private Vector3d fabricatorPosition;

    public CTSMessageToggleFabricatorCrafting(Vector3d fabricatorPosition, boolean nextStateIsOn){
        this.fabricatorPosition = fabricatorPosition;
        this.nextStateIsOn = nextStateIsOn;
        this.messageIsValid = true;
    }

    private CTSMessageToggleFabricatorCrafting() {
        messageIsValid = false;
    }

    public Vector3d getFabricatorPosition(){
        return fabricatorPosition;
    }

    public boolean getNextStateIsOn(){
        return nextStateIsOn;
    }

    public static CTSMessageToggleFabricatorCrafting decode(PacketBuffer buffer) {
        CTSMessageToggleFabricatorCrafting result = new CTSMessageToggleFabricatorCrafting();
        try {
            result.nextStateIsOn = buffer.readBoolean();
            double x = buffer.readDouble();
            double y = buffer.readDouble();
            double z = buffer.readDouble();
            result.fabricatorPosition = new Vector3d(x,z,y);
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
        buffer.writeDouble(fabricatorPosition.x);
        buffer.writeDouble(fabricatorPosition.y);
        buffer.writeDouble(fabricatorPosition.z);
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
