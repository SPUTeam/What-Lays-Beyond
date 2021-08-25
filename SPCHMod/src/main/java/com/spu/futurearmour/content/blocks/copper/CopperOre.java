package com.spu.futurearmour.content.blocks.copper;

import com.spu.futurearmour.content.blocks.OptimisableOreBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class CopperOre extends OptimisableOreBlock {
    public CopperOre() {
        super(Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(2)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .requiresCorrectToolForDrops());
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getShadeBrightness(BlockState blockState, IBlockReader blockReader, BlockPos blockPos) {
        return 1F;
    }
}
