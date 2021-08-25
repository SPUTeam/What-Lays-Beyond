package com.spu.futurearmour.content.blocks.fabricator;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class FabricatorStateData implements IIntArray {
    public int craftTimeElapsed;
    public int craftTimeForCompletion;

    private final int CRAFT_TIME_INDEX = 0;
    private final int CRAFT_TIME_FOR_COMPLETION_INDEX = 1;
    private final int END_OF_DATA_INDEX_PLUS_ONE = 2;

    @Override
    public int get(int index) {
        validateIndex(index);
        switch (index){
            case CRAFT_TIME_INDEX:
                return craftTimeElapsed;
            case CRAFT_TIME_FOR_COMPLETION_INDEX:
                return craftTimeForCompletion;
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
}
