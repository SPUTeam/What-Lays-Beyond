package com.spu.futurearmour.datageneration;

import com.spu.futurearmour.FutureArmour;
import net.minecraft.data.DataGenerator;
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
//        withExistingParent("fabricator_part_block", modLoc("block/fabricator_part_block"));
//        withExistingParent("fabricator_controller_block", modLoc("block/fabricator_controller_block"));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        getBuilder(itemGenerated, "plastic_sheet");
        getBuilder(itemGenerated, "nylon_roll");
        getBuilder(itemGenerated, "carbon_fiber");
        getBuilder(itemGenerated, "glass_fiber");
        getBuilder(itemGenerated, "rubber_piece");
        getBuilder(itemGenerated, "composite_plate");
        getBuilder(itemGenerated, "copper_ingot");
        getBuilder(itemGenerated, "magnesium_powder");
        getBuilder(itemGenerated, "saltpeter_powder");
        getBuilder(itemGenerated, "sulfur_powder");
        getBuilder(itemGenerated, "steel_ingot");
        getBuilder(itemGenerated, "titanium_ingot");
        getBuilder(itemGenerated, "uranium_ingot");
        getBuilder(itemGenerated, "uraniumsteel_ingot");
        getBuilder(itemGenerated, "aluminium_ingot");
        getBuilder(itemGenerated, "pilot_suit_chestplate");
        getBuilder(itemGenerated, "pilot_suit_leggings");
    }

    private ItemModelBuilder getBuilder(ModelFile itemGenerated, String name) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }
}
