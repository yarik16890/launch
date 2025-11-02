package com.addon.modulartech.items;

import net.minecraft.item.Item;

public class ItemModule extends Item {
    public ItemModule(String name) {
        setUnlocalizedName(name);
        setTextureName("modulartech:" + name);
    }

    @Override
    public String getUnlocalizedName() {
        return "item." + super.getUnlocalizedName().substring(5);
    }
}
