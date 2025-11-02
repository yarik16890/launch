package com.addon.modulartech.items;

import com.addon.modulartech.ModularTech;
import com.google.common.collect.Multimap;
import ic2.api.item.IElectricItemManager;
import ic2.api.item.ISpecialElectricItem;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemPowerSword extends ItemSword implements ISpecialElectricItem {

    public double maxCharge = 10000;
    public int tier = 1;
    public double transferLimit = 100;

    public ItemPowerSword() {
        super(ToolMaterial.EMERALD);
        setUnlocalizedName("power_sword");
        setTextureName("modulartech:power_sword");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.isSneaking()) {
            player.openGui(ModularTech.instance, 2, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return stack;
    }

    @Override
    public Multimap getItemAttributeModifiers(ItemStack stack) {
        Multimap multimap = super.getItemAttributeModifiers(stack);
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", 6 + ItemModularTool.getModuleLevel(stack, "module_damage") * 1, 0));
        return multimap;
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
