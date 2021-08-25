package com.spu.futurearmour.content.blocks.fabricator;

import com.spu.futurearmour.content.tileentities.FabricatorControllerTileEntity;
import com.spu.futurearmour.content.tileentities.FabricatorPartTileEntity;
import com.spu.futurearmour.setup.ModBlockStateProperties;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FabricatorPart extends Block {
    public static final BooleanProperty MULTIBLOCK_ASSEMBLED = ModBlockStateProperties.MULTIBLOCK_ASSEMBLED;

    public FabricatorPart() {
        super(AbstractBlock.Properties.of(Material.HEAVY_METAL)
                .strength(5)
                .sound(SoundType.METAL)
                .noOcclusion());

        this.registerDefaultState(this.stateDefinition.any().setValue(MULTIBLOCK_ASSEMBLED, true));
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getShadeBrightness(BlockState blockState, IBlockReader blockReader, BlockPos blockPos) {
        return 1F;
    }

    @SuppressWarnings({"NullableProblems", "deprecation"})
    public BlockRenderType getRenderShape(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    public BlockState getStateForPlacement(BlockItemUseContext itemUseContext) {
        return this.defaultBlockState().setValue(MULTIBLOCK_ASSEMBLED, false);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(MULTIBLOCK_ASSEMBLED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState stateFrom, World world, BlockPos pos, BlockState stateTo, boolean moving) {
        if(world.isClientSide())return;
        FabricatorPartTileEntity entity = getConnectedEntity(world, pos);
        if (entity != null && (stateFrom.getBlock() != stateTo.getBlock())) {
            entity.notifyControllerRemoval();
        }
        super.onRemove(stateFrom, world, pos, stateTo, moving);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FabricatorPartTileEntity();
    }

    private FabricatorPartTileEntity getConnectedEntity(World world, BlockPos pos) {
        TileEntity entity = world.getBlockEntity(pos);
        if (entity instanceof FabricatorPartTileEntity) {
            return (FabricatorPartTileEntity) entity;
        }
        return null;
    }
}