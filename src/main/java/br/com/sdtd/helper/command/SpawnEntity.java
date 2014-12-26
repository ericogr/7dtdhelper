package br.com.sdtd.helper.command;

import br.com.sdtd.helper.telnet.Command;
import br.com.sdtd.helper.Player;

public class SpawnEntity implements Command<Void> {
    private static final String NAME = "SpawnEntity";
    private static final String CMD_GET_TIME = "spawnentity";
    
    private final String entityId;
    private final Player player;
    
    public SpawnEntity(String entityName, Player player) {
        this.entityId = entityName;
        this.player = player;
    }
    
    @Override
    public String getNextCommand() {
        return CMD_GET_TIME + " " + this.player.getId() + " " + this.entityId;
    }
    
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public boolean processData(String data) {
        return false;
    }

    @Override
    public Void getResult() {
        return null;
    }

    @Override
    public boolean reset() {
        return false;
    }
    
}