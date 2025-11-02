package com.addon.modulartech.network;

import com.addon.modulartech.items.ItemModularTool;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SelectModuleMessage implements IMessage {
    private String module;

    public SelectModuleMessage() {}

    public SelectModuleMessage(String module) {
        this.module = module;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int length = buf.readInt();
        module = new String(buf.readBytes(length).array());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(module.length());
        buf.writeBytes(module.getBytes());
    }

    public static class Handler implements IMessageHandler<SelectModuleMessage, IMessage> {
        @Override
        public IMessage onMessage(SelectModuleMessage message, MessageContext ctx) {
            ItemStack stack = ctx.getServerHandler().playerEntity.getHeldItem();
            if (stack != null && stack.getItem() instanceof ItemModularTool) {
                if (stack.getTagCompound() == null) {
                    stack.setTagCompound(new NBTTagCompound());
                }
                stack.getTagCompound().setString("selected_module", message.module);
            }
            return null;
        }
    }
}
