package com.spu.futurearmour.content.armour;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public interface LockableArmourItem {
    public default boolean isLocked(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (!nbt.contains("locked")) return false;
        boolean retVal = nbt.getBoolean("locked");
        return retVal;
    }

    public default void lock(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putBoolean("locked", true);
        stack.setTag(nbt);
    }

    public default void unlock(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putBoolean("locked", false);
        stack.setTag(nbt);
    }

    public default void generateGUID(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        if (nbt.contains("guid")) return;

        long guid = UUID.randomUUID().getMostSignificantBits();
        nbt.putLong("guid", guid);
        stack.setTag(nbt);
    }

    public default long getGUID(ItemStack stack){
        CompoundNBT nbt = stack.getOrCreateTag();
        if (nbt.contains("guid")) {
            return nbt.getLong("guid");
        }else{
            generateGUID(stack);
            return stack.getTag().getLong("guid");
        }
    }
}
