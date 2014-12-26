package br.com.sdtd.helper.command;

import br.com.sdtd.helper.Player;

public class Ban extends DefaultCommand<Void> {
    private static final String CMD = "ban";
    
    private final Player player;
    private final int durationMinutes;
    
    public Ban(Player player, int durationMinutes) {
        this.player = player;
        this.durationMinutes = durationMinutes;
    }
    
    @Override
    public String name() {
        return "banimento";
    }
    
    @Override
    protected String getCommand() {
        return CMD + " " + this.player.getId() + " " + this.durationMinutes + " minutes";
    }

    @Override
    public boolean processData(String data) {
        setExecuted();
        return false;
    }

    @Override
    public Void getResult() {
        return null;
    }
    
}
