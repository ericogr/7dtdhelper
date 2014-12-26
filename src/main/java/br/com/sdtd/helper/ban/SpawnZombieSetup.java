package br.com.sdtd.helper.ban;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpawnZombieSetup {
    private static final Logger log = LoggerFactory.getLogger(SpawnZombieSetup.class);
    private static final String FILE = "spawn-entities.xml";
    
    private int interval;
    private int quantity;
    private int startDelay;
    private String entityIds;

    public int getInterval() {
        return interval;
    }

    public int getQuantity() {
        return quantity;
    }
    
    public int getStartDelay() {
        return this.startDelay;
    }

    public List<String> getEntityIds() {
        StringTokenizer st = new StringTokenizer(this.entityIds, ",");
        List<String> entities = new ArrayList<>();
        
        while(st.hasMoreElements()) {
            entities.add(st.nextToken());
        }
        
        return entities;
    }
    
    public static SpawnZombieSetup load() {
        log.debug("Reading vip list from xml {}.", FILE);
        
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("spawnZombies", SpawnZombieSetup.class);
        
        try {
            SpawnZombieSetup spawnZombies = (SpawnZombieSetup)xstream.fromXML(new FileInputStream(FILE));
            
            log.info("Spawn zombies loaded");
            
            return spawnZombies;
        }
        catch (Exception ex) {
            throw new VipUsersException("error loading file " + FILE, ex);
        }
    }
}
