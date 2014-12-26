package br.com.sdtd.helper.ban.validation;

import br.com.sdtd.helper.Player;
import br.com.sdtd.helper.User;
import br.com.sdtd.helper.ban.BanPlayersException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CursedEarthPlayerValidation implements PlayerValidation {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String URL_QUERY_LIST = "http://www.cursedearth.us/modules.php?name=Bans&pagenum=";
    private static final int RESULTS_PER_PAGE = 50;
    
    private static final int NEXT_RETRY_MILLS = 1000 * 60 * 30;
    private static final int DEFAULT_REFRESH_MILLS = 1000 * 60 * 60;
    private static final int HTTP_TIMEOUT = 10000;
    private final long timeMillsCacheTime;
    private long lastUpdate;
    private List<User> bannedUsers;
    
    public CursedEarthPlayerValidation() {
        this(DEFAULT_REFRESH_MILLS);
    }
    
    public CursedEarthPlayerValidation(int timeMillsCacheTime) {
        this.timeMillsCacheTime = timeMillsCacheTime;
        this.lastUpdate = 0;
        this.bannedUsers = Collections.EMPTY_LIST;
    }
    
    @Override
    public boolean isValid(Player player) {
        List<User> cachedBannedUsers = getCachedBannedUsers();
        
        return !cachedBannedUsers.stream()
                                 .filter(bannedUser -> player.getSteamId().equals(bannedUser.getSteamId()))
                                 .findFirst()
                                 .isPresent();
    }
    
    private List<User> getCachedBannedUsers() {
        if (lastUpdate + timeMillsCacheTime < System.currentTimeMillis()) {
            try {
                this.bannedUsers = getBannedUsers();
                this.lastUpdate = System.currentTimeMillis();
            }
            catch (Exception ex) {
                this.lastUpdate += NEXT_RETRY_MILLS;
                
                log.error("Error getting banned users list: " + ex.getMessage(), ex);
                log.info("Using list from cache with {} banned players", this.bannedUsers.size());
            }
        }
        
        return this.bannedUsers;
    }
    
    private List<User> getBannedUsers() {
        List<User> allBannedIds = new ArrayList<>();
        
        try {
            String firstPageData = getHttpData(1);
            int totalIds = httpDataToNumberOfPages(firstPageData);
            int pages = totalIds / RESULTS_PER_PAGE + (totalIds % RESULTS_PER_PAGE > 0 ? 1 : 0);
            
            allBannedIds.addAll(httpDataToBannedSteamIds(firstPageData));

            for (int i = 2; i <= pages; i++) {
                String pageData = getHttpData(i);
                List<User> ids = httpDataToBannedSteamIds(pageData);
                
                allBannedIds.addAll(ids);
            }
            
            log.info("Ban list from cursed earth loaded with {} players", allBannedIds.size());
            
            return allBannedIds;
        }
        catch (IOException ex) {
            throw new BanPlayersException(ex);
        }
    }
    
    private String getHttpData(int page) throws IOException {
        URL url = new URL(URL_QUERY_LIST + page);
        HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
        urlConn.setReadTimeout(HTTP_TIMEOUT);
        
        log.debug("Query banned list " + url.toString());
        if (urlConn.getResponseCode() == 200) {
            try (LineNumberReader lnr = new LineNumberReader(new InputStreamReader(urlConn.getInputStream(), Charset.forName("ISO-8859-1")))) {
                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = lnr.readLine()) != null) {
                    sb.append(line);
                }

                return sb.toString();
            }
        }
        
        throw new IOException("Error reading from " + urlConn.getResponseMessage());
    }
    
    private int httpDataToNumberOfPages(String httpData) {
        String currentBans = "Current Bans: ";
        int start = httpData.indexOf(currentBans);
        
        if (start == -1) {
            throw new BanPlayersException("No valid data found for current number of pages!");
        }
        
        start += currentBans.length();
        int end = httpData.indexOf("&nbsp;", start);
        String banned = httpData.substring(start, end);
        
        return Integer.parseInt(banned);
    }
    
    private List<User> httpDataToBannedSteamIds(String httpData) {
        String steamId = "modules.php?name=Bans&page=player&id=";
        List<User> players = new ArrayList<>();
        int start = 0;
        
        while((start = httpData.indexOf(steamId, start)) != -1) {
            start += steamId.length();
            int end = httpData.indexOf("\"", start);
            
            String id = httpData.substring(start, end);
            
            players.add(new User(id));
        }
        
        return players;
    }
    
}