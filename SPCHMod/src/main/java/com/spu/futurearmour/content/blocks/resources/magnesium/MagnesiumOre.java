package com.spu.futurearmour.content.blocks.resources.magnesium;

import com.spu.futurearmour.content.blocks.resources.OptimisableOreBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class MagnesiumOre extends OptimisableOreBlock {
    public MagnesiumOre() {
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

    @Override
    protected int xpOnDrop(Random random) {
        return MathHelper.nextInt(random, 2, 5);
    }
}
