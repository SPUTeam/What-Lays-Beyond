package com.spu.futurearmour.content.client.gui.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

public class VerticalSlider extends AbstractSlider{
    public VerticalSlider(int xPos, int yPos) {
        super(xPos, yPos, 5, 62);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderSliderKnob(MatrixStack matrixStack, float sliderValue) {
        RenderSystem.color4f(1, 1, 1, 1);
        Minecraft.getInstance().getTextureManager().bind(TEXTURE_LOCATION);

        float offset = (float) height;
        offset *= getSliderValue();
        int yPos = this.yPos + (int) offset;

        blit(matrixStack, xPos - 1, yPos - 6, 4, 10, 5, 13);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrixStack, Minecraft minecraft, int x, int y) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(TEXTURE_LOCATION);
        blit(matrixStack, xPos, yPos, 0, 10, 3, 62);
    }

    @Override
    protected void updateSliderValue(int mouseX, int mouseY) {
        int offset = yPos - mouseY;
        float ratio = (float) offset / (float) height;
        setSliderValue(ratio * -1f);
    }
}
