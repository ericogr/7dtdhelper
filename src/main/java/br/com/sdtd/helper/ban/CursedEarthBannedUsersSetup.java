package br.com.sdtd.helper.ban;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CursedEarthBannedUsersSetup {
    private static final Logger log = LoggerFactory.getLogger(CursedEarthBannedUsersSetup.class);
    private static final String FILE = "cursed-earth-banned-users.xml";
    
    private final String kickMessage;
    
    private CursedEarthBannedUsersSetup(String kickMessage) {
        this.kickMessage = kickMessage;
    }
    
    public String getKickMessage() {
        return kickMessage;
    }

    public static CursedEarthBannedUsersSetup load() {
        log.debug("Reading from xml {}.", FILE);
        
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("bannedUsers", CursedEarthBannedUsersSetup.class);
        
        try {
            CursedEarthBannedUsersSetup options = (CursedEarthBannedUsersSetup)xstream.fromXML(new FileInputStream(FILE));
            
            log.debug("File {} loaded.", FILE);
            
            return options;
        }
        catch (Exception ex) {
            throw new VipUsersException("error loading " + FILE, ex);
        }
    }
}