package com.spu.futurearmour.content.armour;

import com.spu.futurearmour.setup.ItemRegistry;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ModArmorMaterial implements IArmorMaterial {
    PILOT_SUIT("pilot_suit", 40, new int[]{3, 5, 6, 3}, 20, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F, () -> {
        return Ingredient.of(ItemRegistry.NYLON_ROLL.get());
    });

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyValue<Ingredient> repairMaterial;


    ModArmorMaterial(String name,
                     int maxDamageFactor,
                     int[] damageReductionAmountArray,
                     int enchantability,
                     SoundEvent soundEvent,
                     float toughness,
                     float knockbackResistance,
                     Supplier<Ingredient> repairMaterial) {
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    public int getDurabilityForSlot(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * this.maxDamageFactor;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType slotIn) {
        return this.damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.soundEvent;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }

    @OnlyIn(Dist.CLIENT)
    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
