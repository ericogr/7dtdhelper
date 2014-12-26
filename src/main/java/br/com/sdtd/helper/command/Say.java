package br.com.sdtd.helper.command;

/**
 *
 * @author erico
 */
public class Say extends DefaultCommand<Void> {
    private static final String CMD = "say";
    private final String mensagem;
    
    public Say(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    protected String getCommand() {
        return CMD + " " + this.mensagem;
    }

    @Override
    public String name() {
        return "Say";
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