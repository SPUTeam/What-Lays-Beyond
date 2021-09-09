package com.spu.futurearmour.content.client.gui.fabricator;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.util.ResourceLocation;

public class FabricatorPanelChangeButton extends ImageButton {
    private final ResourceLocation textureLocation;
    private final int xTexStart;
    private final int yTexStart;

    private int texYOffset;

    public FabricatorPanelChangeButton(int posX, int posY, int texSizeX, int texSizeY, int texStartX, int texStartY, ResourceLocation textureLocation, IPressable onPressedLambda) {
        super(posX, posY, texSizeX, texSizeY, texStartX, texStartY, 0, textureLocation, onPressedLambda);
        this.textureLocation = textureLocation;
        this.xTexStart = texStartX;
        this.yTexStart = texStartY;
        texYOffset = 0;
    }

    public void setNextTexYOffset(int nextOffset) {
        texYOffset = nextOffset;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bind(this.textureLocation);
        int yTexStart = this.yTexStart;
        yTexStart += texYOffset;

        RenderSystem.enableDepthTest();
        blit(matrixStack, this.x, this.y, (float)this.xTexStart - 0.5f, (float)yTexStart, this.width, this.height, 256, 256);
        if (this.isHovered()) {
            this.renderToolTip(matrixStack, p_230431_2_, p_230431_3_);
        }

    }
}
