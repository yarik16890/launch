package com.addon.modulartech.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiPowerSword extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation("modulartech", "textures/gui/power_sword.png");

    public GuiPowerSword(InventoryPlayer inventoryPlayer, ItemStack itemStack) {
        super(new ContainerPowerSword(inventoryPlayer, itemStack));
        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(texture);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
    }
}
