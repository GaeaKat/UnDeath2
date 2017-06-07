package com.nekokittygames.undeath.common;

import com.nekokittygames.undeath.common.entities.EntityPlayerSlime;
import com.nekokittygames.undeath.common.entities.EntityPlayerZombie;
import com.nekokittygames.undeath.common.network.InventoryPacket;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

/**
 * Created by katsw on 24/05/2017.
 */
public class Trackers {

    public Trackers()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }


    public void onDeathLoot(PlayerDropsEvent drops)
    {
        //if(Config.deathChest)
    }

    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent e) {
        if (!e.getWorld().isRemote && e.getEntity() instanceof EntityPlayerSlime) {
            InventoryPacket packet = new InventoryPacket(((IUndeadEntity) e.getEntity()).getInventoryComponent(), e.getEntity().getEntityId());
            Undeath.NETWORK_WRAPPER.sendToAll(packet);//, (EntityPlayerMP) e.getEntityPlayer());
        }
    }

    @SubscribeEvent
    public void playerStartedTracking(PlayerEvent.StartTracking e) {
        if (!e.getTarget().world.isRemote && e.getTarget() instanceof EntityPlayerSlime) {
            InventoryPacket packet = new InventoryPacket(((IUndeadEntity) e.getTarget()).getInventoryComponent(), e.getTarget().getEntityId());
            Undeath.NETWORK_WRAPPER.sendTo(packet, (EntityPlayerMP) e.getEntityPlayer());
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer player= (EntityPlayer) event.getEntity();
            Random rand=new Random();
            int chance=rand.nextInt(100);
            if( chance<=Config.zombieChance && event.getSource().getSourceOfDamage() instanceof EntityZombie )
            {
                MainUtils.makeEntity(EnumCreatureType.ZOMBIE,event.getEntity().world,player.getPosition(),player);
                return;
            }
            if(chance <=Config.skeletonChance && event.getSource().getEntity() instanceof AbstractSkeleton)
            {
                MainUtils.makeEntity(EnumCreatureType.SKELLINGTON,event.getEntity().world,player.getPosition(),player);
                return;
            }

            if(chance <=Config.zombiePigmanChance&& event.getSource().getEntity() instanceof EntityPigZombie)
            {
                MainUtils.makeEntity(EnumCreatureType.PIGZOMBIE,event.getEntity().world,player.getPosition(),player);
                return;
            }
        }
    }
}
