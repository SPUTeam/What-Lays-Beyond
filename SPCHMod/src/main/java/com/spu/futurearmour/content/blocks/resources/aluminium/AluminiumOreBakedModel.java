package com.spu.futurearmour.content.blocks.resources.aluminium;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class AluminiumOreBakedModel implements IBakedModel{

    private static final Logger LOGGER = LogManager.getLogger();
    private static boolean loggedError = false; // prevent spamming console

    public static ModelProperty<Optional<Boolean>> SHOULD_RENDER = new ModelProperty<>();

    private IBakedModel defaultModel;

    public AluminiumOreBakedModel(IBakedModel defaultModel)
    {
        this.defaultModel = defaultModel;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        return getActualBakedModelFromIModelData(extraData).getQuads(state, side, rand);
    }

    @Nonnull
    @Override
    public IModelData getModelData(@Nonnull IBlockDisplayReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        Optional<Boolean> shouldRender = AluminiumOre.shouldRender(world, pos);
        ModelDataMap modelDataMap = getEmptyIModelData();
        modelDataMap.setData(SHOULD_RENDER, shouldRender);
        return modelDataMap;
    }

    public static ModelDataMap getEmptyIModelData() {
        ModelDataMap.Builder builder = new ModelDataMap.Builder();
        builder.withInitial(SHOULD_RENDER, Optional.empty());
        ModelDataMap modelDataMap = builder.build();
        return modelDataMap;
    }

    private IBakedModel getActualBakedModelFromIModelData(IModelData extraData) {
        IBakedModel retval = defaultModel;  // default

        if (!extraData.hasProperty(SHOULD_RENDER)) {
            if (!loggedError) {
                LOGGER.error("IModelData did not have expected property SHOULD_RENDER");
                loggedError = true;
            }
            return retval;
        }
        Optional<Boolean> shouldRender = extraData.getData(SHOULD_RENDER);
        if (!shouldRender.isPresent()) return retval;

        if(shouldRender.get().equals(true)){
            return defaultModel;
        }else{
            Minecraft mc = Minecraft.getInstance();
            BlockRendererDispatcher blockRendererDispatcher = mc.getBlockRenderer();
            retval = blockRendererDispatcher.getBlockModel(Blocks.STONE.defaultBlockState());
            return retval;
        }
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState p_200117_1_, @Nullable Direction p_200117_2_, Random p_200117_3_) {
        throw new AssertionError("IBakedModel::getQuads should never be called, only IForgeBakedModel::getQuads");
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {
        return defaultModel.getParticleTexture(data);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return defaultModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return defaultModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return defaultModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return defaultModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return defaultModel.getParticleIcon();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return defaultModel.getOverrides();
    }
}
