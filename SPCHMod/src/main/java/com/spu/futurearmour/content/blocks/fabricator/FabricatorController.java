package com.spu.futurearmour.content.blocks.fabricator;

import com.spu.futurearmour.content.tileentities.FabricatorControllerTileEntity;
import com.spu.futurearmour.content.tileentities.FabricatorPartTileEntity;
import com.spu.futurearmour.setup.ModBlockStateProperties;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class FabricatorController extends Block {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty MULTIBLOCK_ASSEMBLED = ModBlockStateProperties.MULTIBLOCK_ASSEMBLED;

    public FabricatorController() {
        super(AbstractBlock.Properties.of(Material.HEAVY_METAL)
        .strength(5)
        .sound(SoundType.METAL)
        .noOcclusion());

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(MULTIBLOCK_ASSEMBLED, false));
    }

    //region BlockState and rendering
    @SuppressWarnings("deprecation")
    @Override
    public float getShadeBrightness(BlockState blockState, IBlockReader blockReader, BlockPos blockPos) {
        return 1F;
    }

    @SuppressWarnings("deprecation")
    public BlockRenderType getRenderShape(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    public BlockState getStateForPlacement(BlockItemUseContext itemUseContext) {
        return this.defaultBlockState()
                .setValue(FACING, itemUseContext.getHorizontalDirection().getOpposite())
                .setValue(MULTIBLOCK_ASSEMBLED, false);
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, MULTIBLOCK_ASSEMBLED);
    }
    //endregion

    //region Interaction
    @SuppressWarnings({"NullableProblems", "deprecation"})
    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(world.isClientSide()){
            return getConnectedEntity(world, pos).isAssembled()? ActionResultType.SUCCESS : ActionResultType.PASS;
        }
        getConnectedEntity(world, pos).playerInteract(player);
        return ActionResultType.CONSUME;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onRemove(BlockState stateFrom, World world, BlockPos pos, BlockState stateTo, boolean moving) {
        if(world.isClientSide())return;
        FabricatorControllerTileEntity entity = getConnectedEntity(world, pos);
        if (entity != null && (stateFrom.getBlock() != stateTo.getBlock())) {
            entity.onBlockRemoved();
        }
        super.onRemove(stateFrom, world, pos, stateTo, moving);
    }
    //endregion

    //region TileEntity logic
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new FabricatorControllerTileEntity();
    }

    private FabricatorControllerTileEntity getConnectedEntity(World world, BlockPos pos) {
        TileEntity entity = world.getBlockEntity(pos);
        if (entity instanceof FabricatorControllerTileEntity) {
            return (FabricatorControllerTileEntity) entity;
        }
        return null;
    }
    //endregion
}