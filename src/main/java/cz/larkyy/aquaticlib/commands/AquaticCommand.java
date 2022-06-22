package cz.larkyy.aquaticlib.commands;

public class AquaticCommand {

    private final String cmd;
    private final OnCommand onCommand;
    private final String permission;

    public AquaticCommand(String cmd, OnCommand onCommand, String permission) {
        this.cmd = cmd;
        this.onCommand = onCommand;
        this.permission = permission;
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
