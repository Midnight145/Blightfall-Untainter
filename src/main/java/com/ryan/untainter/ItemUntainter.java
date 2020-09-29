package com.ryan.untainter;

import java.util.List;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import com.ryan.untainter.network.BlightbusterNetwork;

import thaumcraft.api.entities.ITaintedMob;
import thaumcraft.api.nodes.NodeType;
import thaumcraft.common.blocks.BlockFluxGoo;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.entities.monster.EntityTaintChicken;
import thaumcraft.common.entities.monster.EntityTaintCow;
import thaumcraft.common.entities.monster.EntityTaintCreeper;
import thaumcraft.common.entities.monster.EntityTaintPig;
import thaumcraft.common.entities.monster.EntityTaintSheep;
import thaumcraft.common.entities.monster.EntityTaintVillager;
import thaumcraft.common.items.wands.ItemWandCasting;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.lib.world.ThaumcraftWorldGenerator;
import thaumcraft.common.tiles.TileNode;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemUntainter extends Item{

		//Boilerplate setup stuff.
		public ItemUntainter() 
		{
			setUnlocalizedName(BlightBuster.MODID + "_untainter");
			GameRegistry.registerItem(this, "Untainter");
			setCreativeTab(CreativeTabs.tabMaterials);
			setTextureName(BlightBuster.MODID + ":untainter");
		}
		
		@Override
	    public ItemStack onItemRightClick(ItemStack itemStack, World theWorld, EntityPlayer thePlayer)
	    {
			//Only do server-side
			if (!theWorld.isRemote)
			{
				//Get the chunk the player is in (round down to the nearest multiple of 16)
				int x = ((int)thePlayer.posX/16)*16;
				int z = ((int)thePlayer.posZ/16)*16;
				
				//Inform the player.
				thePlayer.addChatMessage(new ChatComponentText("Untainting world based on coords: "));
				thePlayer.addChatMessage(new ChatComponentText("  xSection: "+x+", zSection: "+z));
				
				//Taint an 11 chunk diameter area.
				for (int xLoc = x-80; xLoc<x+96; xLoc++)
				{
					for (int zLoc = z-80; zLoc<z+96; zLoc++)
					{
						//Dunno how this works. Thaumcraft's "Utils" package does all the real work. :/
						cleanUpLand(xLoc, zLoc, theWorld, itemStack, thePlayer);
//						BlightbusterNetwork.setBiomeAt(theWorld, xLoc, zLoc, ThaumcraftWorldGenerator.biomeTaint);

					
					}
				}
				
				thePlayer.addChatMessage(new ChatComponentText("  World should now be untainted.")); //Well it should. :/

				
				//I was told by Zeno112 that this should prevent memory leaks.
				theWorld.getChunkProvider().unloadQueuedChunks();
				IntCache.resetIntCache();
			}
			
	        return itemStack;
	    }
	    private void cleanUpLand(int x, int z, World world, ItemStack itemStack, EntityPlayer p) {


	    	
			BiomeGenBase[] biomesForGeneration = null;
			biomesForGeneration = world.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, x, z, 1, 1);
			BlightbusterNetwork.setBiomeAt(world, x, z, biomesForGeneration[0]);

			List entities = world.getEntitiesWithinAABB(ITaintedMob.class, AxisAlignedBB.getBoundingBox(x - 1, 0, z - 1, x + 1, 256, z + 1));
		        for (Object entityObj : entities) {
		            if (entityObj instanceof EntityTaintSheep)
		            {
		                cleanseSingleMob((Entity)entityObj);
		            } else if (entityObj instanceof EntityTaintChicken) {
		                cleanseSingleMob((Entity)entityObj);
		            } else if (entityObj instanceof EntityTaintCow) {
		                cleanseSingleMob((Entity)entityObj);
		            } else if (entityObj instanceof EntityTaintPig) {
		                cleanseSingleMob((Entity)entityObj);
		            } else if (entityObj instanceof EntityTaintVillager) {
		                cleanseSingleMob((Entity)entityObj);
		            } else if (entityObj instanceof EntityTaintCreeper) {
		                cleanseSingleMob((Entity)entityObj);
		            }
		        }

        	
        	for (int y = 0; y < 256; y++) {

				TileEntity tile = p.getEntityWorld().getTileEntity(x, y, z);
		        if (tile != null && tile instanceof TileNode) {
		            TileNode node = (TileNode)tile;
		            if (node.getNodeType() == NodeType.TAINTED) {
		                node.setNodeType(NodeType.NORMAL);
		                node.markDirty();
		                p.getEntityWorld().markBlockForUpdate(x, y, z);
		            }
		        }
		        
        		if (world.getBlock(x, y, z) == ConfigBlocks.blockTaintFibres) {
        			world.setBlock(x, y, z, Blocks.air);
        		}
        		
        		if (world.getBlock(x, y, z) == ConfigBlocks.blockTaint) {
        			if (world.getBlockMetadata(x, y, z) == 0) {
        				world.setBlock(x, y, z, Blocks.air);
        			} 
        			else if (world.getBlockMetadata(x, y, z) == 1) {
        				world.setBlock(x, y, z, Blocks.dirt);
        			} 
        			else {
        				world.setBlock(x, y, z, Blocks.air);
        			}
        		}
        	}
	    }
	    private void cleanseSingleMob(Entity tainted) {

	        tainted.worldObj.removeEntity(tainted);
	    }
}
