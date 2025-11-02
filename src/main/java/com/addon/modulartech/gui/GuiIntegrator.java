package com.addon.modulartech.gui;

import com.addon.modulartech.network.ModeSwitchMessage;
import com.addon.modulartech.network.PacketHandler;
import com.addon.modulartech.tileentities.TileEntityIntegrator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiIntegrator extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation("modulartech", "textures/gui/integrator.png");
    private TileEntityIntegrator tileEntity;

    public GuiIntegrator(InventoryPlayer inventoryPlayer, TileEntityIntegrator tileEntity) {
        super(new ContainerIntegrator(inventoryPlayer, tileEntity));
        this.tileEntity = tileEntity;
        xSize = 176;
        ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(0, guiLeft + 70, guiTop + 34, 36, 20, "Mode"));
        buttonList.add(new GuiButton(1, guiLeft + 70, guiTop + 56, 36, 20, "Run"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            PacketHandler.INSTANCE.sendToServer(new ModeSwitchMessage(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord));
        } else if (button.id == 1) {
            PacketHandler.INSTANCE.sendToServer(new IntegratorRunMessage(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String mode = "Mode: ";
        switch (tileEntity.mode) {
            case 0: mode += "Integrate"; break;
            case 1: mode += "Remove"; break;
            case 2: mode += "Upgrade"; break;
        }
        fontRendererObj.drawString(mode, 8, 6, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(texture);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
    }
}
