package com.addon.ic2addon.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCompressor extends TileEntity implements IInventory, IEnergySink {

    private ItemStack[] inventory = new ItemStack[8]; // 4 input, 4 output
    private double energy = 0;
    public int progress = 0;
    public final int maxProgress = 200;
    private final double maxEnergy = 10000;
    private final int tier = 1;

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (inventory[slot] != null) {
            ItemStack itemstack;
            if (inventory[slot].stackSize <= amount) {
                itemstack = inventory[slot];
                inventory[slot] = null;
                return itemstack;
            } else {
                itemstack = inventory[slot].splitStack(amount);
                if (inventory[slot].stackSize == 0) {
                    inventory[slot] = null;
                }
                return itemstack;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (inventory[slot] != null) {
            ItemStack itemstack = inventory[slot];
            inventory[slot] = null;
            return itemstack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return "container.compressor";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this &&
               player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) <= 64;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return slot < 4; // Only allow items in input slots
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        inventory = new ItemStack[getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < inventory.length) {
                inventory[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        compound.setTag("Items", nbttaglist);
        compound.setDouble("energy", energy);
    }

    @Override
    public boolean acceptsEnergyFrom(Object emitter, ForgeDirection direction) {
        return true;
    }

    @Override
    public double getDemandedEnergy() {
        return maxEnergy - energy;
    }

    @Override
    public int getSinkTier() {
        return tier;
    }

    @Override
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        energy += amount;
        if (energy > maxEnergy) {
            amount = energy - maxEnergy;
            energy = maxEnergy;
        } else {
            amount = 0;
        }
        return amount;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (canProcess()) {
                energy -= 10;
                progress++;
                if (progress >= maxProgress) {
                    progress = 0;
                    processItem();
                }
            } else {
                progress = 0;
            }
        }
    }

    private boolean canProcess() {
        for (int i = 0; i < 4; i++) {
            if (inventory[i] != null && inventory[i].getItem() == net.minecraft.init.Items.coal) {
                for (int j = 4; j < 8; j++) {
                    if (inventory[j] == null || (inventory[j].getItem() == net.minecraft.init.Items.diamond && inventory[j].stackSize < 64)) {
                        return energy >= 10;
                    }
                }
            }
        }
        return false;
    }

    private void processItem() {
        for (int i = 0; i < 4; i++) {
            if (inventory[i] != null && inventory[i].getItem() == net.minecraft.init.Items.coal) {
                decrStackSize(i, 1);
                for (int j = 4; j < 8; j++) {
                    if (inventory[j] == null) {
                        setInventorySlotContents(j, new ItemStack(net.minecraft.init.Items.diamond));
                        return;
                    } else if (inventory[j].getItem() == net.minecraft.init.Items.diamond) {
                        inventory[j].stackSize++;
                        return;
                    }
                }
            }
        }
    }
}
