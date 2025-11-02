package com.addon.ic2addon;

import com.addon.ic2addon.blocks.BlockCompressor;
import com.addon.ic2addon.gui.GuiHandler;
import com.addon.ic2addon.tileentities.TileEntityCompressor;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import org.apache.logging.log4j.Logger;

@Mod(modid = "ic2addon", name = "IC2 Addon", version = "1.0", dependencies = "required-after:IC2")
public class IC2Addon {

    @Mod.Instance("ic2addon")
    public static IC2Addon instance;

    public static Logger logger;

    public static Block compressor;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        compressor = new BlockCompressor();
        GameRegistry.registerTileEntity(TileEntityCompressor.class, "tileEntityCompressor");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
