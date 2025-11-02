package com.addon.modulartech.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ic2.api.item.IElectricItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import java.nio.charset.StandardCharsets;

public class SelectModuleMessage implements IMessage {
    private String module;

    public SelectModuleMessage() {}

    public SelectModuleMessage(String module) {
        this.module = module;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int length = buf.readInt();
        module = buf.toString(buf.readerIndex(), length, StandardCharsets.UTF_8);
        buf.readerIndex(buf.readerIndex() + length);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        byte[] bytes = module.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    public static class Handler implements IMessageHandler<SelectModuleMessage, IMessage> {
        @Override
        public IMessage onMessage(SelectModuleMessage message, MessageContext ctx) {
            ItemStack stack = ctx.getServerHandler().playerEntity.getHeldItem();
            if (stack != null && stack.getItem() instanceof IElectricItem) {
                if (stack.getTagCompound() == null) {
                    stack.setTagCompound(new NBTTagCompound());
                }
                stack.getTagCompound().setString("selected_module", message.module);
            }
            return null;
        }
    }
}
