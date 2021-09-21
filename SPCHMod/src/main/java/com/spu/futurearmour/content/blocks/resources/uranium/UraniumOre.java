package com.spu.futurearmour.content.blocks.resources.uranium;

import com.spu.futurearmour.content.blocks.resources.OptimisableOreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class UraniumOre extends OptimisableOreBlock {
    public UraniumOre() {
        super(Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(2)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2)
                .requiresCorrectToolForDrops());
    }

    @SuppressWarnings("deprecation")
    @Override
    public float getShadeBrightness(BlockState blockState, IBlockReader blockReader, BlockPos blockPos) {
        return 1F;
    }
}
