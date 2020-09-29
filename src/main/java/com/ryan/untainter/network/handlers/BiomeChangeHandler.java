package com.ryan.untainter.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.world.biome.BiomeGenBase;
import com.ryan.untainter.network.BlightbusterNetwork;
import com.ryan.untainter.network.packets.BiomeChangePacket;
import thaumcraft.common.Thaumcraft;

public class BiomeChangeHandler implements IMessageHandler<BiomeChangePacket, IMessage> {
    @Override
    public IMessage onMessage(BiomeChangePacket message, MessageContext ctx) {
        BlightbusterNetwork.setBiomeAt(Thaumcraft.proxy.getClientWorld(), message.getX(), message.getZ(), BiomeGenBase.getBiome(message.getBiomeID()));
        return null;
    }
}
