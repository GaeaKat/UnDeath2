package com.nekokittygames.undeath.common.network;

import com.nekokittygames.undeath.common.IUndeadEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by katsw on 07/06/2017.
 */
@SideOnly(Side.CLIENT)
public class InventoryHandler implements IMessageHandler<InventoryPacket,IMessage>
{

    public InventoryHandler()
    {

    }
    @Override

    public IMessage onMessage(final InventoryPacket inventoryPacket, final MessageContext messageContext) {
        Minecraft.getMinecraft().addScheduledTask(new Runnable() {
            @Override
            public void run() {
                Entity ent=Minecraft.getMinecraft().world.getEntityByID(inventoryPacket.entityID);
                if(ent instanceof IUndeadEntity)
                {
                    IUndeadEntity undead= (IUndeadEntity) ent;
                    for(int i=0;i<inventoryPacket.inv.getInventory().size();i++)
                    {
                        undead.getInventoryComponent().getInventory().set(i,inventoryPacket.inv.getInventory().get(i));
                    }
                    for(int i=0;i<inventoryPacket.inv.getArmor().size();i++)
                    {
                        undead.getInventoryComponent().getArmor().set(i,inventoryPacket.inv.getArmor().get(i));
                    }
                    for(int i=0;i<inventoryPacket.inv.getOffhand().size();i++)
                    {
                        undead.getInventoryComponent().getOffhand().set(i,inventoryPacket.inv.getOffhand().get(i));
                    }
                }
            }
        });
        return null;
    }
}
