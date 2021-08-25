package com.spu.futurearmour.content.armour;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;

public class SlotBlockingArmourItem extends ArmorItem{
    private EquipmentSlotType[] blockedSlots;

    public SlotBlockingArmourItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties, EquipmentSlotType... blockedSlots) {
        super(armorMaterial, slotType, properties);
        this.blockedSlots = blockedSlots;
    }

    public EquipmentSlotType[] getBlockedSlots(){
        return blockedSlots;
    }
}