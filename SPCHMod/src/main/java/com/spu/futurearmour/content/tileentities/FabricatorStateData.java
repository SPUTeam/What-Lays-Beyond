package com.spu.futurearmour.content.tileentities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class FabricatorStateData implements IIntArray {
    public int craftTimeElapsed;
    public int craftTimeForCompletion;
    public int craftingIsOn;
    public int fabricatorPosX;
    public int fabricatorPosY;
    public int fabricatorPosZ;

    private final int CRAFT_TIME_INDEX = 0;
    private final int CRAFT_TIME_FOR_COMPLETION_INDEX = 1;
    private final int CRAFTING_IS_ON_INDEX = 2;
    private final int TE_POS_X_INDEX = 3;
    private final int TE_POS_Y_INDEX = 4;
    private final int TE_POS_Z_INDEX = 5;
    private final int END_OF_DATA_INDEX_PLUS_ONE = 6;

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
                return fabricatorPosX;
            case TE_POS_Y_INDEX:
                return fabricatorPosY;
            case TE_POS_Z_INDEX:
                return fabricatorPosZ;
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
                fabricatorPosX = value;
                break;
            case TE_POS_Y_INDEX:
                fabricatorPosY = value;
                break;
            case TE_POS_Z_INDEX:
                fabricatorPosZ = value;
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
    }

    public void loadFromNBT(CompoundNBT nbt) {
        craftTimeElapsed = nbt.getInt("CraftTimeElapsed");
        craftTimeForCompletion = nbt.getInt("CraftTimeForCompletion");
    }

    public void clean(){
        for (int i =0; i < END_OF_DATA_INDEX_PLUS_ONE; i++){
            set(i, 0);
        }
    }
}
