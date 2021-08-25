package com.spu.futurearmour.content.blocks.magnesium;

import com.spu.futurearmour.content.blocks.OptimisableOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Optional;
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
