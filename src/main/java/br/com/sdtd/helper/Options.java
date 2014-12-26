package br.com.sdtd.helper;

import br.com.sdtd.helper.ban.VipUsersException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Options {

    private static final Logger log = LoggerFactory.getLogger(Options.class);
    private static final String FILE = "options.xml";

    private final int checkInterval;
    private final int commandTimeout;
    private final List<String> executors;
    private final Server server;

    private Options(int checkInterval, int commandTimeout, List<String> executors, Server server) {
        this.checkInterval = checkInterval;
        this.commandTimeout = commandTimeout;
        this.executors = executors;
        this.server = server;
    }

    public int getCheckInterval() {
        return this.checkInterval;
    }

    public int getCommandTimeout() {
        return this.commandTimeout;
    }

    public List<Execution> getExecutors() {
        List<Execution> exe = new ArrayList<>();
        
        this.executors.forEach(executor -> {
            Execution e = loadClass(executor);
            
            if (e != null) {
                exe.add(e);
            }
        });
        
        return exe;
    }

    private Execution loadClass(String name) {
        try {
            Class clazz = getClass().getClassLoader().loadClass(name);
            return (Execution)clazz.newInstance();
        }
        catch (ClassNotFoundException ex) {
            log.error("Cound not load class " + name, ex);
        }
        catch (InstantiationException ex) {
            log.error("Cound not instantiate class " + name, ex);
        }
        catch (IllegalAccessException ex) {
            log.error("Cound not access class " + name, ex);
        }
        
        return null;
    }

    public Server getServer() {
        return this.server;
    }

    public static Options load() {
        log.debug("Reading server options from xml {}.", FILE);

        XStream xstream = new XStream(new DomDriver());
        xstream.alias("options", Options.class);
        xstream.alias("server", Server.class);
        xstream.alias("executor", String.class);

        try {
            Options options = (Options) xstream.fromXML(new FileInputStream(FILE));
            log.debug("Options loaded.");

            return options;
        } catch (FileNotFoundException ex) {
            throw new VipUsersException("error loading " + FILE, ex);
        }
    }

}
