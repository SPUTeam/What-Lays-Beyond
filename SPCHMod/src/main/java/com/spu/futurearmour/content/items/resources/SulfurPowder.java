package com.spu.futurearmour.content.items.resources;

import com.spu.futurearmour.FutureArmour;
import net.minecraft.item.Item;

public class SulfurPowder extends Item {
    public SulfurPowder() {
        super(new Properties()
        .tab(FutureArmour.ITEM_GROUP)
        .stacksTo(64));
    }
}
