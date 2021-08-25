package com.spu.futurearmour.datageneration;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        if (event.includeServer()) {
            generator.addProvider(new RecipesProvider(generator));
            generator.addProvider(new LootTablesProvider(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new BlockStatesProvider(generator, event.getExistingFileHelper()));
            generator.addProvider(new ItemModelsProvider(generator, event.getExistingFileHelper()));
        }
    }
}