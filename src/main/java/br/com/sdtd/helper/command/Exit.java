package br.com.sdtd.helper.command;

import br.com.sdtd.helper.telnet.Command;

/**
 *
 * @author erico
 */
public class Exit implements Command<Void> {
    private static final String CMD_EXIT = "exit";
    private boolean firstTime;
    
    public Exit() {
        this.firstTime = true;
    }
    
    @Override
    public String name() {
        return "Exit";
    }
    
    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public String getNextCommand() {
        return firstTime ? CMD_EXIT : "";
    }

    @Override
    public boolean processData(String data) {
        this.firstTime = false;
        
        return data != null;
    }

    @Override
    public Void getResult() {
        return null;
    }
}
