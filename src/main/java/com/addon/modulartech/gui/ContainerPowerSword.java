package com.addon.modulartech.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerPowerSword extends Container {

    public ContainerPowerSword(InventoryPlayer inventoryPlayer, ItemStack itemStack) {
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
