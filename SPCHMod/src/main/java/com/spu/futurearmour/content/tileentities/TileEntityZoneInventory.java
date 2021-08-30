package com.spu.futurearmour.content.tileentities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Predicate;

public class TileEntityZoneInventory implements IInventory {
    private final ItemStackHandler itemStackHandler;
    private Predicate<PlayerEntity> canPlayerAccessInventoryLambda = x -> false;
    private Notify setChangedNotificationLambda = () -> {
    };
    private Notify openInventoryNotificationLambda = () -> {
    };
    private Notify closeInventoryNotificationLambda = () -> {
    };

    public static TileEntityZoneInventory createForTileEntity(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLambda, Notify markDirtyNotificationLambda) {
        return new TileEntityZoneInventory(size, canPlayerAccessInventoryLambda, markDirtyNotificationLambda);
    }

    public static TileEntityZoneInventory createForClientContainer(int size) {
        return new TileEntityZoneInventory(size);
    }

    private TileEntityZoneInventory(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLambda, Notify markDirtyNotificationLambda) {
        this.itemStackHandler = new ItemStackHandler(size);
        setMarkDirtyNotificationLambda(markDirtyNotificationLambda);
        setCanPlayerAccessInventoryLambda(canPlayerAccessInventoryLambda);
    }

    private TileEntityZoneInventory(int size) {
        this.itemStackHandler = new ItemStackHandler(size);
    }

    public CompoundNBT serializeNBT() {
        return itemStackHandler.serializeNBT();
    }

    public void deserializeNBT(CompoundNBT nbt) {
        itemStackHandler.deserializeNBT(nbt);
    }

    //region Lambdas for TileEntity <-> Container communication
    public void setCanPlayerAccessInventoryLambda(Predicate<PlayerEntity> canPlayerAccessInventoryLambda) {
        this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
    }

    public void setMarkDirtyNotificationLambda(Notify markDirtyNotificationLambda) {
        this.setChangedNotificationLambda = markDirtyNotificationLambda;
    }

    public void setOpenInventoryNotificationLambda(Notify openInventoryNotificationLambda) {
        this.openInventoryNotificationLambda = openInventoryNotificationLambda;
    }

    public void setCloseInventoryNotificationLambda(Notify closeInventoryNotificationLambda) {
        this.closeInventoryNotificationLambda = closeInventoryNotificationLambda;
    }

    @Override
    public void setChanged() {
        setChangedNotificationLambda.invoke();
    }

    @Override
    public void startOpen(PlayerEntity player) {
        openInventoryNotificationLambda.invoke();
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        closeInventoryNotificationLambda.invoke();
    }
    //endregion

    //region Inventory
    @Override
    public int getContainerSize() {
        return itemStackHandler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < itemStackHandler.getSlots(); ++i) {
            if (!itemStackHandler.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return itemStackHandler.getStackInSlot(index);
    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        return itemStackHandler.extractItem(index, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        int maxPossibleItemStackSize = itemStackHandler.getSlotLimit(index);
        return itemStackHandler.extractItem(index, maxPossibleItemStackSize, false);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        itemStackHandler.setStackInSlot(index, stack);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return canPlayerAccessInventoryLambda.test(player);
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < itemStackHandler.getSlots(); ++i) {
            itemStackHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return itemStackHandler.isItemValid(index, stack);
    }

    public void decreaseAllStacks(){
        for(int i = 0; i < itemStackHandler.getSlots(); ++i){
            itemStackHandler.extractItem(i,1,false);
        }
    }

    public boolean doesItemStackFit(int index, ItemStack itemStackToInsert) {
        ItemStack leftoverItemStack = itemStackHandler.insertItem(index, itemStackToInsert, true);
        return leftoverItemStack.isEmpty();
    }


    public ItemStack increaseStackSize(int index, ItemStack itemStackToInsert) {
        ItemStack leftoverItemStack = itemStackHandler.insertItem(index, itemStackToInsert, false);
        return leftoverItemStack;
    }

    public ItemStack insertStack(ItemStack stack){
        for(int i = 0; i < getContainerSize(); i++){
            stack = increaseStackSize(i, stack);
            if(stack.isEmpty())return stack;
        }
        return stack;
    }

    public boolean canFitStack(ItemStack stack){
        for (int i =0; i < getContainerSize(); i++){
            ItemStack leftoverItemStack = itemStackHandler.insertItem(i, stack, true);
            if(leftoverItemStack.isEmpty())return true;
        }
        return false;
    }
    //endregion

    @FunctionalInterface
    public interface Notify {
        void invoke();
    }

    private static Logger LOGGER = LogManager.getLogger();
}