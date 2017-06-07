package com.nekokittygames.undeath.common.network;

import com.nekokittygames.undeath.common.IUndeadEntity;
import com.nekokittygames.undeath.common.entities.components.InventoryComponent;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

/**
 * Created by katsw on 07/06/2017.
 */
public class InventoryPacket implements IMessage{

    InventoryComponent inv;
    int entityID;

    public InventoryPacket(InventoryComponent inv,int entityID)
    {
        this.entityID=entityID;
        this.inv=inv;
    }
    public InventoryPacket()
    {
        inv=new InventoryComponent();
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        try {
            PacketBuffer buf = new PacketBuffer(byteBuf);
            entityID=buf.readInt();
            for (int i = 0; i < inv.getInventory().size(); i++) {

                inv.getInventory().set(i, buf.readItemStack());

            }
            for (int i = 0; i < inv.getArmor().size(); i++) {

                inv.getArmor().set(i, buf.readItemStack());

            }
            for (int i = 0; i < inv.getOffhand().size(); i++) {

                inv.getOffhand().set(i, buf.readItemStack());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        PacketBuffer buf=new PacketBuffer(byteBuf);

        try {
            buf.writeInt(entityID);
            for (int i = 0; i < inv.getInventory().size(); i++) {

                buf.writeItemStack(inv.getInventory().get(i));

            }
            for (int i = 0; i < inv.getArmor().size(); i++) {

                buf.writeItemStack(inv.getArmor().get(i));

            }
            for (int i = 0; i < inv.getOffhand().size(); i++) {

                buf.writeItemStack(inv.getOffhand().get(i));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
