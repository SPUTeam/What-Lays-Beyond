package com.spu.futurearmour.content.items.armour;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.armour.LockableArmourItem;
import com.spu.futurearmour.content.armour.ModArmorMaterial;
import com.spu.futurearmour.content.armour.SlotBlockingArmourItem;
import com.spu.futurearmour.content.armour.pilotsuit.PilotCostumeCustomModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PilotSuitChestplate extends SlotBlockingArmourItem implements LockableArmourItem {
    public PilotSuitChestplate() {
        super(ModArmorMaterial.PILOT_SUIT, EquipmentSlotType.CHEST, new Properties()
                .tab(FutureArmour.ITEM_GROUP), EquipmentSlotType.HEAD);
    }

    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        PilotCostumeCustomModel model = new PilotCostumeCustomModel(EquipmentSlotType.CHEST);
        return (A) model;
    }

    @Override
    public void appendHoverText(ItemStack item, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        LockableArmourItem lockableItem = (LockableArmourItem) item.getItem();
        boolean locked = lockableItem.isLocked(item);
        String lockState = locked ? "Locked" : "Unlocked";
        TextFormatting formatting = locked ? TextFormatting.DARK_RED : TextFormatting.DARK_GREEN;
        tooltip.add(new StringTextComponent(lockState).withStyle(formatting));
    }
}
