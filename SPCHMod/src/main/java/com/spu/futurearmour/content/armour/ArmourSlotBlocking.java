package com.spu.futurearmour.content.armour;

import com.spu.futurearmour.FutureArmour;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

@Mod.EventBusSubscriber(modid = FutureArmour.MOD_ID)
public class ArmourSlotBlocking {
    public static Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void TryBlockSlots(LivingEquipmentChangeEvent event) {
        if (event.getEntityLiving().level.isClientSide()) return;
        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        EquipmentSlotType[] totalBlockedSlots = getTotalBlockedSlots(player.inventory);
        DropFromBlockedSlots(player.inventory, totalBlockedSlots);
    }

    private static EquipmentSlotType[] getTotalBlockedSlots(PlayerInventory inventory) {
        ArrayList<EquipmentSlotType> retList = new ArrayList<>();

        for (EquipmentSlotType slot : EquipmentSlotType.values()) {
            ItemStack itemInSlot = inventory.getArmor(slot.getIndex());
            if (!(itemInSlot.getItem() instanceof SlotBlockingArmourItem)) continue;
            SlotBlockingArmourItem blockingItemType = (SlotBlockingArmourItem) itemInSlot.getItem();

            for (EquipmentSlotType blockedSlot : blockingItemType.getBlockedSlots()) {
                if (!retList.contains(blockedSlot)) {
                    retList.add(blockedSlot);
                }
            }
        }
        EquipmentSlotType[] reyArray = new EquipmentSlotType[retList.size()];
        retList.toArray(reyArray);
        return reyArray;
    }

    private static void DropFromBlockedSlots(PlayerInventory inventory, EquipmentSlotType[] blockedSlots) {
        for (EquipmentSlotType slot : blockedSlots) {
            ItemStack itemInSlot = inventory.getArmor(slot.getIndex());
            if (itemInSlot == null) continue;

            inventory.player.drop(itemInSlot, false, false);
            inventory.player.setItemSlot(slot, ItemStack.EMPTY);
        }
    }


    @SubscribeEvent
    public static void TryKeepLockedItems(LivingEquipmentChangeEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        ItemStack itemFrom = event.getFrom();
        Item itemFromType = itemFrom.getItem();

        if (!(itemFromType instanceof LockableArmourItem)) return;
        if (event.getSlot().getType() != EquipmentSlotType.Group.ARMOR) return;
//        ((LockableArmourItem) itemFromType).lock(itemFrom);
        if (!((LockableArmourItem) itemFromType).isLocked(itemFrom)) return;

        if (event.getEntityLiving().level.isClientSide()) {
            player.inventory.setPickedItem(ItemStack.EMPTY);
            player.inventory.setCarried(ItemStack.EMPTY);
            return;
        }

        revertItemSwap(event);
    }

    private static void revertItemSwap(LivingEquipmentChangeEvent event) {
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();

        player.inventory.setCarried(ItemStack.EMPTY);
        player.setItemSlot(event.getSlot(), event.getFrom());
        player.inventory.add(event.getTo());
    }


    @SubscribeEvent
    public static void TryRemoveDuplicate(TickEvent.PlayerTickEvent event) {
        if (event.player.isLocalPlayer()) return;

        ArrayList<ItemStack> originals = getOriginals(event);
        if(originals.size() < 1)return;
        HashMap<Integer, ItemStack> duplicates = getDuplicates(event.player.inventory);
        deleteDuplicates(originals, duplicates, event.player.inventory);
    }

    public static ArrayList<ItemStack> getOriginals(TickEvent.PlayerTickEvent event) {
        ArrayList<ItemStack> result = new ArrayList<>();

        for (ItemStack item : event.player.getArmorSlots()) {
            if (item.getItem() instanceof LockableArmourItem) {
                LockableArmourItem lockableType = (LockableArmourItem) item.getItem();
                if(lockableType.isLocked(item)){
                    lockableType.generateGUID(item);
                    result.add(item);
                }
            }
        }

        return result;
    }

    public static HashMap<Integer, ItemStack> getDuplicates(PlayerInventory inventory) {
        HashMap<Integer, ItemStack> result = new HashMap<>();

        for (int i = 0; i < 36; i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getItem() instanceof LockableArmourItem) {
                result.put(i, item);
            }
        }

        return result;
    }

    public static void deleteDuplicates(ArrayList<ItemStack> originals, HashMap<Integer, ItemStack> duplicates, PlayerInventory inventory) {
        for (ItemStack originalItem : originals) {
            LockableArmourItem originalLockable = (LockableArmourItem) originalItem.getItem();
            long originalGUID = originalLockable.getGUID(originalItem);

            for (int i = 0; i < 36; i++) {
                if (!duplicates.containsKey(i)) continue;

                ItemStack duplicateItem = duplicates.get(i);
                if (originalItem.getItem() != duplicateItem.getItem()) continue;

                LockableArmourItem duplicateLockable = (LockableArmourItem) duplicateItem.getItem();
                if (originalGUID == duplicateLockable.getGUID(duplicateItem)) {
                    inventory.setItem(i, ItemStack.EMPTY);
                }
            }
        }
    }


    @SubscribeEvent
    public static void TryPreventThrowing(ItemTossEvent event) {
        if (event.getPlayer().isLocalPlayer()) return;

        ItemStack stack = event.getEntityItem().getItem();
        if (!(stack.getItem() instanceof LockableArmourItem)) return;

        LockableArmourItem lockable = (LockableArmourItem) stack.getItem();
        if(lockable.isLocked(stack)){
            event.setCanceled(true);
        }
    }
}