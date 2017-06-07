package com.nekokittygames.undeath.common;

import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * Created by katsw on 31/05/2017.
 */
@net.minecraftforge.common.config.Config(modid = Undeath.MOD_ID)
@net.minecraftforge.common.config.Config.LangKey("undeath.config.title")
public class Config {

    @net.minecraftforge.common.config.Config.Comment("Chance of a zombie being spawned on death by zombie")
    @net.minecraftforge.common.config.Config.RangeInt(min = 0,max = 100)
    @net.minecraftforge.common.config.Config.LangKey("undeath.general.zombieChance")
    public static int zombieChance=5;

    @net.minecraftforge.common.config.Config.Comment("Chance of a skeleton being spawned on death by skeleton")
    @net.minecraftforge.common.config.Config.RangeInt(min = 0,max = 100)
    @net.minecraftforge.common.config.Config.LangKey("undeath.general.skeletonChance")
    public static int skeletonChance=5;

    @net.minecraftforge.common.config.Config.Comment("Chance of a Zombie Pigman being spawned on death by zombie pigman")
    @net.minecraftforge.common.config.Config.RangeInt(min = 0,max = 100)
    @net.minecraftforge.common.config.Config.LangKey("undeath.general.zombiePigmanChance")
    public static int zombiePigmanChance=5;

    @net.minecraftforge.common.config.Config.Comment("Do corrupted undead use your equipment?")
    @net.minecraftforge.common.config.Config.LangKey("undeath.general.useEquipment")
    public static boolean useEquipment=false;


    @net.minecraftforge.common.config.Config.Comment("Do corrupted undead hold your inventory when you die? Also do player slimes spawn")
    @net.minecraftforge.common.config.Config.LangKey("undeath.general.deathChest")
    public static boolean deathChest=false;

    @Mod.EventBusSubscriber
    private static class EventHandler {

        /**
         * Inject the new values and save to the config file when the config has been changed from the GUI.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Undeath.MOD_ID)) {
                ConfigManager.sync(Undeath.MOD_ID, net.minecraftforge.common.config.Config.Type.INSTANCE);
            }
        }
    }
}
