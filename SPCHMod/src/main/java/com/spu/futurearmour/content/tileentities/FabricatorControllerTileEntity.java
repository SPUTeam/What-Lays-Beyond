package com.spu.futurearmour.content.tileentities;

import com.ibm.icu.impl.IllegalIcuArgumentException;
import com.spu.futurearmour.FutureArmour;
import com.spu.futurearmour.content.blocks.fabricator.FabricatorController;
import com.spu.futurearmour.content.containers.FabricatorControllerContainer;
import com.spu.futurearmour.content.recipes.fabricator.FabricatorRecipe;
import com.spu.futurearmour.setup.BlockRegistry;
import com.spu.futurearmour.setup.ModBlockStateProperties;
import com.spu.futurearmour.setup.RecipeTypesRegistry;
import com.spu.futurearmour.setup.TileEntityTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FabricatorControllerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IInventory {
    public static final int INPUT_SLOTS_COUNT = 12;
    public static final int OUTPUT_SLOTS_COUNT = 3;
    public static final int TOTAL_SLOTS_COUNT = INPUT_SLOTS_COUNT + OUTPUT_SLOTS_COUNT;

    private boolean assembled;
    private HashMap<BlockPos, Block> structureBlueprint;
    private HashMap<BlockPos, BlockState> registeredParts;

    private TileEntityZoneInventory inputInventory;
    private TileEntityZoneInventory outputInventory;
    private final FabricatorStateData fabricatorStateData = new FabricatorStateData();
    private ResourceLocation previousRecipeID = new ResourceLocation("");
    private ResourceLocation currentRecipeID = new ResourceLocation("");
    private boolean craftingIsOn = false;

    public FabricatorControllerTileEntity() {
        super(TileEntityTypeRegistry.FABRICATOR_CONTROLLER_TILE_ENTITY_TYPE.get());
        inputInventory = TileEntityZoneInventory.createForTileEntity(INPUT_SLOTS_COUNT,
                this::canPlayerAccessInventory, this::setChanged);
        outputInventory = TileEntityZoneInventory.createForTileEntity(OUTPUT_SLOTS_COUNT,
                this::canPlayerAccessInventory, this::setChanged);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        updateFabricatorPosition();
    }

    @Override
    public void tick() {
        if (this.getLevel() == null) return;

        //maintaining multiblock structure
        if (structureBlueprint == null) structureBlueprint = getStructureBlueprint();
        if (!assembled) {
            boolean allBlocksInPlace = checkStructureIntegrity(structureBlueprint);
            if (allBlocksInPlace) {
                registeredParts = registerParts(structureBlueprint);
                changeAssemblyState(true);
            }
        }

        if (this.level.isClientSide()) return;

        //crafting
        tickCrafting(currentRecipeID, fabricatorStateData);
    }

    private void updateFabricatorPosition(){
        fabricatorStateData.set(3, getBlockPos().getX());
        fabricatorStateData.set(4, getBlockPos().getY());
        fabricatorStateData.set(5, getBlockPos().getZ());
    }

    //region Crafting
    private void tickCrafting(ResourceLocation recipeId, FabricatorStateData stateData) {
        FabricatorRecipe newRecipe;
        if (Objects.equals(recipeId, new ResourceLocation("")) || !recipeId.getNamespace().equals(FutureArmour.MOD_ID)) {
            newRecipe = getCurrentRecipe();
            if (newRecipe == null || !canStartCraft(newRecipe, outputInventory)) return;
            startCraft(newRecipe, inputInventory);
        }

        RecipeManager recipeManager = getLevel().getRecipeManager();
        FabricatorRecipe currentRecipe = (FabricatorRecipe) recipeManager.byKey(currentRecipeID).get();

        if (fabricatorStateData.get(0) >= fabricatorStateData.get(1)) {
            finishCraft(currentRecipe, outputInventory);
            return;
        }

        proceedCraft(stateData);

        previousRecipeID = currentRecipeID;
    }

    private void startCraft(FabricatorRecipe recipe, TileEntityZoneInventory inputInventory) {
        updateStateData(recipe);
        inputInventory.decreaseAllStacks();
        setChanged();
    }

    private void proceedCraft(FabricatorStateData stateData) {
        updateStateData(stateData.get(0) + 1);
        setChanged();
    }

    private void finishCraft(FabricatorRecipe recipe, TileEntityZoneInventory outputInventory) {
        outputInventory.insertStack(recipe.getResultItem().copy());
        updateStateData(null);
        setChanged();

        FabricatorRecipe nextRecipe = getCurrentRecipe();
        if(nextRecipe == null || !previousRecipeID.toString().equals(nextRecipe.getId().toString())){
            if(this.craftingIsOn)toggleCrafting(false);
        }
    }

    private boolean canStartCraft(FabricatorRecipe recipe, TileEntityZoneInventory outputInventory) {
        boolean buttonToggled = this.craftingIsOn;
        boolean enoughSpace = outputInventory.canFitStack(recipe.getResultItem());
        return enoughSpace && buttonToggled;
    }

    public void playerInteract(PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity)) return;
        NetworkHooks.openGui((ServerPlayerEntity) player, this, getBlockPos());
    }

    private FabricatorRecipe getCurrentRecipe() {
        RecipeManager recipeManager = getLevel().getRecipeManager();
        FabricatorRecipe recipe = recipeManager.getRecipeFor(RecipeTypesRegistry.FABRICATING_RECIPE, this, getLevel()).orElse(null);
        return recipe;
    }

    private void updateStateData(int craftTicksElapsed) {
        int maxTicksForCurrentRecipe = fabricatorStateData.get(1);
        //clamp 0,maxTicks (thanks java, very cool)
        int validatedTicksLeft = craftTicksElapsed > maxTicksForCurrentRecipe ? maxTicksForCurrentRecipe : Math.max(craftTicksElapsed, 0);
        fabricatorStateData.set(0, validatedTicksLeft);
    }

    private void updateStateData(FabricatorRecipe newRecipe) {
        if (newRecipe == null) {
            fabricatorStateData.set(0,0);
            fabricatorStateData.set(1,0);
            currentRecipeID = new ResourceLocation("");
            return;
        }

        int maxTicksForRecipe = newRecipe.getTime();
        currentRecipeID = newRecipe.getId();
        fabricatorStateData.set(0, 0);
        fabricatorStateData.set(1, maxTicksForRecipe);
    }

    public void toggleCrafting(boolean nextState){
        if (this.level.isClientSide()) return;

        this.craftingIsOn = nextState;
        int asInt = this.craftingIsOn ? 1 : 0;
        fabricatorStateData.set(2, asInt);
    }
    //endregion

    //region Assembling/Maintaining structure
    public boolean isAssembled() {
        return assembled;
    }

    private HashMap<BlockPos, Block> getStructureBlueprint() {
        HashMap<BlockPos, Block> result = new HashMap<>();
        Block partBlock = BlockRegistry.FABRICATOR_PART_BLOCK.get();

        BlockPos controllerPos = this.getBlockPos();
        Direction controllerDirection = this.getBlockState().getValue(FabricatorController.FACING);
        result.put(controllerPos, BlockRegistry.FABRICATOR_CONTROLLER_BLOCK.get());

        BlockPos behindControllerPos = controllerPos.relative(controllerDirection.getOpposite());
        result.put(behindControllerPos, partBlock);

        Direction sideBlocksDirection = controllerDirection.getClockWise();
        BlockPos controllerSidePos = controllerPos.relative(sideBlocksDirection);
        result.put(controllerSidePos, partBlock);

        BlockPos backSidePos = behindControllerPos.relative(sideBlocksDirection);
        result.put(backSidePos, partBlock);

        BlockPos[] middlePos = result.keySet().toArray(new BlockPos[4]);
        for (BlockPos pos : middlePos) {
            result.put(pos.above(), partBlock);
            result.put(pos.below(), partBlock);
        }

        return result;
    }

    private boolean checkStructureIntegrity(HashMap<BlockPos, Block> blueprint) {
        World world = this.getLevel();
        for (Map.Entry<BlockPos, Block> entry : blueprint.entrySet()) {
            if (!world.getBlockState(entry.getKey()).getBlock().equals(entry.getValue())) return false;

            if (entry.getKey() == this.getBlockPos()) continue;
            TileEntity entity = world.getBlockEntity(entry.getKey());
            if (entity.getType() == TileEntityTypeRegistry.FABRICATOR_PART_TILE_ENTITY_TYPE.get()) {
                FabricatorPartTileEntity partEntity = (FabricatorPartTileEntity) entity;
                boolean connectedToAnotherController = partEntity.controllerPos != this.getBlockPos();

                if (partEntity.assembled && connectedToAnotherController) return false;
            } else {
                return false;
            }

        }
        return true;
    }

    private HashMap<BlockPos, BlockState> registerParts(HashMap<BlockPos, Block> blueprint) {
        HashMap<BlockPos, BlockState> result = new HashMap<>();
        World world = this.getLevel();

        for (Map.Entry<BlockPos, Block> entry : blueprint.entrySet()) {
            BlockPos pos = entry.getKey();
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == entry.getValue()) {
                result.put(pos, state);

                if (entry.getKey() == this.getBlockPos()) continue;
                TileEntity entity = world.getBlockEntity(pos);
                if (entity.getType() == TileEntityTypeRegistry.FABRICATOR_PART_TILE_ENTITY_TYPE.get()) {
                    ((FabricatorPartTileEntity) entity).registerToController(this.getBlockPos(), this);
                }
            }
        }
        return result;
    }

    private void changeAssemblyState(boolean toAssembled) {
        if (assembled == toAssembled) return;
        switchModels(registeredParts, toAssembled);
        assembled = toAssembled;
        setChanged();
    }

    public void partRemoved(BlockPos removalEmptyPos) {
        if (assembled) {
            registeredParts.remove(removalEmptyPos);
            boolean allBlocksInPlace = checkStructureIntegrity(structureBlueprint);
            if (!allBlocksInPlace) changeAssemblyState(false);
        }
    }

    private void switchModels(HashMap<BlockPos, BlockState> parts, boolean toAssembled) {
        World world = this.getLevel();
        for (Map.Entry<BlockPos, BlockState> entry : parts.entrySet()) {
            BlockState newState = entry.getValue().setValue(ModBlockStateProperties.MULTIBLOCK_ASSEMBLED, toAssembled);
            world.setBlock(entry.getKey(), newState, 3);
        }
    }

    public void onBlockRemoved() {
        registerParts(structureBlueprint);
        registeredParts.remove(this.getBlockPos());
        if (assembled) changeAssemblyState(false);

        World world = this.getLevel();
        for (Map.Entry<BlockPos, BlockState> entry : registeredParts.entrySet()) {
            TileEntity entity = world.getBlockEntity(entry.getKey());
            if (entity != null && entity.getType() == TileEntityTypeRegistry.FABRICATOR_PART_TILE_ENTITY_TYPE.get()) {
                ((FabricatorPartTileEntity) entity).detachFromController();
            }
        }

        structureBlueprint = null;
        registeredParts = null;
    }
    //endregion

    //region IContainerProvider
    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Fabricator");
    }

    @Nullable
    @Override
    public Container createMenu(int menuID, PlayerInventory playerInventory, PlayerEntity player) {
        return FabricatorControllerContainer.createForServer(menuID, playerInventory, inputInventory, outputInventory, fabricatorStateData);
    }

    public int[] getCraftingSlotIndexes() {
        int[] indexes = new int[INPUT_SLOTS_COUNT];
        for (int i = 0; i < INPUT_SLOTS_COUNT; i++) {
            indexes[i] = i;
        }
        return indexes;
    }

    public TileEntityZoneInventory getInputInventory() {
        return inputInventory;
    }
    //endregion

    //region Save/Load & Server<->Client Communication

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        fabricatorStateData.saveToNBT(nbt);
        nbt.putString("previous_recipe_id", previousRecipeID.toString());
        nbt.putString("current_recipe_id", currentRecipeID.toString());
        nbt.put("input_slots", inputInventory.serializeNBT());
        nbt.put("output_slots", outputInventory.serializeNBT());
        nbt.putBoolean("craftingIsOn", craftingIsOn);
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);

        fabricatorStateData.loadFromNBT(nbt);

        String previousRecipeIdString = nbt.getString("previous_recipe_id");
        previousRecipeID = ResourceLocation.tryParse(previousRecipeIdString);

        String currentRecipeIdString = nbt.getString("current_recipe_id");
        currentRecipeID = ResourceLocation.tryParse(currentRecipeIdString);

        CompoundNBT inventoryNBT = nbt.getCompound("input_slots");
        inputInventory.deserializeNBT(inventoryNBT);

        inventoryNBT = nbt.getCompound("output_slots");
        outputInventory.deserializeNBT(inventoryNBT);

        craftingIsOn = nbt.getBoolean("craftingIsOn");

        if (inputInventory.getContainerSize() != INPUT_SLOTS_COUNT
                || outputInventory.getContainerSize() != OUTPUT_SLOTS_COUNT) {
            throw new IllegalIcuArgumentException("Corrupted NBT: Number of inventory slots did not match expected.");
        }
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT updateTagDescribingTileEntityState = getUpdateTag();
        final int METADATA = 42;
        return new SUpdateTileEntityPacket(this.getBlockPos(), METADATA, updateTagDescribingTileEntityState);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT updateTagDescribingTileEntityState = pkt.getTag();
        BlockState blockState = level.getBlockState(worldPosition);
        handleUpdateTag(blockState, updateTagDescribingTileEntityState);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        save(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state, tag);
    }

    //endregion

    public boolean canPlayerAccessInventory(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        boolean result = player.distanceToSqr(this.worldPosition.getX() + X_CENTRE_OFFSET, this.worldPosition.getY() + Y_CENTRE_OFFSET, this.worldPosition.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
        return result;
    }

    private static Logger LOGGER = LogManager.getLogger();

    //region DO NOT FKING TOUCH! needed for recipe serialization
    @Override
    public int getContainerSize() {
        throw new RuntimeException("Do not use TE's IInventory. Use it's TileEntityZoneInventory instead.");
    }

    @Override
    public boolean isEmpty() {
        throw new RuntimeException("Do not use TE's IInventory. Use it's TileEntityZoneInventory instead.");
    }

    @Override
    public ItemStack getItem(int p_70301_1_) {
        throw new RuntimeException("Do not use TE's IInventory. Use it's TileEntityZoneInventory instead.");
    }

    @Override
    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        throw new RuntimeException("Do not use TE's IInventory. Use it's TileEntityZoneInventory instead.");
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        throw new RuntimeException("Do not use TE's IInventory. Use it's TileEntityZoneInventory instead.");
    }

    @Override
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
        throw new RuntimeException("Do not use TE's IInventory. Use it's TileEntityZoneInventory instead.");
    }

    @Override
    public boolean stillValid(PlayerEntity p_70300_1_) {
        throw new RuntimeException("Do not use TE's IInventory. Use it's TileEntityZoneInventory instead.");
    }

    @Override
    public void clearContent() {
        throw new RuntimeException("Do not use TE's IInventory. Use it's TileEntityZoneInventory instead.");
    }
    //endregion
}