package com.coolspy3.util;

import com.coolspy3.csmodloader.network.packet.Packet;

public class ClientChatReceiveEvent extends Packet
{

    public final String msg;

    public ClientChatReceiveEvent(String msg)
    {
        this.msg = msg;
    }

    @Override
    public Object[] getValues()
    {
        return null;
    }

}
