package com.spu.futurearmour.content.recipes.fabricator;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.tileentities.FabricatorControllerTileEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

import javax.annotation.Nonnull;

public interface IFabricatorRecipe extends IRecipe<FabricatorControllerTileEntity> {
    ResourceLocation RECIPE_TYPE = new ResourceLocation(FutureArmour.MOD_ID, "fabricating");

    @Nonnull
    @Override
    default IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.get(RECIPE_TYPE);
    }

    @Override
    default boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_){
        return false;
    }
}
