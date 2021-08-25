package com.spu.futurearmour.datageneration;

import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.setup.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStatesProvider extends BlockStateProvider {
    public BlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, FutureArmour.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //simpleBlock(BlockRegistry.FABRICATOR_PART_BLOCK.get());
    }
}
