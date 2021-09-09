package com.spu.futurearmour.content.client.gui.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.spu.futurearmour.FutureArmour;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AbstractSlider extends Widget {
    protected static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(FutureArmour.MOD_ID, "textures/gui/gui_widgets_common.png");

    protected final int xPos;
    protected final int yPos;

    private float sliderValue = 0.5f;

    public AbstractSlider(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height, StringTextComponent.EMPTY);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public float getSliderValue(){
        return sliderValue;
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTicks) {
        if(!visible)return;

        renderBg(matrixStack, Minecraft.getInstance(), x, y);
        renderSliderKnob(matrixStack, sliderValue);
    }

    protected void renderSliderKnob(MatrixStack matrixStack, float sliderValue){

    }

    protected static Logger LOGGER = LogManager.getLogger();
}
