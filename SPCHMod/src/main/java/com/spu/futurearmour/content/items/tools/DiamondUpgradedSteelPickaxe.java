package com.spu.futurearmour.content.items.tools;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.setup.ItemTiers;
import net.minecraft.item.PickaxeItem;

public class DiamondUpgradedSteelPickaxe extends PickaxeItem {
    public DiamondUpgradedSteelPickaxe() {
        super(ItemTiers.DIAMOND_UPGRADED_STEEL, 1, -2.8F,
                new Properties()
                .tab(FutureArmour.ITEM_GROUP));
    }
}
