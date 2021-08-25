package com.spu.futurearmour.content.recipes;

import net.minecraft.item.crafting.IRecipe;

import javax.annotation.Nullable;

@Nullable
public class CurrentCraftingProcess<T extends IRecipe> {
    public T recipe;
    public int ticksLeft;

    public CurrentCraftingProcess(T recipe, int ticksLeft) {
        this.recipe = recipe;
        this.ticksLeft = ticksLeft;
    }
}
