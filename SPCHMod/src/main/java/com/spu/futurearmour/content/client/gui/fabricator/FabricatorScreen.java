package com.spu.futurearmour.content.client.gui.fabricator;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.client.gui.common.AbstractSlider;
import com.spu.futurearmour.content.client.gui.common.HorizontalSlider;
import com.spu.futurearmour.content.client.gui.common.VerticalSlider;
import com.spu.futurearmour.content.containers.FabricatorControllerContainer;
import com.spu.futurearmour.content.network.Networking;
import com.spu.futurearmour.content.network.messages.fabricator.CTSMessageToggleFabricatorCrafting;
import com.spu.futurearmour.setup.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.SeparatePerspectiveModel;
import net.minecraftforge.common.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

@SuppressWarnings("deprecation")
@OnlyIn(Dist.CLIENT)
public class FabricatorScreen extends ContainerScreen<FabricatorControllerContainer> {
    public static final ResourceLocation BG_TEXTURE = new ResourceLocation(FutureArmour.MOD_ID, "textures/gui/fabricator_bg.png");
    public static final ResourceLocation PARTS_ONE_TEXTURE = new ResourceLocation(FutureArmour.MOD_ID, "textures/gui/fabricator_menu_parts_one.png");
    public static final ResourceLocation PARTS_TWO_TEXTURE = new ResourceLocation(FutureArmour.MOD_ID, "textures/gui/fabricator_menu_parts_two.png");

    private FabricatorCraftButton craftButton;
    private FabricatorPanelChangeButton panelChangeButton;
    private AbstractSlider previewModelSlider;
    private AbstractSlider recipesSlider;

    private FabricatorGuiPanel currentPanel = FabricatorGuiPanel.PREVIEW;

    public FabricatorScreen(FabricatorControllerContainer container, PlayerInventory playerInventory, ITextComponent defaultName) {
        super(container, playerInventory, new StringTextComponent(""));
    }

    @Override
    protected void init() {
        super.init();
        craftButton = addCraftButton();
        panelChangeButton = addPanelChangeButton();
        previewModelSlider = addPreviewModelSlider();
        recipesSlider = addRecipesSlider();
        changeLeftPanel();
    }

    @Override
    public void tick() {
        super.tick();

    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTicks) {
        this.renderBackground(matrixStack);
        if(currentPanel == FabricatorGuiPanel.PREVIEW)this.renderItemStackWithRotation(new ItemStack(ItemRegistry.PILOT_SUIT_CHESTPLATE.get().getItem()), previewModelSlider.getSliderValue());
        if(currentPanel == FabricatorGuiPanel.RECIPES)this.renderRecipes(recipesSlider.getSliderValue());
        super.render(matrixStack, x, y, partialTicks);
        for(int i = 0; i < children().size(); i++){
            if(!(children.get(i) instanceof Button)){
                Widget widget = (Widget) children.get(i);
                if(widget.visible)widget.render(matrixStack, x, y, partialTicks);
            }
        }
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

    //region Left Panels
    private void renderRecipes(float sliderValue){
        int ySpaceLeft = 50;

    }

    private void renderRecipeItem(int yPos, int yToRender, boolean renderFromTop){

    }

    private AbstractSlider addRecipesSlider(){
        Vector3i centerPos = centerPosForSize(3, 62);
        int posX = (centerPos.getX() + 20);
        int posY = (centerPos.getY() - 38);

        return this.addWidget(new VerticalSlider(posX, posY));
    }

    private void renderItemStackWithRotation(ItemStack stack, float rotation01){
        IBakedModel bakedModel = itemRenderer.getModel(stack, null, null);
        RenderSystem.pushMatrix();

        Vector3i centerPos = centerPosForSize(150, 150);
        int x = (centerPos.getX() + 17);
        int y = (centerPos.getY() + 58);

        Minecraft.getInstance().getTextureManager().bind(AtlasTexture.LOCATION_BLOCKS);
        Minecraft.getInstance().getTextureManager().getTexture(AtlasTexture.LOCATION_BLOCKS).setFilter(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.translatef((float)x, (float)y, 150.0F);
        RenderSystem.scalef(1.0F, -1.0F, 1.0F);
        RenderSystem.scalef(192.0F, 192.0F, 1.0F);
        RenderSystem.rotatef((250 * rotation01) - 25, 0, 1, 0);

        MatrixStack matrixstack = new MatrixStack();
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance().renderBuffers().bufferSource();
        boolean doesntUseBlockLight = !bakedModel.usesBlockLight();
        if (doesntUseBlockLight) {
            RenderHelper.setupForFlatItems();
        }

        itemRenderer.render(stack, ItemCameraTransforms.TransformType.GROUND, false, matrixstack, irendertypebuffer$impl, 15728880, OverlayTexture.NO_OVERLAY, bakedModel);
        irendertypebuffer$impl.endBatch();
        RenderSystem.enableDepthTest();
        if (doesntUseBlockLight) {
            RenderHelper.setupFor3DItems();
        }

        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.popMatrix();
    }

    private AbstractSlider addPreviewModelSlider(){
        Vector3i centerPos = centerPosForSize(62, 3);
        int posX = (centerPos.getX() - 98);
        int posY = (centerPos.getY() + 7);

        return this.addWidget(new HorizontalSlider(posX, posY));
    }

    private FabricatorPanelChangeButton addPanelChangeButton() {
        Vector3i centerPos = centerPosForSize(67, 15);
        int posX = centerPos.getX() - 113;
        int posY = centerPos.getY() - 100;
        return addButton(new FabricatorPanelChangeButton(
                posX, posY,
                65, 15,
                0, 0,
                PARTS_TWO_TEXTURE,
                (button) -> {
                    changeLeftPanel();
                }
        ));
    }

    private void changeLeftPanel() {
        switch (currentPanel) {
            case PREVIEW:
                currentPanel = FabricatorGuiPanel.RECIPES;
                panelChangeButton.setNextTexYOffset(FabricatorGuiPanel.RECIPES.buttonTexYOffset);
                previewModelSlider.visible = false;
                recipesSlider.visible = true;
                break;
            case RECIPES:
                currentPanel = FabricatorGuiPanel.PREVIEW;
                panelChangeButton.setNextTexYOffset(FabricatorGuiPanel.PREVIEW.buttonTexYOffset);
                previewModelSlider.visible = true;
                recipesSlider.visible = false;
                break;
        }
    }
    //endregion

    //region Crafting UI
    private FabricatorCraftButton addCraftButton() {
        Vector3i centerPos = centerPosForSize(44, 21);
        int posX = centerPos.getX() + 102;
        int posY = centerPos.getY() + 8;
        int xOffset = menu.getCraftingIsOn() ? 45 : 0;
        return addButton(new FabricatorCraftButton(
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

    private void updateCraftingButton() {
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
    //endregion

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

    private enum FabricatorGuiPanel {
        PREVIEW(0),
        RECIPES(16);

        public final int buttonTexYOffset;

        FabricatorGuiPanel(int buttonTexYOffset) {
            this.buttonTexYOffset = buttonTexYOffset;
        }
    }

    private static Logger LOGGER = LogManager.getLogger();
}