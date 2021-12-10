package com.coolspy3.util;

import com.coolspy3.csmodloader.mod.Entrypoint;
import com.coolspy3.csmodloader.mod.Mod;
import com.coolspy3.csmodloader.network.PacketHandler;
import com.coolspy3.csmodloader.network.SubscribeToPacketStream;
import com.coolspy3.cspackets.packets.GameJoinPacket;


@Mod(id = "csutils", name = "CSUtils", version = "1.0.0",
        description = "Adds utility functions for use by other mods",
        dependencies = {"csmodloader:[1.0.0,2)", "cspackets:[1.0.0,2]"})
public class CoolSpyLib implements Entrypoint
{

    private boolean isLoggedIn = false;

    @Override
    public Entrypoint create()
    {
        return new CoolSpyLib();
    }

    @Override
    public void init(PacketHandler handler)
    {
        handler.register(this);
    }

    @SubscribeToPacketStream
    public void onJoinGame(GameJoinPacket packet)
    {
        if (isLoggedIn) return;

        isLoggedIn = true;
        PacketHandler.getLocal().dispatch(new ServerJoinEvent());
    }
}