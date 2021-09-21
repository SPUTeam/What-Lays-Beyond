package com.spu.futurearmour.content.tileentities.fabricator;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;

public class FabricatorStateData implements IIntArray {
    public int craftTimeElapsed;
    public int craftTimeForCompletion;
    public int craftingIsOn;
    public short fabricatorPosXLeft;
    public short fabricatorPosXRight;
    public short fabricatorPosYLeft;
    public short fabricatorPosYRight;
    public short fabricatorPosZLeft;
    public short fabricatorPosZRight;

    private final int CRAFT_TIME_INDEX = 0;
    private final int CRAFT_TIME_FOR_COMPLETION_INDEX = 1;
    private final int CRAFTING_IS_ON_INDEX = 2;
    private final int TE_POS_X_INDEX = 3;
    private final int TE_POS_Y_INDEX = 4;
    private final int TE_POS_Z_INDEX = 5;
    private final int END_OF_DATA_INDEX_PLUS_ONE = 6;

    private final ByteBuffer byteBuffer = ByteBuffer.allocate(4);

    @Override
    public int get(int index) {
        validateIndex(index);
        switch (index){
            case CRAFT_TIME_INDEX:
                return craftTimeElapsed;
            case CRAFT_TIME_FOR_COMPLETION_INDEX:
                return craftTimeForCompletion;
            case CRAFTING_IS_ON_INDEX:
                return craftingIsOn;
            case TE_POS_X_INDEX:
                return shortsToInt(fabricatorPosXLeft, fabricatorPosXRight);
            case TE_POS_Y_INDEX:
                return shortsToInt(fabricatorPosYLeft, fabricatorPosYRight);
            case TE_POS_Z_INDEX:
                return shortsToInt(fabricatorPosZLeft, fabricatorPosZRight);
            default:
                return 0;
        }
    }

    @Override
    public void set(int index, int value) {
        validateIndex(index);
        switch (index){
            case CRAFT_TIME_INDEX:
                craftTimeElapsed = value;
                break;
            case CRAFT_TIME_FOR_COMPLETION_INDEX:
                craftTimeForCompletion = value;
                break;
            case CRAFTING_IS_ON_INDEX:
                if(value != 1 && value != 0){
                    throw new IllegalArgumentException("Must be either 1 or 0");
                }
                craftingIsOn = value;
                break;
            case TE_POS_X_INDEX:
                short[] shortsX = intToShortsArray(value);
                fabricatorPosXLeft = shortsX[0];
                fabricatorPosXRight = shortsX[1];
                break;
            case TE_POS_Y_INDEX:
                short[] shortsY = intToShortsArray(value);
                fabricatorPosYLeft = shortsY[0];
                fabricatorPosYRight = shortsY[1];
                break;
            case TE_POS_Z_INDEX:
                short[] shortsZ = intToShortsArray(value);
                fabricatorPosZLeft = shortsZ[0];
                fabricatorPosZRight = shortsZ[1];
                break;
        }
    }

    @Override
    public int getCount() {
        return END_OF_DATA_INDEX_PLUS_ONE;
    }

    private void validateIndex(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    public void saveToNBT(CompoundNBT nbt) {
        nbt.putInt("CraftTimeElapsed", craftTimeElapsed);
        nbt.putInt("CraftTimeForCompletion", craftTimeForCompletion);
        nbt.putInt("CraftingIsOn", craftingIsOn);
        nbt.putShort("FabricatorPosXLeft", fabricatorPosXLeft);
        nbt.putShort("FabricatorPosXRight", fabricatorPosXRight);
        nbt.putShort("FabricatorPosYLeft", fabricatorPosYLeft);
        nbt.putShort("FabricatorPosYRight", fabricatorPosYRight);
        nbt.putShort("FabricatorPosZLeft", fabricatorPosZLeft);
        nbt.putShort("FabricatorPosZRight", fabricatorPosZRight);
    }

    public void loadFromNBT(CompoundNBT nbt) {
        craftTimeElapsed = nbt.getInt("CraftTimeElapsed");
        craftTimeForCompletion = nbt.getInt("CraftTimeForCompletion");
        craftingIsOn = nbt.getInt("CraftingIsOn");
        fabricatorPosXLeft = nbt.getShort("FabricatorPosXLeft");
        fabricatorPosXRight = nbt.getShort("FabricatorPosXRight");
        fabricatorPosXLeft = nbt.getShort("FabricatorPosXLeft");
        fabricatorPosXRight = nbt.getShort("FabricatorPosXRight");
        fabricatorPosXLeft = nbt.getShort("FabricatorPosXLeft");
        fabricatorPosXRight = nbt.getShort("FabricatorPosXRight");
    }

    public void clean(){
        for (int i =0; i < END_OF_DATA_INDEX_PLUS_ONE; i++){
            set(i, 0);
        }
    }

    private int shortsToInt(short left, short right){
        int result = 0;

        byteBuffer.clear();
        byteBuffer.putShort(left);
        byteBuffer.putShort(right);
        byteBuffer.rewind();
        result = byteBuffer.getInt();

        return result;
    }

    private short[] intToShortsArray(int input){
        short[] result = new short[2];

        byteBuffer.clear();
        byteBuffer.putInt(input);
        byteBuffer.rewind();
        result[0] = byteBuffer.getShort();
        result[1] = byteBuffer.getShort();

        return result;
    }

    private static final Logger LOGGER = LogManager.getLogger();
}
