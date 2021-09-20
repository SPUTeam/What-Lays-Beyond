package com.spu.futurearmour.content.network.messages.fabricator;

import com.spu.futurearmour.content.recipes.fabricator.FabricatorRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3i;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CTSMessageFabricatorAssembleRecipe {
    private boolean messageIsValid;
    private ResourceLocation recipeID;
    private Vector3i fabricatorPosition;

    public CTSMessageFabricatorAssembleRecipe(Vector3i fabricatorPosition, ResourceLocation recipeID){
        this.fabricatorPosition = fabricatorPosition;
        this.recipeID = recipeID;
        this.messageIsValid = true;
    }

    private CTSMessageFabricatorAssembleRecipe() {
        messageIsValid = false;
    }

    public Vector3i getFabricatorPosition(){
        return fabricatorPosition;
    }

    public ResourceLocation getRecipeID(){
        return recipeID;
    }

    public static CTSMessageFabricatorAssembleRecipe decode(PacketBuffer buffer) {
        CTSMessageFabricatorAssembleRecipe result = new CTSMessageFabricatorAssembleRecipe();
        try {
            result.recipeID = buffer.readResourceLocation();
            int x = buffer.readInt();
            int y = buffer.readInt();
            int z = buffer.readInt();
            result.fabricatorPosition = new Vector3i(x,y,z);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading CTSMessageFabricatorAssembleRecipe: " + e);
            return result;
        }
        result.messageIsValid = true;
        return result;
    }

    public void encode(PacketBuffer buffer) {
        if (!messageIsValid) return;
        buffer.writeResourceLocation(recipeID);
        buffer.writeInt(fabricatorPosition.getX());
        buffer.writeInt(fabricatorPosition.getY());
        buffer.writeInt(fabricatorPosition.getZ());
    }

    public boolean isMessageValid(){
        return messageIsValid;
    }

    @Override
    public String toString() {
        return "CTSMessageFabricatorAssembleRecipe[recipeID=" + recipeID + "]";
    }

    private static final Logger LOGGER = LogManager.getLogger();
}
