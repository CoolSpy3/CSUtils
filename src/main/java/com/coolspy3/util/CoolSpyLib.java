package com.coolspy3.util;

import com.coolspy3.csmodloader.mod.Entrypoint;
import com.coolspy3.csmodloader.mod.Mod;
import com.coolspy3.csmodloader.network.PacketHandler;
import com.coolspy3.csmodloader.network.SubscribeToPacketStream;
import com.coolspy3.cspackets.datatypes.MCColor;
import com.coolspy3.cspackets.packets.GameJoinPacket;
import com.coolspy3.cspackets.packets.ServerChatSendPacket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Mod(id = "csutils", name = "CSUtils", version = "1.1.2",
        description = "Adds utility functions for use by other mods",
        dependencies = {"csmodloader:[1.3.1,2)", "cspackets:[1.2.1,2)"})
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

    @SubscribeToPacketStream
    public void onChatReceived(ServerChatSendPacket packet)
    {
        if (packet.position == 0x00)
            PacketHandler.getLocal().dispatch(new ClientChatReceiveEvent(MCColor.stripFormatting(
                    recursivelyReadChat(JsonParser.parseString(packet.msg).getAsJsonObject()))));
    }

    public static final String recursivelyReadChat(JsonObject chat)
    {
        StringBuilder message = new StringBuilder(chat.get("text").getAsString());

        if (chat.has("extra")) for (JsonElement component : chat.get("extra").getAsJsonArray())
            message.append(recursivelyReadChat(component.getAsJsonObject()));

        return message.toString();
    }
}
