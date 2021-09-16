package com.spu.futurearmour.datageneration;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.setup.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelsProvider extends ItemModelProvider {
    public ItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, FutureArmour.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        forTool("steel_pickaxe");
        forTool("diamond_upgraded_steel_pickaxe");

        forItem("plastic_sheet");
        forItem("nylon_roll");
        forItem("carbon_fiber");
        forItem("glass_fiber");
        forItem("rubber_piece");
        forItem("composite_plate");
        forItem("copper_ingot");
        forItem("magnesium_powder");
        forItem("saltpeter_powder");
        forItem("sulfur_powder");
        forItem("steel_ingot");
        forItem("titanium_ingot");
        forItem("uranium_ingot");
        forItem("uraniumsteel_ingot");
        forItem("aluminium_ingot");
        forItem("pilot_suit_chestplate");
        forItem("pilot_suit_leggings");
    }

    private ItemModelBuilder forTool(String name) {
        return getBuilder(getExistingFile(mcLoc("item/handheld")), name);
    }

    private ItemModelBuilder forItem(String name) {
        return getBuilder(getExistingFile(mcLoc("item/generated")), name);
    }

    private ItemModelBuilder getBuilder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
