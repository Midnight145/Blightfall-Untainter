package com.ryan.untainter;


import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import cpw.mods.fml.client.registry.RenderingRegistry;


public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenderers() 
    {
    	super.registerRenderers();	
        

    }

    @Override
    public double getBestCleanseSpawnHeight() {
        return Minecraft.getMinecraft().thePlayer.posY;
    }
}
