package com.addon.modulartech.items;

import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemModularTool extends Item implements ISpecialElectricItem {

    public double maxCharge = 10000;
    public int tier = 1;
    public double transferLimit = 100;

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
        return transferLimit;
    }

    @Override
    public IElectricItemManager getManager(ItemStack itemStack) {
        return ElectricItemManager.instance;
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
}
