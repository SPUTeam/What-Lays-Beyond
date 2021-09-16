package com.spu.futurearmour.content.items.tools;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.setup.ItemTiers;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class SteelPickaxe extends PickaxeItem {
    public SteelPickaxe() {
        super(ItemTiers.STEEL, 1, -2.8F,
                new Item.Properties()
                .tab(FutureArmour.ITEM_GROUP));
    }
}
