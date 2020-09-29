package com.ryan.untainter;

import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;

import com.ryan.untainter.network.BlightbusterNetwork;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = BlightBuster.MODID, name = BlightBuster.MODNAME, version = BlightBuster.VERSION)//, dependencies = BlightBuster.DEPS)
public class BlightBuster
{
	
	public static final String MODID = "untainter";
	public static final String MODNAME = "Untainter";
    public static final String VERSION = "1.0.0";
//    public static final String DEPS = "before:UndergroundBiomes;after:ThermalFoundation;after:appliedenergistics2;after:Thaumcraft";
	public static final String COMMONPROXYLOCATION = "com.ryan."+MODID+".CommonProxy";
	public static final String CLIENTPROXYLOCATION = "com.ryan."+MODID+".ClientProxy";
	
	public static Item untainter;
	
	
	
	@SidedProxy(clientSide = BlightBuster.CLIENTPROXYLOCATION, serverSide = BlightBuster.COMMONPROXYLOCATION)
	public static CommonProxy proxy;

	public static BlightBuster instance;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        instance = this;
        untainter = new ItemUntainter();

    }
 
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        BlightbusterNetwork.init();
    }
 
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.registerRenderers();
    }
}
