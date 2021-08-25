package com.spu.futurearmour.setup;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.recipes.fabricator.IFabricatorRecipe;
import com.spu.futurearmour.content.recipes.fabricator.FabricatorRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public final class RecipeTypesRegistry {
    //region Serializers
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZER_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FutureArmour.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<FabricatorRecipe>> FABRICATING_SERIALIZER =
            registerSerializer("fabricating", FabricatorRecipe.Serializer::new);

    private static <T extends IRecipe<?>> RegistryObject<IRecipeSerializer<T>> registerSerializer(String name, Supplier<IRecipeSerializer<T>> serializer) {
        return RECIPE_SERIALIZER_TYPES.register(name, serializer);
    }
    //endregion


    //region RecipeTypes
    public static final IRecipeType<FabricatorRecipe> FABRICATING_RECIPE = registerType(IFabricatorRecipe.RECIPE_TYPE);

    private static IRecipeType<FabricatorRecipe> registerType(ResourceLocation recipeType) {
        return Registry.register(Registry.RECIPE_TYPE, recipeType, new RegistryType<>());
    }

    private static class RegistryType<T extends IRecipe<?>> implements IRecipeType<T> {
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }
    //endregion

    private static Logger LOGGER = LogManager.getLogger();
}
