package com.coolspy3.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coolspy3.csmodloader.network.SubscribeToPacketStream;
import com.coolspy3.cspackets.datatypes.MCColor;
import com.coolspy3.cspackets.packets.ClientChatSendPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ListCommand
{

    private static final Logger logger = LoggerFactory.getLogger(ListCommand.class);

    public final String prefix;
    public final Pattern addPattern;
    public final Pattern removePattern;
    public final String listType;

    public ListCommand(String prefix, String listType)
    {
        this.prefix = prefix;
        addPattern = Pattern.compile(prefix + " add (.+)");
        removePattern = Pattern.compile(prefix + " remove (.+)");
        this.listType = "<" + listType + ">";
    }

    @SubscribeToPacketStream
    public boolean register(ClientChatSendPacket event)
    {
        String msg = event.msg;
        if (msg.matches(prefix + "( .*)?"))
        {
            try
            {
                if (msg.matches(prefix + " list( .*)?"))
                {
                    list();

                    return true;
                }

                if (msg.matches(prefix + " add( .*)?"))
                {
                    Matcher addMatcher = addPattern.matcher(msg);

                    if (addMatcher.matches())
                    {
                        String str = addMatcher.group(1);

                        if (validate(str))
                        {
                            add(str);

                            return true;
                        }
                    }

                    ModUtil.sendMessage(MCColor.RED + "Usage: " + prefix + " add " + listType);

                    return true;
                }
                if (msg.matches(prefix + " remove( .*)?"))
                {
                    Matcher removeMatcher = removePattern.matcher(msg);

                    if (removeMatcher.matches())
                    {
                        String str = removeMatcher.group(1);

                        if (validate(str))
                        {
                            remove(str);

                            return true;
                        }
                    }

                    ModUtil.sendMessage(MCColor.RED + "Usage: " + prefix + " remove " + listType);

                    return true;
                }

                ModUtil.sendMessage(
                        MCColor.RED + "Usage: " + prefix + " [add|remove|list] " + listType);
            }
            catch (IOException e)
            {
                logger.error("IOException in ListCommand implementation", e);
            }

            return true;
        }

        return false;
    }

    public abstract boolean validate(String str);

    public abstract void add(String str) throws IOException;

    public abstract void remove(String str) throws IOException;

    public abstract void list();

}
