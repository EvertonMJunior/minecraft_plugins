package me.everton.jpvp.utils;

import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Method;
import java.util.Collection;

public class MojangUtils{

    public volatile static Collection<WrappedSignedProperty> properties;

    public static Object getSessionService()
    {
        Server server = Bukkit.getServer();
        try
        {
            Object mcServer = server.getClass().getDeclaredMethod("getServer").invoke(server);
            for (Method m : mcServer.getClass().getMethods())
            {
                if (m.getReturnType().getSimpleName().equalsIgnoreCase("MinecraftSessionService"))
                {
                    return m.invoke(mcServer);
                }
            }
        }
        catch (Exception ex)
        {
            throw new IllegalStateException("An error occurred while trying to get the session service", ex);
        }
        throw new IllegalStateException("No session service found :o");
    }

    public static Method getFillMethod(Object sessionService)
    {
        for(Method m : sessionService.getClass().getDeclaredMethods())
        {
            if(m.getName().equals("fillProfileProperties"))
            {
                return m;
            }
        }
        throw new IllegalStateException("No fillProfileProperties method found in the session service :o");
    }
}