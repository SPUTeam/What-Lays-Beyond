package com.spu.futurearmour.content.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;

import javax.annotation.Nonnull;
import java.util.Optional;

public class OptimisableOreBlock extends OreBlock {
    public OptimisableOreBlock(Properties p_i48357_1_) {
        super(p_i48357_1_);
    }

    @SuppressWarnings({"NullableProblems", "deprecation"})
    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    public static Optional<Boolean> shouldRender(@Nonnull IBlockDisplayReader world, @Nonnull BlockPos blockPos) {
        int adjacentBlockCount = 0;

        for (Direction facing : Direction.values()) {
            BlockPos adjacentPosition = blockPos.offset(facing.getStepX(),
                    facing.getStepY(),
                    facing.getStepZ());

            BlockState adjacentBS = world.getBlockState(adjacentPosition);
            Block adjacentBlock = adjacentBS.getBlock();

            if (!adjacentBlock.isAir(adjacentBS, world, adjacentPosition)
                    && adjacentBS.isViewBlocking(world, adjacentPosition)) {
                adjacentBlockCount++;
            }
        }

        return Optional.of(adjacentBlockCount < 6);
    }
}
