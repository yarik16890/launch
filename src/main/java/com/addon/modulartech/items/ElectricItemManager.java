package com.addon.modulartech.items;

import ic2.api.item.IElectricItemManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ic2.api.item.ElectricItem;

public class ElectricItemManager implements IElectricItemManager {
    public static final ElectricItemManager instance = new ElectricItemManager();

    @Override
    public double charge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
        if (itemStack.getItem() instanceof ItemModularTool) {
            ItemModularTool tool = (ItemModularTool) itemStack.getItem();
            if (tier < tool.getTier(itemStack)) {
                return 0;
            }
            if (!ignoreTransferLimit && amount > tool.getTransferLimit(itemStack)) {
                amount = tool.getTransferLimit(itemStack);
            }
            NBTTagCompound nbt = getNBT(itemStack);
            double energy = nbt.getDouble("energy");
            double maxCharge = tool.getMaxCharge(itemStack);
            if (amount > maxCharge - energy) {
                amount = maxCharge - energy;
            }
            if (!simulate) {
                nbt.setDouble("energy", energy + amount);
            }
            return amount;
        }
        return 0;
    }

    @Override
    public double discharge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        if (itemStack.getItem() instanceof ItemModularTool) {
            ItemModularTool tool = (ItemModularTool) itemStack.getItem();
            if (tier < tool.getTier(itemStack)) {
                return 0;
            }
            if (!ignoreTransferLimit && amount > tool.getTransferLimit(itemStack)) {
                amount = tool.getTransferLimit(itemStack);
            }
            NBTTagCompound nbt = getNBT(itemStack);
            double energy = nbt.getDouble("energy");
            if (amount > energy) {
                amount = energy;
            }
            if (!simulate) {
                nbt.setDouble("energy", energy - amount);
            }
            return amount;
        }
        return 0;
    }

    @Override
    public double getCharge(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemModularTool) {
            return getNBT(itemStack).getDouble("energy");
        }
        return 0;
    }

    @Override
    public boolean canUse(ItemStack itemStack, double amount) {
        return getCharge(itemStack) >= amount;
    }

    @Override
    public boolean use(ItemStack itemStack, double amount, net.minecraft.entity.EntityLivingBase entity) {
        discharge(itemStack, amount, Integer.MAX_VALUE, true, false, false);
        return true;
    }

    @Override
    public void chargeFromArmor(ItemStack itemStack, net.minecraft.entity.EntityLivingBase entity) {
        ElectricItem.manager.chargeFromArmor(itemStack, entity);
    }

    @Override
    public String getToolTip(ItemStack itemStack) {
        return null;
    }

    private NBTTagCompound getNBT(ItemStack itemStack) {
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        return itemStack.getTagCompound();
    }
}
