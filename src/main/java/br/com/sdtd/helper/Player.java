package br.com.sdtd.helper;

/**
 *
 * @author erico
 */
public final class Player {
    private final int id;
    private final String steamId;
    private String ipAddress;
    private int ping;
    private int score;
    
    public Player(int id, String steamId) {
        this(id, steamId, 0, "", 0);
    }
    
    public Player(int id, String steamId, int score, String ipAddress, int ping) {
        this.id = id;
        this.steamId = steamId;
        this.score = score;
        this.ipAddress = ipAddress;
        this.ping = ping;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getSteamId() {
        return this.steamId;
    }
    
    public int getScore() {
        return this.score;
    }
    
    public String getIPAddress() {
        return this.ipAddress;
    }
    
    public int getPing() {
        return this.ping;
    }
    
    @Override
    public String toString() {
        return "player id=" + this.id + " steamId: " + this.steamId;
    }
}
