package com.spu.futurearmour.content.tileentities.fabricator;

import com.spu.futurearmour.setup.TileEntityTypeRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FabricatorPartTileEntity extends TileEntity {
    private static Logger LOGGER = LogManager.getLogger();

    public boolean assembled;
    public BlockPos controllerPos;
    private FabricatorControllerTileEntity controllerEntity;

    public FabricatorPartTileEntity() {
        super(TileEntityTypeRegistry.FABRICATOR_PART_TILE_ENTITY_TYPE.get());
    }

    public void registerToController(BlockPos controllerPos, FabricatorControllerTileEntity controllerEntity) {
        this.controllerPos = controllerPos;
        this.controllerEntity = controllerEntity;
        setAssemblyState(true);
    }

    public void detachFromController() {
        controllerEntity = null;
        controllerPos = null;
        setAssemblyState(false);
    }

    public void notifyControllerRemoval() {
        if (controllerEntity == null) return;
        controllerEntity.partRemoved(this.getBlockPos());
        detachFromController();
    }

    public void playerInteract(PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity) || !assembled || controllerEntity == null) return;

        controllerEntity.playerInteract(player);
    }

    private void setAssemblyState(boolean toAssembled) {
        assembled = toAssembled;
    }

}