package cz.larkyy.aquaticlib.commands;

public class CommandBuilder {

    private String cmd;
    private OnCommand onCommand;
    private String permission;

    public CommandBuilder(String cmd) {
        this.cmd = cmd;
    }

    public CommandBuilder setOnCommand(OnCommand onCommand) {
        this.onCommand = onCommand;
        return this;
    }

    public CommandBuilder setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public AquaticCommand build() {
        return new AquaticCommand(cmd, onCommand, permission);
    }


}
