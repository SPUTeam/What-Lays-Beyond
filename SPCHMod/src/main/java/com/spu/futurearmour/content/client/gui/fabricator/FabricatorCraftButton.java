package com.spu.futurearmour.content.client.gui.fabricator;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class FabricatorCraftButton extends ImageButton {

    private final ResourceLocation textureLocation;
    private final int xTexStart;
    private final int yTexStart;
    private final int yDiff;


    private boolean craftingIsOn = false;

    public FabricatorCraftButton(int p_i51134_1_, int p_i51134_2_, int p_i51134_3_, int p_i51134_4_, int xTexStart, int yTexStart, int yDiff, ResourceLocation textureLocation, IPressable onPress) {
        super(p_i51134_1_, p_i51134_2_, p_i51134_3_, p_i51134_4_, xTexStart, yTexStart, yDiff, textureLocation, onPress);

        this.textureLocation = textureLocation;
        this.xTexStart = xTexStart;
        this.yTexStart = yTexStart;
        this.yDiff = yDiff;
    }

    public void updateCraftingState(boolean nextState){
        this.craftingIsOn = nextState;
    }

    public boolean getCraftingIsOn() {
        return this.craftingIsOn;
    }

    @Override
    public void renderButton(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bind(this.textureLocation);
        int yTex = this.yTexStart;
        if (this.isHovered()) {
            yTex += this.yDiff;
        }

        int xTex = this.xTexStart;
        if(this.craftingIsOn){
            xTex += 45;
        }

        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        blit(p_230431_1_, this.x, this.y, (float)xTex, (float)yTex, this.width, this.height, 256, 256);
        if (this.isHovered()) {
            this.renderToolTip(p_230431_1_, p_230431_2_, p_230431_3_);
        }
        RenderSystem.disableBlend();
    }
}
