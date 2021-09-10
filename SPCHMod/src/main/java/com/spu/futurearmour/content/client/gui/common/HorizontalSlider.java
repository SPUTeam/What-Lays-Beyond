package com.spu.futurearmour.content.client.gui.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

public class HorizontalSlider extends AbstractSlider {
    public HorizontalSlider(int xPos, int yPos) {
        super(xPos, yPos, 62, 5);
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
    public void onClick(double x, double y) {
        super.onClick(x, y);
        if (!isHovered) return;

        updateSliderValue((int) x, (int) y);
    }

    @Override
    protected void updateSliderValue(int mouseX, int mouseY) {
        int offset = xPos - mouseX;
        float ratio = (float) offset / (float) width;
        setSliderValue(ratio * -1f);
    }
}
