package com.addon.modulartech.items;

import ic2.api.item.IElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import java.util.List;

public class ItemModularTool extends Item implements IElectricItem {
    public double maxCharge;
    public int tier;

    public ItemModularTool(double maxCharge, int tier) {
        this.maxCharge = maxCharge;
        this.tier = tier;
        setMaxDamage(27);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        list.add("Energy: " + getCharge(stack) + " / " + getMaxCharge(stack));
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return maxCharge;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return tier;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 100;
    }

    @Override
    public double charge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean simulate) {
        NBTTagCompound nbt = getNBT(itemStack);
        double energy = nbt.getDouble("energy");
        if (amount > maxCharge - energy) {
            amount = maxCharge - energy;
        }
        if (!simulate) {
            nbt.setDouble("energy", energy + amount);
            itemStack.setItemDamage(26 - (int)(26 * (energy + amount) / maxCharge));
        }
        return amount;
    }

    @Override
    public double discharge(ItemStack itemStack, double amount, int tier, boolean ignoreTransferLimit, boolean externally, boolean simulate) {
        NBTTagCompound nbt = getNBT(itemStack);
        double energy = nbt.getDouble("energy");
        if (amount > energy) {
            amount = energy;
        }
        if (!simulate) {
            nbt.setDouble("energy", energy - amount);
            itemStack.setItemDamage(26 - (int)(26 * (energy - amount) / maxCharge));
        }
        return amount;
    }

    @Override
    public double getCharge(ItemStack itemStack) {
        return getNBT(itemStack).getDouble("energy");
    }

    @Override
    public boolean canUse(ItemStack itemStack, double amount) {
        return getCharge(itemStack) >= amount;
    }

    public void use(ItemStack itemStack, double amount, EntityPlayer player) {
        discharge(itemStack, amount, Integer.MAX_VALUE, true, false, false);
    }

    public static int getModuleLevel(ItemStack stack, String module) {
        if (stack.getTagCompound() == null || !stack.getTagCompound().hasKey("modules")) {
            return 0;
        }
        return stack.getTagCompound().getCompoundTag("modules").getInteger(module);
    }

    public static void setModuleLevel(ItemStack stack, String module, int level) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
        if (!stack.getTagCompound().hasKey("modules")) {
            stack.getTagCompound().setTag("modules", new NBTTagCompound());
        }
        stack.getTagCompound().getCompoundTag("modules").setInteger(module, level);
    }

    private NBTTagCompound getNBT(ItemStack itemStack) {
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        return itemStack.getTagCompound();
    }
}
