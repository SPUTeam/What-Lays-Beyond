package com.spu.futurearmour.setup;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.blocks.resources.aluminium.AluminiumBlock;
import com.spu.futurearmour.content.blocks.resources.aluminium.AluminiumOre;
import com.spu.futurearmour.content.blocks.resources.copper.CopperBlock;
import com.spu.futurearmour.content.blocks.resources.copper.CopperOre;
import com.spu.futurearmour.content.blocks.fabricator.FabricatorController;
import com.spu.futurearmour.content.blocks.fabricator.FabricatorPart;
import com.spu.futurearmour.content.blocks.resources.magnesium.MagnesiumOre;
import com.spu.futurearmour.content.blocks.resources.SteelBlock;
import com.spu.futurearmour.content.blocks.resources.UraniumsteelBlock;
import com.spu.futurearmour.content.blocks.resources.sulfur.SulfurOre;
import com.spu.futurearmour.content.blocks.resources.titanium.TitaniumBlock;
import com.spu.futurearmour.content.blocks.resources.titanium.TitaniumOre;
import com.spu.futurearmour.content.blocks.resources.uranium.UraniumBlock;
import com.spu.futurearmour.content.blocks.resources.uranium.UraniumOre;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FutureArmour.MOD_ID);

    public static void register(){
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<TitaniumOre> TITANIUM_ORE =
            BLOCKS.register("titanium_ore", TitaniumOre::new);
    public static final RegistryObject<AluminiumOre> ALUMINIUM_ORE =
            BLOCKS.register("aluminium_ore", AluminiumOre::new);
    public static final RegistryObject<CopperOre> COPPER_ORE =
            BLOCKS.register("copper_ore", CopperOre::new);
    public static final RegistryObject<MagnesiumOre> MAGNESIUM_ORE =
            BLOCKS.register("magnesium_ore", MagnesiumOre::new);
    public static final RegistryObject<SulfurOre> SULFUR_ORE =
            BLOCKS.register("sulfur_ore", SulfurOre::new);
    public static final RegistryObject<UraniumOre> URANIUM_ORE =
            BLOCKS.register("uranium_ore", UraniumOre::new);
    public static final RegistryObject<AluminiumBlock> ALUMINIUM_BLOCK =
            BLOCKS.register("aluminium_block", AluminiumBlock::new);
    public static final RegistryObject<CopperBlock> COPPER_BLOCK =
            BLOCKS.register("copper_block", CopperBlock::new);
    public static final RegistryObject<TitaniumBlock> TITANIUM_BLOCK =
            BLOCKS.register("titanium_block", TitaniumBlock::new);
    public static final RegistryObject<SteelBlock> STEEL_BLOCK =
            BLOCKS.register("steel_block", SteelBlock::new);
    public static final RegistryObject<UraniumsteelBlock> URANIUMSTEEL_BLOCK =
            BLOCKS.register("uraniumsteel_block", UraniumsteelBlock::new);
    public static final RegistryObject<UraniumBlock> URANIUM_BLOCK =
            BLOCKS.register("uranium_block", UraniumBlock::new);
    public static final RegistryObject<FabricatorPart> FABRICATOR_PART_BLOCK =
            BLOCKS.register("fabricator_part_block", () -> new FabricatorPart());
    public static final RegistryObject<FabricatorController> FABRICATOR_CONTROLLER_BLOCK =
            BLOCKS.register("fabricator_controller_block", () -> new FabricatorController());
}