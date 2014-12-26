package br.com.sdtd.helper.command;

import br.com.sdtd.helper.telnet.Command;

/**
 *
 * @author erico
 */
public abstract class DefaultCommand<R> implements Command<R> {
    private boolean firstTime;
    
    //TODO: add a default implementation for String name(); (using reflection)
    
    public DefaultCommand() {
        this.firstTime = true;
    }
    
    protected boolean isFirstTime() {
        return this.firstTime;
    }
    
    protected void setExecuted() {
        this.firstTime = false;
    }
    
    protected abstract String getCommand();
    
    @Override
    public String getNextCommand() {
        return isFirstTime() ? getCommand() : "";
    }
    
    @Override
    public boolean reset() {
        return true;
    }
}
