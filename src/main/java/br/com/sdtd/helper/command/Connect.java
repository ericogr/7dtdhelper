package br.com.sdtd.helper.command;

import br.com.sdtd.helper.telnet.Command;

public class Connect implements Command<Boolean> {
    private enum Step {UNKNOWN, ENTER_PASSWORD};
    private static final String MSG_ENTER_PASSWORD = "Please enter password:";
    private static final String MSG_PASSWORD_CORRECT = "Logon successful.";
    private static final String MSG_PASSWORD_WRONG = "Password incorrect, please enter password:";

    private final String password;
    private Step step;
    private Boolean connected;

    public Connect(String password) {
        this.password = password;
        this.step = Step.UNKNOWN;
    }
    
    @Override
    public String name() {
        return "Connect";
    }
    
    @Override
    public boolean reset() {
        return false;
    }

    @Override
    public String getNextCommand() {
        switch(this.step) {
            case UNKNOWN:
                return "";
            case ENTER_PASSWORD:
                return this.password;
            default:
                return "";
        }
    }

    @Override
    public boolean processData(String data) {
        switch (data.trim()) {
            case MSG_ENTER_PASSWORD:
                this.step = Step.ENTER_PASSWORD;
                break;
            case MSG_PASSWORD_CORRECT:
                this.connected = Boolean.TRUE;
                return false;
            case MSG_PASSWORD_WRONG:
                this.connected = Boolean.FALSE;
                return false;
        }
        
        return true;
    }

    @Override
    public Boolean getResult() {
        return this.connected;
    }

}
