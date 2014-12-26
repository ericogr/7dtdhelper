package br.com.sdtd.helper.ban.validation;

import br.com.sdtd.helper.Country;
import br.com.sdtd.helper.Player;
import br.com.sdtd.helper.ban.BanPlayersException;
import br.com.sdtd.helper.ban.BannedCountriesSetup;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelizeCountryPlayerValidation implements PlayerValidation {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String URL_QUERY_LIST = "http://www.telize.com/geoip/";
    private static final String UNKNOWN_COUNTRY_CODE = "XX";
    
    private final BannedCountriesSetup bannedCountries;
    private final Map<String, Country> countryCache;

    public TelizeCountryPlayerValidation(BannedCountriesSetup bannedCountries) {
        this.bannedCountries = bannedCountries;
        this.countryCache = new HashMap<>();
    }
    
    @Override
    public boolean isValid(Player player) {
        try {
            Country country = getCountryCode(player.getIPAddress());
            
            log.debug("Player {} in country code {}", player.getSteamId(), country.getCountryCode());
            
            return !this.bannedCountries.existCountry(country);
        }
        catch (IOException ex) {
            throw new BanPlayersException(ex);
        }
    }
    
    private Country getCountryCode(String ipAddress) throws IOException {
        Country country = getCountryCodeFromCache(ipAddress);
        
        if (country == null) {
            String countryCode = httpDataCountryCode(getHttpData(ipAddress));
            country = new Country(countryCode);
            this.countryCache.put(ipAddress, country);
            
            log.debug("Country {} added in cache for ip ", country, ipAddress);
        }
        
        return country;
    }
    
    private Country getCountryCodeFromCache(String ipAddress) {
        return this.countryCache.get(ipAddress);
    }
    
    private String getHttpData(String ipAddress) throws IOException {
        URL url = new URL(URL_QUERY_LIST + ipAddress);
        HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
        urlConn.setReadTimeout(10000);
        
        log.debug("Query ip address country {}", url.toString());
        if (urlConn.getResponseCode() == 200) {
            try (LineNumberReader lnr = new LineNumberReader(new InputStreamReader(urlConn.getInputStream(), Charset.forName("UTF-8")))) {
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
    
    private String httpDataCountryCode(String httpData) {
        String countryAbbr = "\",\"country_code\":\"";
        int start = httpData.indexOf(countryAbbr);
        
        if (start == -1) {
            log.error("No valid country found for http data: {}", httpData);
            return UNKNOWN_COUNTRY_CODE;
        }
        
        start += countryAbbr.length();
        int end = httpData.indexOf("\",\"", start);
        String code = httpData.substring(start, end);
        
        return code;
    }
    
}
