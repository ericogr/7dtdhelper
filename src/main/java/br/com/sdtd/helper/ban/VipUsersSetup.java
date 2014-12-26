package br.com.sdtd.helper.ban;

import br.com.sdtd.helper.User;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VipUsersSetup {
    private static final Logger log = LoggerFactory.getLogger(VipUsersSetup.class);
    private static final String FILE = "vip-users.xml";
    
    private int slotsAvailable;
    private int banTimeMinutes;
    private String serverFullKickMessage;
    private List<User> users;
    
    private VipUsersSetup() {
    }
    
    public List<User> getVipUsers() {
        return users;
    }
    
    public int getSlotsAvailable() {
        return slotsAvailable;
    }

    public String getServerFullKickMessage() {
        return serverFullKickMessage;
    }
    
    public int getBanTimeMinutes() {
        return this.banTimeMinutes;
    }

    public int size() {
        return this.users.size();
    }
    
    public boolean existUserBySteamId(String steamId) {
        Optional<User> optionalUser = users.stream().filter(user -> user.getSteamId().equals(steamId)).findFirst();
        
        return optionalUser.isPresent();
    }
    
    public static VipUsersSetup load() {
        log.debug("Reading vip list from xml {}.", FILE);
        
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("vipUsers", VipUsersSetup.class);
        xstream.alias("user", User.class);
        
        try {
            VipUsersSetup vipUsers = (VipUsersSetup)xstream.fromXML(new FileInputStream(FILE));
            
            log.info("Vip list loaded with {} users.", vipUsers.size());
            
            return vipUsers;
        }
        catch (Exception ex) {
            throw new VipUsersException("error loading file " + FILE, ex);
        }
    }

}