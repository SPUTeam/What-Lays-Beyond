package com.spu.futurearmour.setup;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.tileentities.FabricatorControllerTileEntity;
import com.spu.futurearmour.content.tileentities.FabricatorPartTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeRegistry {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, FutureArmour.MOD_ID);

    public static void register() {
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<TileEntityType<FabricatorControllerTileEntity>> FABRICATOR_CONTROLLER_TILE_ENTITY_TYPE =
            TILE_ENTITY_TYPES.register("fabricator_controller_tet", () ->
                    TileEntityType.Builder.of(FabricatorControllerTileEntity::new, BlockRegistry.FABRICATOR_CONTROLLER_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<FabricatorPartTileEntity>> FABRICATOR_PART_TILE_ENTITY_TYPE =
            TILE_ENTITY_TYPES.register("fabricator_part_tet", ()->
                    TileEntityType.Builder.of(FabricatorPartTileEntity::new, BlockRegistry.FABRICATOR_PART_BLOCK.get()).build(null));
}