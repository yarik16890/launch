package com.addon.modulartech.gui;

import com.addon.modulartech.tileentities.TileEntityCompressor;
import com.addon.modulartech.tileentities.TileEntityIntegrator;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityCompressor) {
                return new ContainerCompressor(player.inventory, (TileEntityCompressor) tileEntity);
            }
        } else if (ID == 1) {
            return new ContainerPowerPickaxe(player.inventory, player.getHeldItem());
        } else if (ID == 2) {
            return new ContainerPowerSword(player.inventory, player.getHeldItem());
        } else if (ID == 3) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityIntegrator) {
                return new ContainerIntegrator(player.inventory, (TileEntityIntegrator) tileEntity);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityCompressor) {
                return new GuiCompressor(player.inventory, (TileEntityCompressor) tileEntity);
            }
        } else if (ID == 1) {
            return new GuiPowerPickaxe(player.inventory, player.getHeldItem());
        } else if (ID == 2) {
            return new GuiPowerSword(player.inventory, player.getHeldItem());
        } else if (ID == 3) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityIntegrator) {
                return new GuiIntegrator(player.inventory, (TileEntityIntegrator) tileEntity);
            }
        }
        return null;
    }
}
