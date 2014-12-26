package br.com.sdtd.helper.command;

import br.com.sdtd.helper.Day;

public class GetTime extends DefaultCommand<Day> {
    private static final String NAME = "GetTime";
    private static final String CMD_GET_TIME = "gt";
    private Day day;
    
    @Override
    public String name() {
        return NAME;
    }
    
    @Override
    protected String getCommand() {
        return CMD_GET_TIME;
    }
    
    @Override
    public boolean processData(String data) {
        setExecuted();
        
        //TODO: please, use regex!
        
        String textToFind = "Day ";
        int posIni = data.indexOf(textToFind);
        if (posIni != -1) {
            int posEnd = data.indexOf(",");
            int d = Integer.parseInt(data.substring(posIni + textToFind.length(), posEnd));
            
            posIni = posEnd + 2;
            posEnd = data.indexOf(":", posIni);
            
            int h = Integer.parseInt(data.substring(posIni, posEnd));
            
            posIni = posEnd + 1;
            posEnd = data.length() -1;
            
            int m = Integer.parseInt(data.substring(posIni, posEnd));
            
            this.day = new Day(d, h, m);
            
            return false;
        }
        
        return true;
    }

    @Override
    public Day getResult() {
        return this.day;
    }
    
}
