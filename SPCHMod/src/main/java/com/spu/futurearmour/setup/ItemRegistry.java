package com.spu.futurearmour.setup;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.items.armour.PilotSuitChestplate;
import com.spu.futurearmour.content.items.armour.PilotSuiteLeggings;
import com.spu.futurearmour.content.items.resources.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FutureArmour.MOD_ID);

    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<Item> PLASTIC_SHEET =
            ITEMS.register("plastic_sheet", PlasticSheet::new);
    public static final RegistryObject<Item> NYLON_ROLL =
            ITEMS.register("nylon_roll", NylonRoll::new);
    public static final RegistryObject<Item> CARBON_FIBER_ROLL =
            ITEMS.register("carbon_fiber", CarbonFiberRoll::new);
    public static final RegistryObject<Item> GLASS_FIBER_ROLL =
            ITEMS.register("glass_fiber", GlassFiberRoll::new);
    public static final RegistryObject<Item> RUBBER_PIECE =
            ITEMS.register("rubber_piece", RubberPiece::new);
    public static final RegistryObject<Item> COMPOSITE_SHEET =
            ITEMS.register("composite_plate", CompositeSheet::new);
    public static final RegistryObject<Item> COPPER_INGOT =
            ITEMS.register("copper_ingot", CopperIngot::new);
    public static final RegistryObject<Item> MAGNESIUM_POWDER =
            ITEMS.register("magnesium_powder", MagnesiumPowder::new);
    public static final RegistryObject<Item> SALTPETER_POWDER =
            ITEMS.register("saltpeter_powder", SaltpeterPowder::new);
    public static final RegistryObject<Item> SULFUR_POWDER =
            ITEMS.register("sulfur_powder", SulfurPowder::new);
    public static final RegistryObject<Item> STEEL_INGOT =
            ITEMS.register("steel_ingot", SteelIngot::new);
    public static final RegistryObject<Item> TITANIUM_INGOT =
            ITEMS.register("titanium_ingot", TitaniumIngot::new);
    public static final RegistryObject<Item> URANIUM_INGOT =
            ITEMS.register("uranium_ingot", UraniumIngot::new);
    public static final RegistryObject<Item> URANIUMSTEEL_INGOT =
            ITEMS.register("uraniumsteel_ingot", UraniumsteelIngot::new);
    public static final RegistryObject<Item> ALUMINIUM_INGOT =
            ITEMS.register("aluminium_ingot", AluminiumIngot::new);


    public static final RegistryObject<Item> PILOT_SUIT_CHESTPLATE =
            ITEMS.register("pilot_suit_chestplate", PilotSuitChestplate::new);
    public static final RegistryObject<Item> PILOT_SUIT_LEGGINGS =
            ITEMS.register("pilot_suit_leggings", PilotSuiteLeggings::new);

    public static final RegistryObject<Item> FABRICATOR_CONTROLLER_BLOCK_ITEM =
            ITEMS.register("fabricator_controller_block", () ->
                    new BlockItem(BlockRegistry.FABRICATOR_CONTROLLER_BLOCK.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> FABRICATOR_PART_BLOCK_ITEM =
            ITEMS.register("fabricator_part_block", () ->
                    new BlockItem(BlockRegistry.FABRICATOR_PART_BLOCK.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> URANIUM_BLOCK_ITEM =
            ITEMS.register("uranium_block", () ->
                    new BlockItem(BlockRegistry.URANIUM_BLOCK.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> URANIUMSTEEL_BLOCK_ITEM =
            ITEMS.register("uraniumsteel_block", () ->
                    new BlockItem(BlockRegistry.URANIUMSTEEL_BLOCK.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> STEEL_BLOCK_ITEM =
            ITEMS.register("steel_block", () ->
                    new BlockItem(BlockRegistry.STEEL_BLOCK.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> TITANIUM_BLOCK_ITEM =
            ITEMS.register("titanium_block", () ->
                    new BlockItem(BlockRegistry.TITANIUM_BLOCK.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_BLOCK_ITEM =
            ITEMS.register("copper_block", () ->
                    new BlockItem(BlockRegistry.COPPER_BLOCK.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> ALUMINIUM_BLOCK_ITEM =
            ITEMS.register("aluminium_block", () ->
                    new BlockItem(BlockRegistry.ALUMINIUM_BLOCK.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> URANIUM_ORE_ITEM =
            ITEMS.register("uranium_ore", () ->
                    new BlockItem(BlockRegistry.URANIUM_ORE.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> SULFUR_ORE_ITEM =
            ITEMS.register("sulfur_ore", () ->
                    new BlockItem(BlockRegistry.SULFUR_ORE.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> MAGNESIUM_ORE_ITEM =
            ITEMS.register("magnesium_ore", () ->
                    new BlockItem(BlockRegistry.MAGNESIUM_ORE.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> COPPER_ORE_ITEM =
            ITEMS.register("copper_ore", () ->
                    new BlockItem(BlockRegistry.COPPER_ORE.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> ALUMINIUM_ORE_ITEM =
           ITEMS.register("aluminium_ore", () ->
                    new BlockItem(BlockRegistry.ALUMINIUM_ORE.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
    public static final RegistryObject<Item> TITANIUM_ORE_ITEM =
            ITEMS.register("titanium_ore", () ->
                    new BlockItem(BlockRegistry.TITANIUM_ORE.get(), new Item.Properties().tab(FutureArmour.ITEM_GROUP)));
}