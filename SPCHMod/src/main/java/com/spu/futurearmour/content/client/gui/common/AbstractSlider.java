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

    protected boolean isDragging = false;

    private boolean wasHovered = false;
    private float sliderValue = 0.5f;

    public AbstractSlider(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height, StringTextComponent.EMPTY);
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public float getSliderValue() {
        return sliderValue;
    }

    public void setSliderValue(float value) {
        sliderValue = value > 0 ? Math.min(value, 1) : 0;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (!visible) return;
        this.isHovered = isHovered(mouseX, mouseY, isDragging);
        if (isDragging){
            if(wasHovered != isHovered){
                isDragging = false;
            }else{
                updateSliderValue(mouseX, mouseY);
            }
        }

        renderBg(matrixStack, Minecraft.getInstance(), x, y);
        renderSliderKnob(matrixStack, sliderValue);
        this.wasHovered = this.isHovered();
    }

    private boolean isHovered(int x, int y, boolean withMargin) {
        int margin = withMargin ? 10 : 0;
        return x >= this.x - margin && y >= this.y - margin &&
                x < this.x + this.width + margin && y < this.y + this.height + margin;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int mouseButton) {
        if(mouseButton == 0) {
            isDragging = false;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(double x, double y) {
        isDragging = true;
    }

    protected void updateSliderValue(int mouseX, int mouseY){

    }

    protected void renderSliderKnob(MatrixStack matrixStack, float sliderValue) {
    }

    protected static Logger LOGGER = LogManager.getLogger();
}
