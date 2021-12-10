package com.coolspy3.util;

import com.coolspy3.csmodloader.network.PacketHandler;
import com.coolspy3.cspackets.packets.ServerChatSendPacket;

public class ModUtil
{

    public static void executeAsync(Runnable function)
    {
        Thread thread = new Thread(function);
        thread.setDaemon(true);
        thread.start();
    }

    private static final byte defaultChatPosition = 0x00;

    public static void sendMessage(String msg)
    {
        PacketHandler.getLocal().sendPacket(new ServerChatSendPacket(msg, defaultChatPosition));
    }

}
