package com.addon.ic2addon.gui;

import com.addon.ic2addon.tileentities.TileEntityCompressor;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityCompressor) {
            return new ContainerCompressor(player.inventory, (TileEntityCompressor) tileEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityCompressor) {
            return new GuiCompressor(player.inventory, (TileEntityCompressor) tileEntity);
        }
        return null;
    }
}
