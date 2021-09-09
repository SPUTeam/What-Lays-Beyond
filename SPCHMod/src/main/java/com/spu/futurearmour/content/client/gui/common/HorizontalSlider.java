package com.spu.futurearmour.content.client.gui.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

public class HorizontalSlider extends AbstractSlider{
    public HorizontalSlider(int xPos, int yPos) {
        super(xPos, yPos, 62, 5);
    }

    @Override
    protected void onDrag(double p_230983_1_, double p_230983_3_, double p_230983_5_, double p_230983_7_) {
        LOGGER.debug(p_230983_1_ + " | " + p_230983_3_);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderSliderKnob(MatrixStack matrixStack, float sliderValue) {
        RenderSystem.color4f(1, 1, 1, 1);
        Minecraft.getInstance().getTextureManager().bind(TEXTURE_LOCATION);

        float offset = (float) width;
        offset *= getSliderValue();
        int xPos = this.xPos + (int) offset;

        blit(matrixStack, xPos - 6, yPos - 1, 0, 4, 13, 5);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrixStack, Minecraft minecraft, int x, int y) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(TEXTURE_LOCATION);
        blit(matrixStack, xPos, yPos, 0, 0, 62, 3);
    }

    @Override
    public void onClick(double p_230982_1_, double p_230982_3_) {
        LOGGER.debug("KEK");
    }

    @Override
    public void onRelease(double p_231000_1_, double p_231000_3_) {
        LOGGER.debug("LOL");
    }
}
