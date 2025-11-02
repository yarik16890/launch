package com.addon.modulartech.network;

import com.addon.modulartech.tileentities.TileEntityIntegrator;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class ModeSwitchMessage implements IMessage {
    private int x, y, z;

    public ModeSwitchMessage() {}

    public ModeSwitchMessage(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public static class Handler implements IMessageHandler<ModeSwitchMessage, IMessage> {
        @Override
        public IMessage onMessage(ModeSwitchMessage message, MessageContext ctx) {
            TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
            if (tileEntity instanceof TileEntityIntegrator) {
                TileEntityIntegrator integrator = (TileEntityIntegrator) tileEntity;
                integrator.mode = (integrator.mode + 1) % 3;
            }
            return null;
        }
    }
}
