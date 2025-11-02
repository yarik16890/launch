package com.addon.modulartech.network;

import com.addon.modulartech.items.ItemModularTool;
import com.addon.modulartech.items.ItemModule;
import com.addon.modulartech.items.upgrades.UpgradeTier;
import com.addon.modulartech.tileentities.TileEntityIntegrator;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IElectricItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class IntegratorRunMessage implements IMessage {
    private int x, y, z;

    public IntegratorRunMessage() {}

    public IntegratorRunMessage(int x, int y, int z) {
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

    public static class Handler implements IMessageHandler<IntegratorRunMessage, IMessage> {
        @Override
        public IMessage onMessage(IntegratorRunMessage message, MessageContext ctx) {
            TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
            if (tileEntity instanceof TileEntityIntegrator) {
                TileEntityIntegrator integrator = (TileEntityIntegrator) tileEntity;
                ItemStack tool = integrator.getStackInSlot(0);
                ItemStack module = integrator.getStackInSlot(1);
                if (tool != null && tool.getItem() instanceof IElectricItem) {
                    if (integrator.mode == 0 && module != null && module.getItem() instanceof ItemModule) {
                        ItemModularTool.setModuleLevel(tool, module.getUnlocalizedName().replace("item.", ""), 1);
                        integrator.decrStackSize(1, 1);
                    } else if (integrator.mode == 1) {
                        String selectedModule = tool.getTagCompound().getString("selected_module");
                        ItemModularTool.setModuleLevel(tool, selectedModule, 0);
                        integrator.setInventorySlotContents(1, new ItemStack(GameRegistry.findItem("modulartech", selectedModule.replace("item.", ""))));
                    } else if (integrator.mode == 2 && module != null && module.getItem() instanceof UpgradeTier) {
                        String selectedModule = tool.getTagCompound().getString("selected_module");
                        int currentLevel = ItemModularTool.getModuleLevel(tool, selectedModule);
                        ItemModularTool.setModuleLevel(tool, selectedModule, currentLevel + 1);
                        integrator.decrStackSize(1, 1);
                    }
                }
                ctx.getServerHandler().playerEntity.openContainer.detectAndSendChanges();
            }
            return null;
        }
    }
}
