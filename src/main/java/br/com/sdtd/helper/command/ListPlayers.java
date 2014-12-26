package br.com.sdtd.helper.command;

import br.com.sdtd.helper.Player;
import java.util.ArrayList;
import java.util.List;

public final class ListPlayers extends DefaultCommand<List<Player>> {

    private static final String END_DATA = "Total of";
    private static final String CMD_LIST_PLAYERS = "lp";

    private final List<Player> players;

    public ListPlayers() {
        this.players = new ArrayList<>();
    }

    @Override
    public String name() {
        return "ListPlayers";
    }

    @Override
    protected String getCommand() {
        return CMD_LIST_PLAYERS;
    }

    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public final boolean processData(String data) {
        setExecuted();

        //TODO: please, use regex!
        String textToFind = this.players.size() + 1 + ". id=";
        int posSta = data.indexOf(textToFind);
        if (posSta != -1) {
            //id
            int posEnd = data.indexOf(",");
            int id = Integer.parseInt(data.substring(posSta + textToFind.length(), posEnd));

            //score
            textToFind = ", score=";
            posSta = data.indexOf(textToFind) + textToFind.length();
            posEnd = data.indexOf(", ", posSta);
            int score = Integer.parseInt(data.substring(posSta, posEnd));

            //steamid
            textToFind = ", steamid=";
            posSta = data.indexOf(textToFind) + textToFind.length();
            posEnd = data.indexOf(", ", posSta);
            String steamId = data.substring(posSta, posEnd);

            //ip
            textToFind = ", ip=";
            posSta = data.indexOf(textToFind) + textToFind.length();
            posEnd = data.indexOf(", ", posSta);
            String ipAddress = data.substring(posSta, posEnd);

            //ping
            textToFind = ", ping=";
            posSta = data.indexOf(textToFind) + textToFind.length();
            posEnd = data.length();
            int ping = Integer.parseInt(data.substring(posSta, posEnd));

            this.players.add(new Player(id, steamId, score, ipAddress, ping));
        } 
        else if (data.startsWith(END_DATA)) {
            return false;
        }

        return true;
    }

    @Override
    public List<Player> getResult() {
        return this.players;
    }

}
