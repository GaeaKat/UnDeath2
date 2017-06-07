package com.nekokittygames.undeath.common.items;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by katsw on 25/05/2017.
 */
@Mod.EventBusSubscriber
public class ModItems {

    public static Item spawner;


    public static void initItems()
    {
        spawner=new ItemSpawnUndead();

    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        ModItems.initItems();
        event.getRegistry().registerAll(spawner);

    }
}
