package com.addon.modulartech.gui;

import com.addon.modulartech.items.ItemPowerPickaxe;
import com.addon.modulartech.network.PacketHandler;
import com.addon.modulartech.network.SelectModuleMessage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiPowerPickaxe extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation("modulartech", "textures/gui/power_pickaxe.png");
    private ItemStack itemStack;

    public GuiPowerPickaxe(InventoryPlayer inventoryPlayer, ItemStack itemStack) {
        super(new ContainerPowerPickaxe(inventoryPlayer, itemStack));
        this.itemStack = itemStack;
        xSize = 176;
        ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(0, guiLeft + 8, guiTop + 20, 100, 20, "Area Miner"));
        buttonList.add(new GuiButton(1, guiLeft + 8, guiTop + 42, 100, 20, "Efficiency"));
        buttonList.add(new GuiButton(2, guiLeft + 8, guiTop + 64, 100, 20, "Fortune"));
        buttonList.add(new GuiButton(3, guiLeft + 8, guiTop + 86, 100, 20, "Autosmelt"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        String module = "";
        switch (button.id) {
            case 0: module = "module_area_miner"; break;
            case 1: module = "module_efficiency"; break;
            case 2: module = "module_fortune"; break;
            case 3: module = "module_autosmelt"; break;
        }
        PacketHandler.INSTANCE.sendToServer(new SelectModuleMessage(module));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString("Area Miner: " + ItemPowerPickaxe.getModuleLevel(itemStack, "module_area_miner"), 110, 26, 4210752);
        fontRendererObj.drawString("Efficiency: " + ItemPowerPickaxe.getModuleLevel(itemStack, "module_efficiency"), 110, 48, 4210752);
        fontRendererObj.drawString("Fortune: " + ItemPowerPickaxe.getModuleLevel(itemStack, "module_fortune"), 110, 70, 4210752);
        fontRendererObj.drawString("Autosmelt: " + ItemPowerPickaxe.getModuleLevel(itemStack, "module_autosmelt"), 110, 92, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(texture);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
    }
}
