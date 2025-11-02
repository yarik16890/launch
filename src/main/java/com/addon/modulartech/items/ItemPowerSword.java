package com.addon.modulartech.items;

import com.addon.modulartech.ModularTech;
import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import java.util.UUID;

public class ItemPowerSword extends ItemModularTool {

    public ItemPowerSword() {
        super(10000, 1);
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
        multimap.removeAll(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", 6 + getModuleLevel(stack, "module_damage") * 1, 0));
        return multimap;
    }
}
