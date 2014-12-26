package br.com.sdtd.helper.command;

import br.com.sdtd.helper.Player;

/**
 *
 * @author erico
 */
public class Kick extends DefaultCommand<Boolean> {
    private static final String CMD_KICK = "kick";
    private static final String MSG_KICK_OK = "Kicking Player ";
    private static final String MSG_KICK_ERROR = "No players found with name containing or player id of ";
    
    private final Player player;
    private final String reason;
    
    private Boolean kicked;
    
    public Kick(Player player, String reason) {
        this.player = player;
        this.reason = reason;
        this.kicked = Boolean.FALSE;
    }
    
    @Override
    public String name() {
        return "Kick";
    }
    
    @Override
    protected String getCommand() {
        return CMD_KICK + " " + this.player.getId() + " " + this.reason;
    }

    @Override
    public boolean processData(String data) {
        setExecuted();
        
        //TODO: please, use regex!
        
        if (data.startsWith(MSG_KICK_OK)) {
            this.kicked = Boolean.TRUE;
            return false;
        }
        else if(data.startsWith(MSG_KICK_ERROR)) {
            this.kicked = Boolean.FALSE;
            return false;
        }
        
        return true;
    }

    @Override
    public Boolean getResult() {
        return this.kicked;
    }
    
}
