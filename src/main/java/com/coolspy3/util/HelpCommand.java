package com.coolspy3.util;

import java.util.HashMap;
import java.util.Map.Entry;

import com.coolspy3.csmodloader.network.SubscribeToPacketStream;
import com.coolspy3.cspackets.datatypes.MCColor;
import com.coolspy3.cspackets.packets.ClientChatSendPacket;

public class HelpCommand
{

    public static final String justABunchOfDashes = "-----------------------------";
    public final String trigger;
    protected final HashMap<String, String> helpInfo;

    public HelpCommand(String trigger)
    {
        this.trigger = trigger;
        helpInfo = new HashMap<>();
    }

    @SubscribeToPacketStream
    public boolean register(ClientChatSendPacket event)
    {
        if (event.msg.matches(trigger + "( .*)?"))
        {
            ModUtil.sendMessage(MCColor.BLUE + justABunchOfDashes);
            for (Entry<String, String> command : helpInfo.entrySet())
            {
                ModUtil.sendMessage(MCColor.YELLOW + command.getKey() + MCColor.AQUA + " - "
                        + command.getValue());
            }
            ModUtil.sendMessage(MCColor.BLUE + justABunchOfDashes);

            return true;
        }

        return false;
    }

    protected void addCommand(String command, String description)
    {
        helpInfo.put(command, description);
    }

}
