package com.addon.modulartech.blocks;

import com.addon.modulartech.ModularTech;
import com.addon.modulartech.tileentities.TileEntityCompressor;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCompressor extends BlockContainer {

    public final String name = "compressor";

    public BlockCompressor() {
        super(Material.iron);
        setBlockName(name);
        setBlockTextureName("modulartech:" + name);
        setCreativeTab(CreativeTabs.tabRedstone);
        GameRegistry.registerBlock(this, name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCompressor();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(ModularTech.instance, 0, world, x, y, z);
        }
        return true;
    }
}
