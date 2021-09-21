package com.spu.futurearmour.datageneration;

import com.spu.futurearmour.setup.BlockRegistry;
import net.minecraft.data.DataGenerator;

public class LootTablesProvider extends BaseLootTablesProvider {
    public LootTablesProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addTables() {
        lootTables.put(BlockRegistry.TITANIUM_ORE.get(), createSimpleBlockTable("titanium_ore", BlockRegistry.TITANIUM_ORE.get()));
        lootTables.put(BlockRegistry.ALUMINIUM_ORE.get(), createSimpleBlockTable("aluminium_ore", BlockRegistry.ALUMINIUM_ORE.get()));
        lootTables.put(BlockRegistry.COPPER_ORE.get(), createSimpleBlockTable("copper_ore", BlockRegistry.COPPER_ORE.get()));
        lootTables.put(BlockRegistry.URANIUM_ORE.get(), createSimpleBlockTable("uranium_ore", BlockRegistry.URANIUM_ORE.get()));
        lootTables.put(BlockRegistry.TITANIUM_BLOCK.get(), createSimpleBlockTable("titanium_block", BlockRegistry.TITANIUM_BLOCK.get()));
        lootTables.put(BlockRegistry.ALUMINIUM_BLOCK.get(), createSimpleBlockTable("aluminium_block", BlockRegistry.ALUMINIUM_BLOCK.get()));
        lootTables.put(BlockRegistry.COPPER_BLOCK.get(), createSimpleBlockTable("copper_block", BlockRegistry.COPPER_BLOCK.get()));
        lootTables.put(BlockRegistry.URANIUM_BLOCK.get(), createSimpleBlockTable("uranium_block", BlockRegistry.URANIUM_BLOCK.get()));
        lootTables.put(BlockRegistry.STEEL_BLOCK.get(), createSimpleBlockTable("steel_block", BlockRegistry.STEEL_BLOCK.get()));
        lootTables.put(BlockRegistry.URANIUMSTEEL_BLOCK.get(), createSimpleBlockTable("uraniumsteel_block", BlockRegistry.URANIUMSTEEL_BLOCK.get()));
        lootTables.put(BlockRegistry.FABRICATOR_CONTROLLER_BLOCK.get(), createSimpleBlockTable("fabricator_controller_block", BlockRegistry.FABRICATOR_CONTROLLER_BLOCK.get()));
        lootTables.put(BlockRegistry.FABRICATOR_PART_BLOCK.get(), createSimpleBlockTable("fabricator_part_block", BlockRegistry.FABRICATOR_PART_BLOCK.get()));
    }
}
