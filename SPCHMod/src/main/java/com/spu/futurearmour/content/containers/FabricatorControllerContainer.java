package com.spu.futurearmour.content.containers;

import com.spu.futurearmour.content.blocks.fabricator.FabricatorStateData;
import com.spu.futurearmour.content.recipes.fabricator.FabricatorRecipe;
import com.spu.futurearmour.content.tileentities.TileEntityZoneInventory;
import com.spu.futurearmour.setup.ContainerTypeRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FabricatorControllerContainer extends Container {
    private TileEntityZoneInventory inputZoneInventory;
    private TileEntityZoneInventory outputZoneInventory;
    private FabricatorStateData fabricatorStateData;
    private World world;

    public static FabricatorControllerContainer createForClient(int id, PlayerInventory playerInventory, PacketBuffer extraDataBuffer) {
        TileEntityZoneInventory inputZoneInventory = TileEntityZoneInventory.createForClientContainer(12);
        TileEntityZoneInventory outputZoneInventory = TileEntityZoneInventory.createForClientContainer(3);
        FabricatorStateData fabricatorStateData = new FabricatorStateData();

        return new FabricatorControllerContainer(id, playerInventory, inputZoneInventory, outputZoneInventory, fabricatorStateData);
    }

    public static FabricatorControllerContainer createForServer(int id, PlayerInventory playerInventory, TileEntityZoneInventory inputZoneInventory, TileEntityZoneInventory outputZoneInventory, FabricatorStateData fabricatorStateData) {
        return new FabricatorControllerContainer(id, playerInventory, inputZoneInventory, outputZoneInventory, fabricatorStateData);
    }

    public FabricatorControllerContainer(int id, PlayerInventory playerInventory, TileEntityZoneInventory inputZoneInventory, TileEntityZoneInventory outputZoneInventory, FabricatorStateData fabricatorStateData) {
        super(ContainerTypeRegistry.FABRICATOR_CONTROLLER_CONTAINER_TYPE.get(), id);
        this.inputZoneInventory = inputZoneInventory;
        this.outputZoneInventory = outputZoneInventory;
        this.fabricatorStateData = fabricatorStateData;
        this.world = playerInventory.player.level;

        addDataSlots(fabricatorStateData);

        //region slots
        //crafting slots
        Vector3i craftingSlotsOffset = new Vector3i(143, 115, 0);
        int craftNetWidth = 89;
        int spaceX = 6;
        int spaceY = 0;
        int currentCraftSlot = 0;
        for (int row = 0; row < 4; row++) {
            int slotsInRow = FabricatorRecipe.getRowWidth(row);
            int rowYPos = craftingSlotsOffset.getY() + ((18 + spaceY) * row);
            int rowWidth = (16 * slotsInRow) + (spaceX * Math.max(0, slotsInRow - 1));
            int rowXPos = craftingSlotsOffset.getX() + ((craftNetWidth - rowWidth) / 2);

            for (int slot = 0; slot < slotsInRow; slot++) {
                int slotXPos = rowXPos + (16 * slot) + ((slot - 1) * spaceX);
                this.addSlot(new Slot(inputZoneInventory, currentCraftSlot, slotXPos, rowYPos));
                currentCraftSlot++;
            }
        }

        //crafting result
        Vector3i[] craftResultPositions = {
                new Vector3i(156, 37, 0),
                new Vector3i(173, 9, 0),
                new Vector3i(190, 37, 0)};
        for (int i = 0; i < 3; i++) {
            this.addSlot(new Slot(outputZoneInventory, i, craftResultPositions[i].getX(), craftResultPositions[i].getY()) {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    return false;
                }
            });
        }

        //player inventory
        for (int y = 0; y < 4; ++y) {
            for (int x = 0; x < 9; ++x) {
                int index = x + y * 9;
                int posX = -51 + x * 20;
                int posY = 109 + y * 20;
                this.addSlot(new Slot(playerInventory, index, posX, posY));
            }
        }
        //endregion
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return inputZoneInventory.stillValid(playerIn) && outputZoneInventory.stillValid(playerIn);
    }

    @OnlyIn(Dist.CLIENT)
    public int getProgressArrowScale() {
        int progress = this.fabricatorStateData.get(0);
        int progressArrowHeight = 29;
        if (progress > 0) {
            return progress * progressArrowHeight / 100;
        }
        return 0;
        //TODO THIS SHIT BROKEN
    }

    private enum SlotZone {
        INPUT_ZONE(0, 12),
        OUTPUT_ZONE(12, 3),
        PLAYER_MAIN_INVENTORY(15, 27),
        PLAYER_HOTBAR(27, 9);

        SlotZone(int firstIndex, int numberOfSlots) {
            this.firstIndex = firstIndex;
            this.slotCount = numberOfSlots;
            this.lastIndexPlus1 = firstIndex + numberOfSlots;
        }

        public final int firstIndex;
        public final int slotCount;
        public final int lastIndexPlus1;

        public static SlotZone getZoneFromIndex(int slotIndex) {
            for (SlotZone slotZone : SlotZone.values()) {
                if (slotIndex >= slotZone.firstIndex && slotIndex < slotZone.lastIndexPlus1) return slotZone;
            }
            throw new IndexOutOfBoundsException("Unexpected slotIndex");
        }
    }

    private static Logger LOGGER = LogManager.getLogger();
}
