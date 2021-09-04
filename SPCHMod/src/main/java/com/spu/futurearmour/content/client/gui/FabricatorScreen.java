package com.spu.futurearmour.content.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.containers.FabricatorControllerContainer;
import com.spu.futurearmour.content.network.Networking;
import com.spu.futurearmour.content.network.messages.fabricator.CTSMessageToggleFabricatorCrafting;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class FabricatorScreen extends ContainerScreen<FabricatorControllerContainer> {
    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(FutureArmour.MOD_ID, "textures/gui/fabricator_bg.png");
    public static final ResourceLocation PARTS_ONE_TEXTURE = new ResourceLocation(FutureArmour.MOD_ID, "textures/gui/fabricator_menu_parts_one.png");

    public FabricatorScreen(FabricatorControllerContainer container, PlayerInventory playerInventory, ITextComponent defaultName) {
        super(container, playerInventory, new StringTextComponent(""));
    }

    @Override
    protected void init() {
        super.init();
        addCraftButton();
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, x, y, partialTicks);
        this.renderTooltip(matrixStack, x, y);
        this.updateCraftingButton();
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        if (minecraft == null) return;

        this.inventoryLabelY = -300;
        renderScreenBackground(matrixStack, BG_TEXTURE);
        renderPlayerInventory(matrixStack, PARTS_ONE_TEXTURE);
        renderCraftingSlots(matrixStack, PARTS_ONE_TEXTURE);
        renderResultSlots(matrixStack, PARTS_ONE_TEXTURE);
        renderProgressArrowBG(matrixStack, PARTS_ONE_TEXTURE);
        renderProgressArrowOverlay(matrixStack, PARTS_ONE_TEXTURE, menu.getProgressArrowScale());
    }

    private void addCraftButton() {
        Vector3i centerPos = centerPosForSize(44, 21);
        int posX = centerPos.getX() + 102;
        int posY = centerPos.getY() + 8;
        int xOffset = menu.getCraftingIsOn() ? 45 : 0;
        addButton(new FabricatorCraftButton(
                posX, posY,
                44, 21,
                xOffset, 175, 24,
                PARTS_ONE_TEXTURE,
                (button) -> {
                    FabricatorCraftButton craftButton = (FabricatorCraftButton) button;
                    Networking.simpleChannel.sendToServer(new CTSMessageToggleFabricatorCrafting(menu.getFabricatorPosition(), !craftButton.getCraftingIsOn()));
                }
        ));
    }

    private void updateCraftingButton(){
        FabricatorCraftButton craftButton = (FabricatorCraftButton) this.buttons.get(0);
        craftButton.updateCraftingState(menu.getCraftingIsOn());
    }

    private void renderProgressArrowOverlay(MatrixStack matrixStack, ResourceLocation texture, int heightToRender) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(texture);
        this.imageWidth = 12;
        this.imageHeight = 29;

        Vector3i centerPos = centerPosForSize(12, 29);
        int renderOffset = 29 - heightToRender;
        int posX = centerPos.getX() + 70;
        int posY = centerPos.getY() + 8 + renderOffset;

        blit(matrixStack, posX, posY, 131, 127 + renderOffset, 12, heightToRender);
    }

    private void renderProgressArrowBG(MatrixStack matrixStack, ResourceLocation texture) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(texture);
        this.imageWidth = 12;
        this.imageHeight = 29;

        Vector3i centerPos = centerPosForSize(12, 29);
        int posX = centerPos.getX() + 70;
        int posY = centerPos.getY() + 8;

        blit(matrixStack, posX, posY, 107, 127, 12, 29);
    }

    private void renderResultSlots(MatrixStack matrixStack, ResourceLocation texture) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(texture);
        this.imageWidth = 83;
        this.imageHeight = 78;

        Vector3i centerPos = centerPosForSize(83, 78);
        int posX = centerPos.getX() + 95;
        int posY = centerPos.getY() - 52;

        blit(matrixStack, posX, posY, 175, 78, 79, 78);
    }

    private void renderCraftingSlots(MatrixStack matrixStack, ResourceLocation texture) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(texture);
        this.imageWidth = 89;
        this.imageHeight = 79;

        Vector3i centerPos = centerPosForSize(85, 85);
        int posX = centerPos.getX() + 93;
        int posY = centerPos.getY() + 72;

        blit(matrixStack, posX, posY, 0, 81, 85, 75);
    }

    private void renderPlayerInventory(MatrixStack matrixStack, ResourceLocation texture) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(texture);
        this.imageWidth = 178;
        this.imageHeight = 78;

        Vector3i centerPos = centerPosForSize(178, 78);
        int posX = (centerPos.getX() - 51);
        int posY = (centerPos.getY() + 64);

        blit(matrixStack, posX, posY, 0, 0, 178, 78);
    }

    private void renderScreenBackground(MatrixStack matrixStack, ResourceLocation texture) {
        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(texture);
        this.imageWidth = 329;
        this.imageHeight = 252;

        Vector3i centerPos = centerPosForSize(329, 252);

        int leftPosX = centerPos.getX();
        blit(matrixStack, leftPosX, centerPos.getY(), 0, 0, 206, this.imageHeight);

        int filledWidth = 206;
        for (int i = 0; i < 6; i++) {
            int iterationWidth = Math.min(16, 295 - filledWidth);
            blit(matrixStack, leftPosX + filledWidth, centerPos.getY(), 206, 0, iterationWidth, this.imageHeight);
            filledWidth += iterationWidth;
        }

        int rightPosX = centerPos.getX() + 295;
        blit(matrixStack, rightPosX, centerPos.getY(), 222, 0, 34, this.imageHeight);
    }

    private Vector3i centerPosForSize(int xSize, int ySize) {
        int xPos = (this.width - xSize) / 2;
        int yPos = (this.height - ySize) / 2;

        return new Vector3i(xPos, yPos, 0);
    }

    private static Logger LOGGER = LogManager.getLogger();
}
