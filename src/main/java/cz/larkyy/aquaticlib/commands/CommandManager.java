package cz.larkyy.aquaticlib.commands;

public interface CommandManager {

    AquaticCommand createCommand(String cmd);

    AquaticCommand addEvent(CommandEvent event);

}
