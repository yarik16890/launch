package com.addon.modulartech.gui;

import com.addon.modulartech.items.ItemModularTool;
import com.addon.modulartech.network.PacketHandler;
import com.addon.modulartech.network.SelectModuleMessage;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiPowerSword extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation("modulartech", "textures/gui/power_sword.png");
    private ItemStack itemStack;

    public GuiPowerSword(InventoryPlayer inventoryPlayer, ItemStack itemStack) {
        super(new ContainerPowerSword(inventoryPlayer, itemStack));
        this.itemStack = itemStack;
        xSize = 176;
        ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(0, guiLeft + 8, guiTop + 20, 100, 20, "Damage"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        String module = "";
        if (button.id == 0) {
            module = "module_damage";
        }
        PacketHandler.INSTANCE.sendToServer(new SelectModuleMessage(module));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString("Damage: " + ItemModularTool.getModuleLevel(itemStack, "module_damage"), 110, 26, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(texture);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
    }
}
