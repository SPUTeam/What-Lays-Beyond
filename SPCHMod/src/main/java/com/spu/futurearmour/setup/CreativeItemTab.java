package com.spu.futurearmour.setup;

import com.spu.futurearmour.FutureArmour;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeItemTab extends ItemGroup {
    public CreativeItemTab() {
        super(FutureArmour.MOD_ID);
    }

    @Override
    public ItemStack makeIcon() {
        return ItemRegistry.PILOT_SUIT_CHESTPLATE.get().getDefaultInstance();
    }
}
