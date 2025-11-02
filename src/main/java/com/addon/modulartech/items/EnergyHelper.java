package com.addon.modulartech.items;

import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnergyHelper {

    public static boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    public static Item getChargedItem(ItemStack itemStack) {
        return itemStack.getItem();
    }

    public static Item getEmptyItem(ItemStack itemStack) {
        return itemStack.getItem();
    }

    public static double getMaxCharge(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ISpecialElectricItem) {
            return ((ISpecialElectricItem) itemStack.getItem()).getMaxCharge(itemStack);
        }
        return 0;
    }

    public static int getTier(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ISpecialElectricItem) {
            return ((ISpecialElectricItem) itemStack.getItem()).getTier(itemStack);
        }
        return 0;
    }

    public static double getTransferLimit(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ISpecialElectricItem) {
            return ((ISpecialElectricItem) itemStack.getItem()).getTransferLimit(itemStack);
        }
        return 0;
    }

    public static IElectricItemManager getManager(ItemStack itemStack) {
        return ElectricItemManager.instance;
    }
}
