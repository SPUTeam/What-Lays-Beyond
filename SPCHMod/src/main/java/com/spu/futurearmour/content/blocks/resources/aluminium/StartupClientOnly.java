package com.spu.futurearmour.content.blocks.resources.aluminium;


import com.spu.futurearmour.setup.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StartupClientOnly {
    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event)
    {
        for (BlockState blockState : BlockRegistry.ALUMINIUM_ORE.get().getStateDefinition().getPossibleStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.stateToModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                LOGGER.debug("Did not find the expected vanilla baked model(s) in registry");
            } else if (existingModel instanceof AluminiumOreBakedModel) {
                LOGGER.warn("Tried to replace twice");
            } else {
                AluminiumOreBakedModel customModel = new AluminiumOreBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }

    }

    /**
     *
     * @param event
     */
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(BlockRegistry.ALUMINIUM_ORE.get().getBlock(), RenderType.solid());
    }

    private static final Logger LOGGER = LogManager.getLogger();

}