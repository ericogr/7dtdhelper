package br.com.sdtd.helper;

import java.util.Objects;

public final class User {
    private final String steamId;
    
    public User(String steamId) {
        if (steamId.length() != 17) {
            throw new IllegalArgumentException("Invalid steamId: " + steamId);
        }
        
        this.steamId = steamId;
    }
    
    public String getSteamId() {
        return this.steamId;
    }
    
    @Override
    public int hashCode() {
        return this.steamId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        
        return Objects.equals(this.steamId, other.steamId);
    }
}