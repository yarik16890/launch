package com.addon.modulartech;

import com.addon.modulartech.blocks.BlockCompressor;
import com.addon.modulartech.blocks.BlockIntegrator;
import com.addon.modulartech.gui.GuiHandler;
import com.addon.modulartech.items.ItemPowerPickaxe;
import com.addon.modulartech.items.ItemPowerSword;
import com.addon.modulartech.items.modules.*;
import com.addon.modulartech.items.upgrades.UpgradeTier;
import com.addon.modulartech.network.PacketHandler;
import com.addon.modulartech.tileentities.TileEntityCompressor;
import com.addon.modulartech.tileentities.TileEntityIntegrator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;

@Mod(modid = "modulartech", name = "ModularTech", version = "1.0", dependencies = "required-after:IC2")
public class ModularTech {

    @Mod.Instance("modulartech")
    public static ModularTech instance;

    public static Logger logger;

    public static Block compressor;
    public static Block integrator;
    public static Item powerPickaxe;
    public static Item powerSword;
    public static Item moduleAreaMiner;
    public static Item moduleEfficiency;
    public static Item moduleFortune;
    public static Item moduleAutosmelt;
    public static Item moduleDamage;
    public static Item upgradeTier;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        compressor = new BlockCompressor();
        GameRegistry.registerTileEntity(TileEntityCompressor.class, "tileEntityCompressor");
        integrator = new BlockIntegrator();
        GameRegistry.registerTileEntity(TileEntityIntegrator.class, "tileEntityIntegrator");

        powerPickaxe = new ItemPowerPickaxe();
        GameRegistry.registerItem(powerPickaxe, "power_pickaxe");

        powerSword = new ItemPowerSword();
        GameRegistry.registerItem(powerSword, "power_sword");

        moduleAreaMiner = new ModuleAreaMiner();
        GameRegistry.registerItem(moduleAreaMiner, "module_area_miner");
        moduleEfficiency = new ModuleEfficiency();
        GameRegistry.registerItem(moduleEfficiency, "module_efficiency");
        moduleFortune = new ModuleFortune();
        GameRegistry.registerItem(moduleFortune, "module_fortune");
        moduleAutosmelt = new ModuleAutosmelt();
        GameRegistry.registerItem(moduleAutosmelt, "module_autosmelt");
        moduleDamage = new ModuleDamage();
        GameRegistry.registerItem(moduleDamage, "module_damage");
        upgradeTier = new UpgradeTier();
        GameRegistry.registerItem(upgradeTier, "upgrade_tier");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        PacketHandler.init();

        GameRegistry.addRecipe(new ItemStack(integrator), "IGI", "RGR", "IGI", 'I', Items.iron_ingot, 'G', Items.gold_ingot, 'R', Items.redstone);
        GameRegistry.addRecipe(new ItemStack(powerPickaxe), "DID", " I ", " I ", 'D', Items.diamond, 'I', Items.iron_ingot);
        GameRegistry.addRecipe(new ItemStack(powerSword), " D ", " D ", " I ", 'D', Items.diamond, 'I', Items.iron_ingot);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
