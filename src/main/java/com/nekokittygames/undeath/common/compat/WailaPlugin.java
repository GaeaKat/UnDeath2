package com.nekokittygames.undeath.common.compat;

import com.nekokittygames.undeath.common.entities.EntityPlayerPigZombie;
import com.nekokittygames.undeath.common.entities.EntityPlayerSkellington;
import com.nekokittygames.undeath.common.entities.EntityPlayerSlime;
import com.nekokittygames.undeath.common.entities.EntityPlayerZombie;
//import mcp.mobius.waila.api.IWailaPlugin;
//import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by katsw on 31/05/2017.


@mcp.mobius.waila.api.WailaPlugin("undeath")
public class WailaPlugin implements IWailaPlugin {
    @Override
    public void register(IWailaRegistrar iWailaRegistrar) {
        WailaHUD hud=new WailaHUD();
        iWailaRegistrar.registerBodyProvider(hud, EntityPlayerZombie.class);
        iWailaRegistrar.registerBodyProvider(hud, EntityPlayerPigZombie.class);
        iWailaRegistrar.registerBodyProvider(hud, EntityPlayerSkellington.class);
        iWailaRegistrar.registerBodyProvider(hud, EntityPlayerSlime.class);
    }
}
*/