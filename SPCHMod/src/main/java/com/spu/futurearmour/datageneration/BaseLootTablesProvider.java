package com.spu.futurearmour.datageneration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import com.spu.futurearmour.setup.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BaseLootTablesProvider extends LootTableProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    protected final DataGenerator generator;

    public BaseLootTablesProvider(DataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

    protected abstract void addTables();

    protected LootTable.Builder createSimpleBlockTable(String name, Block block) {
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(ConstantRange.exactly(1))
                .add(ItemLootEntry.lootTableItem(block));
        return LootTable.lootTable().withPool(builder);
    }

    protected LootTable.Builder createOneTypeMultipleTable(String name, Item item, int min, int max, int maxStackSize){
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(new RandomValueRange(min, max))
                .add(
                        ItemLootEntry.lootTableItem(item)
                        .setWeight(1)
                        .apply(SetCount.setCount(new RandomValueRange(1, maxStackSize)))
                );
        return LootTable.lootTable().withPool(builder);
    }

    @Override
    public void run(DirectoryCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey().getLootTable(), entry.getValue().setParamSet(LootParameterSets.BLOCK).build());
        }
        writeTables(cache, tables);
    }

    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();

        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.serialize(lootTable), path);
            } catch (IOException exception) {
                LOGGER.error("Couldn't write loot table {}", path, exception);
            }
        });
    }
}
