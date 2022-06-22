package cz.larkyy.aquaticlib.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class AquaticCommand extends BukkitCommand {

    private final String cmd;
    private final OnCommand onCommand;
    private final String permission;

    public AquaticCommand(String cmd, OnCommand onCommand, String permission) {
        super(cmd);
        this.cmd = cmd;
        this.onCommand = onCommand;
        this.permission = permission;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        onCommand.onCommand(sender, args);
        return false;
    }

    public String getPermission() {
        return permission;
    }

    public OnCommand getOnCommand() {
        return onCommand;
    }

    public String getCmd() {
        return cmd;
    }
}
