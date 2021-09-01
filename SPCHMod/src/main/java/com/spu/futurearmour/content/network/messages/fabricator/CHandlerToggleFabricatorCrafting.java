package com.spu.futurearmour.content.network.messages.fabricator;

import com.spu.futurearmour.content.tileentities.FabricatorControllerTileEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class CHandlerToggleFabricatorCrafting {
    public static void onMessageReceived(final CTSMessageToggleFabricatorCrafting message, Supplier<NetworkEvent.Context> ctxSupplier){
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide sideReceived = ctx.getDirection().getReceptionSide();
        ctx.setPacketHandled(true);

        if(!message.isMessageValid())return;

        switch (sideReceived){
            case CLIENT:
                LOGGER.warn("CTS Message received on wrong side");
                return;
            case SERVER:
                final ServerPlayerEntity sendingPlayer = ctx.getSender();
                if (sendingPlayer == null) {
                    LOGGER.warn("EntityPlayerMP was null when CTSMessageToggleFabricatorCrafting was received");
                }

                ctx.enqueueWork(() -> processOnServer(message, sendingPlayer));
                break;
        }
    }

    private static void processOnServer(final CTSMessageToggleFabricatorCrafting message, ServerPlayerEntity sendingPlayer){
        ServerWorld serverWorld = sendingPlayer.getLevel();
        TileEntity tileEntity = serverWorld.getBlockEntity(new BlockPos(message.getFabricatorPosition()));
        if(tileEntity instanceof FabricatorControllerTileEntity){
            FabricatorControllerTileEntity fabricatorTileEntity = (FabricatorControllerTileEntity) tileEntity;
            fabricatorTileEntity.toggleCrafting(message.getNextStateIsOn());
        }
    }

    private static final Logger LOGGER = LogManager.getLogger();
}
