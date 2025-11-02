package com.addon.modulartech.items;

import com.addon.modulartech.ModularTech;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPowerPickaxe extends ItemModularTool {

    public ItemPowerPickaxe() {
        super(10000, 1);
        setUnlocalizedName("power_pickaxe");
        setTextureName("modulartech:power_pickaxe");
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (canUse(stack, 100)) {
            return 8.0F + getModuleLevel(stack, "module_efficiency") * 2.0F;
        }
        return 1.0F;
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
        if (getModuleLevel(stack, "module_fortune") > 0) {
            stack.addEnchantment(Enchantment.fortune, getModuleLevel(stack, "module_fortune"));
        }
        if (getModuleLevel(stack, "module_autosmelt") > 0) {
            stack.addEnchantment(Enchantment.silkTouch, 1);
        }
        int radius = getModuleLevel(stack, "module_area_miner");
        if (radius > 0) {
            for (int i = x - radius; i <= x + radius; i++) {
                for (int j = y - radius; j <= y + radius; j++) {
                    for (int k = z - radius; k <= z + radius; k++) {
                        if (canUse(stack, 100)) {
                            use(stack, 100, player);
                            player.worldObj.func_147480_a(i, j, k, true);
                        }
                    }
                }
            }
        } else {
            if (canUse(stack, 100)) {
                use(stack, 100, player);
            } else {
                return true;
            }
        }
        return false;
    }
}
