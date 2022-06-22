package cz.larkyy.aquaticlib.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

public class CommandManager {

    public static void registerCommand(AquaticCommand command) {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());

            command.setAliases(command.getAliases());
            command.setPermission(command.getPermission());

            commandMap.register(command.getCmd(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
