package com.spu.futurearmour.content.recipes.fabricator;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.spu.futurearmour.content.tileentities.FabricatorControllerTileEntity;
import com.spu.futurearmour.setup.RecipeTypesRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FabricatorRecipe implements IFabricatorRecipe {
    private final ItemStack result;
    private final int time;
    private final NonNullList<Ingredient> ingredients;
    private final ResourceLocation id;
    private final String group;

    public FabricatorRecipe(ItemStack result, NonNullList<Ingredient> ingredients, ResourceLocation id, String group, int time) {
        this.ingredients = ingredients;
        this.id = id;
        this.group = group;
        this.result = result;
        this.time = time;
        LOGGER.debug("--------------------------------------");
        LOGGER.debug("Built recipe:" + result.toString());
        for(int i =0; i < ingredients.size(); i ++){
            Optional<ItemStack> first = Arrays.stream(ingredients.get(i).getItems()).findFirst();
            if(first.isPresent()) {
                LOGGER.debug(i + " " + first.get());
            }else{
                LOGGER.debug(i);
            }
        }
        LOGGER.debug("--------------------------------------");
    }

    @Override
    public boolean matches(FabricatorControllerTileEntity tileEntity, World world) {
        int[] slotIndexes = tileEntity.getCraftingSlotIndexes();
        for (int i = 0; i < slotIndexes.length; i++) {
            if (!ingredients.get(i).test(tileEntity.getInputInventory().getItem(slotIndexes[i]))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(FabricatorControllerTileEntity tileEntity) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeType<?> getType() {
        return Registry.RECIPE_TYPE.get(RECIPE_TYPE);
    }

    public int getTime() {
        return time;
    }

    public static int getRowWidth(int row) {
        switch (row) {
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 3;
            default:
                return 0;
        }
    }

    //region Serializer
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeTypesRegistry.FABRICATING_SERIALIZER.get();
    }

    private static Map<String, Ingredient> keysFromJson(JsonObject jsonObject) {
        Map<String, Ingredient> map = Maps.newHashMap();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }
            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }
            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue()));
        }
        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    private static String[] patternFromJson(JsonArray jsonArray) {
        String[] pattern = new String[jsonArray.size()];
        if (pattern.length != 4) {
            throw new JsonSyntaxException("Invalid pattern: " + pattern.length + " rows instead of 4");
        } else {
            for (int i = 0; i < pattern.length; ++i) {
                String patternRow = JSONUtils.convertToString(jsonArray.get(i), "pattern[" + i + "]");
                if (patternRow.length() > getRowWidth(i)) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + getRowWidth(i) + " is maximum");
                }
                if (getRowWidth(i) != patternRow.length()) {
                    throw new JsonSyntaxException("Invalid pattern: row " + i + " has width " + patternRow.length() + "instead of " + getRowWidth(i));
                }
                pattern[i] = patternRow;
            }
            return pattern;
        }
    }

    private static NonNullList<Ingredient> dissolvePattern(String[] pattern, Map<String, Ingredient> ingredientMap) {
        NonNullList<Ingredient> result = NonNullList.withSize(12, Ingredient.EMPTY);
        Set<String> keys = Sets.newHashSet(ingredientMap.keySet());
        keys.remove(" ");

        for (int row = 0; row < pattern.length; row++) {
            for (int ch = 0; ch < pattern[row].length(); ch++) {
                String key = pattern[row].substring(ch, ch + 1);
                Ingredient ingredient = ingredientMap.get(key);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + key + "' but it's not defined in the key");
                }
                keys.remove(key);

                int resultIndex = ch;
                for(int i = 0; i < row; i++){
                    resultIndex += getRowWidth(i);
                }
                result.set(resultIndex, ingredient);
            }
        }

        if (!keys.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + keys);
        } else {
            return result;
        }
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<FabricatorRecipe> {

        @Override
        public FabricatorRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            String group = JSONUtils.getAsString(jsonObject, "group", "");
            int time = JSONUtils.getAsInt(jsonObject, "time");
            Map<String, Ingredient> ingredientMap = keysFromJson(JSONUtils.getAsJsonObject(jsonObject, "key"));
            String[] pattern = patternFromJson(JSONUtils.getAsJsonArray(jsonObject, "pattern"));
            NonNullList<Ingredient> ingredients = dissolvePattern(pattern, ingredientMap);
            ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "result"));
            return new FabricatorRecipe(result, ingredients, id, group, time);
        }

        @Nullable
        @Override
        public FabricatorRecipe fromNetwork(ResourceLocation id, PacketBuffer packetBuffer) {
            String group = packetBuffer.readUtf(32767);
            NonNullList<Ingredient> ingredients = NonNullList.withSize(12, Ingredient.EMPTY);

            for (int k = 0; k < ingredients.size(); ++k) {
                ingredients.set(k, Ingredient.fromNetwork(packetBuffer));
            }

            ItemStack result = packetBuffer.readItem();

            int time = packetBuffer.readInt();

            return new FabricatorRecipe(result, ingredients, id, group, time);
        }

        @Override
        public void toNetwork(PacketBuffer packetBuffer, FabricatorRecipe recipe) {
            packetBuffer.writeUtf(recipe.group);

            for (Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(packetBuffer);
            }

            packetBuffer.writeItem(recipe.result);

            packetBuffer.writeInt(recipe.time);
        }
    }
    //endregion

    private static Logger LOGGER = LogManager.getLogger();
}