package com.addon.modulartech.items;

import com.addon.modulartech.ModularTech;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPowerPickaxe extends ItemPickaxe implements ISpecialElectricItem {
    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (ElectricItemManager.instance.canUse(stack, 100)) {
            return 8.0F + ItemModularTool.getModuleLevel(stack, "module_efficiency") * 2.0F;
        }
        return 1.0F;
    }

    public double maxCharge = 10000;
    public int tier = 1;
    public double transferLimit = 100;

    public ItemPowerPickaxe() {
        super(ToolMaterial.EMERALD);
        setUnlocalizedName("power_pickaxe");
        setTextureName("modulartech:power_pickaxe");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            player.openGui(ModularTech.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return stack;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        if (ItemModularTool.getModuleLevel(stack, "module_fortune") > 0) {
            stack.addEnchantment(Enchantment.fortune, ItemModularTool.getModuleLevel(stack, "module_fortune"));
        }
        if (ItemModularTool.getModuleLevel(stack, "module_autosmelt") > 0) {
            stack.addEnchantment(Enchantment.silkTouch, 1);
        }
        int radius = ItemModularTool.getModuleLevel(stack, "module_area_miner");
        if (radius > 0) {
            for (int i = x - radius; i <= x + radius; i++) {
                for (int j = y - radius; j <= y + radius; j++) {
                    for (int k = z - radius; k <= z + radius; k++) {
                        if (ElectricItemManager.instance.canUse(stack, 100)) {
                            ElectricItemManager.instance.use(stack, 100, player);
                            player.worldObj.func_147480_a(i, j, k, true);
                        }
                    }
                }
            }
        } else {
            if (ElectricItemManager.instance.canUse(stack, 100)) {
                ElectricItemManager.instance.use(stack, 100, player);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return EnergyHelper.canProvideEnergy(itemStack);
    }

    @Override
    public net.minecraft.item.Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public net.minecraft.item.Item getEmptyItem(ItemStack itemStack) {
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
        return EnergyHelper.getManager(itemStack);
    }
}
