package br.com.sdtd.helper.ban;

import br.com.sdtd.helper.Country;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileInputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BannedCountriesSetup {
    private static final Logger log = LoggerFactory.getLogger(BannedCountriesSetup.class);
    private static final String BANNED_COUNSTRIES_FILE = "banned-countries.xml";
    
    private final List<Country> countries;
    private final String kickMessage;
    
    private BannedCountriesSetup(List<Country> countries, String kickMessage) {
        this.countries = countries;
        this.kickMessage = kickMessage;
    }
    
    public List<Country> getBannedCountries() {
        return this.countries;
    }
    
    public String getKickMessage() {
        return kickMessage;
    }

    public int size() {
        return countries.size();
    }
    
    public boolean existCountry(Country ucountry) {
        return countries.stream()
                .filter(country -> ucountry.equals(country))
                .findFirst()
                .isPresent();
    }
    
    public static BannedCountriesSetup load() {
        log.debug("Reading banned countries list from xml {}.", BANNED_COUNSTRIES_FILE);
        
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("BannedCountries", BannedCountriesSetup.class);
        xstream.alias("country", Country.class);
        
        try {
            BannedCountriesSetup bannedCountries = (BannedCountriesSetup)xstream.fromXML(new FileInputStream(BANNED_COUNSTRIES_FILE));
            
            log.info("Banned countries list loaded with {} countries.", bannedCountries.size());
            
            return bannedCountries;
        }
        catch (Exception ex) {
            throw new VipUsersException("error loading " + BANNED_COUNSTRIES_FILE, ex);
        }
    }

}
