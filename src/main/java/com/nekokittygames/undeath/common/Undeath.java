    package com.nekokittygames.undeath.common;

import com.nekokittygames.undeath.common.entities.EntityPlayerPigZombie;
import com.nekokittygames.undeath.common.entities.EntityPlayerSkellington;
import com.nekokittygames.undeath.common.entities.EntityPlayerSlime;
import com.nekokittygames.undeath.common.entities.EntityPlayerZombie;
import com.nekokittygames.undeath.common.items.ModItems;
import com.nekokittygames.undeath.common.network.InventoryHandler;
import com.nekokittygames.undeath.common.network.InventoryPacket;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

    @Mod(
        modid = Undeath.MOD_ID,
        name = Undeath.MOD_NAME,
        version = Undeath.VERSION
)
public class Undeath {

    public static final String MOD_ID = "undeath";
    public static final String MOD_NAME = "Undeath";
    public static final String VERSION = "1.0.0";
    public static final SimpleNetworkWrapper NETWORK_WRAPPER= NetworkRegistry.INSTANCE.newSimpleChannel("undeath");
    @SidedProxy(serverSide = "com.nekokittygames.undeath.common.CommonProxy",clientSide = "com.nekokittygames.undeath.client.ClientProxy")
    public static CommonProxy proxy;
    public static Logger logger;
    private static Trackers trackers;

    public static Config config;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        trackers=new Trackers();
        logger=event.getModLog();
        int id=99;
        EntityRegistry.registerModEntity(new ResourceLocation("undeath","playerZombie"), EntityPlayerZombie.class,"undeath.playerZombie",id++,this,64,1,true);
        EntityRegistry.registerModEntity(new ResourceLocation("undeath","playerSkell"), EntityPlayerSkellington.class,"undeath.playerSkell",id++,this,64,1,true);
        EntityRegistry.registerModEntity(new ResourceLocation("undeath","playerPig"), EntityPlayerPigZombie.class,"undeath.playerPig",id++,this,64,1,true);
        EntityRegistry.registerModEntity(new ResourceLocation("undeath","playerSlime"), EntityPlayerSlime.class,"undeath.playerSlime",id++,this,64,1,true);
        NETWORK_WRAPPER.registerMessage(InventoryHandler.class,InventoryPacket.class,0, Side.CLIENT);
        proxy.registerRenderers();

    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerItemRenderer();

    }

}
