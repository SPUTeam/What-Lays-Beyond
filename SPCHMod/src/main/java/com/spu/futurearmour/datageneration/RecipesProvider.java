package com.spu.futurearmour.datageneration;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.setup.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class RecipesProvider extends RecipeProvider {
    public RecipesProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ItemRegistry.NYLON_ROLL.get())
                .pattern("x x")
                .pattern(" x ")
                .pattern("x x")
                .define('x', ItemRegistry.PLASTIC_SHEET.get().asItem())
                .group(FutureArmour.MOD_ID)
                .unlockedBy("has_item", has(ItemRegistry.PLASTIC_SHEET.get().asItem()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.STEEL_PICKAXE.get())
                .pattern("sss")
                .pattern(" i ")
                .pattern(" r ")
                .define('s', ItemRegistry.STEEL_INGOT.get().asItem())
                .define('i', Tags.Items.INGOTS_IRON)
                .define('r', ItemRegistry.RUBBER_PIECE.get().asItem())
                .group(FutureArmour.MOD_ID)
                .unlockedBy("has_item", has(ItemRegistry.STEEL_INGOT.get().asItem()))
                .save(consumer);
    }
}
