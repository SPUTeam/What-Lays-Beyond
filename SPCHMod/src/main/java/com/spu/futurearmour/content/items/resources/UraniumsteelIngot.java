package com.spu.futurearmour.content.items.resources;

import com.spu.futurearmour.FutureArmour;
import net.minecraft.item.Item;

public class UraniumsteelIngot extends Item {
    public UraniumsteelIngot() {
        super(new Properties()
        .tab(FutureArmour.ITEM_GROUP)
        .stacksTo(64));
    }
}
