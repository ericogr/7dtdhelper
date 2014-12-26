package br.com.sdtd.helper;

import br.com.sdtd.helper.telnet.TelnetDataErrorException;
import br.com.sdtd.helper.telnet.TelnetException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorSchedule implements Runnable {
    private final int TIMEOUT_ERROR = -1;
    private final int TELNET_ERROR = -2;
    private final int CONNECTION_ERROR = -3;
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    private List<Execution> executions;
    
    public ExecutorSchedule(Options options) {
        try {
            GameService gameService = GameService.connect(options);
            
            this.executions = options.getExecutors();
            this.executions.forEach(exec -> {
                log.debug("Init " + exec.name());
                exec.init(gameService);
            });
        }
        catch (VipConnectionException ex) {
            log.error("Invalid password", ex);
            System.exit(CONNECTION_ERROR);
        }
    }
    
    @Override
    public void run() {
        try {
            long time = System.currentTimeMillis();
            
            log.debug("Running schedule...");
            
            this.executions.forEach(exec -> {
                log.debug("Executing " + exec.name());
                exec.execute();
            });
            
            log.debug("Running schedule finished in {}ms", (System.currentTimeMillis() - time));
        }
        catch (VipTimeoutException ex) {
            log.error(ex.getMessage(), ex);
            System.exit(TIMEOUT_ERROR);
        }
        catch (TelnetDataErrorException ex) {
            log.error(ex.getMessage(), ex);
            System.exit(TELNET_ERROR);
        }
        catch (TelnetException ex) {
            log.error(ex.getMessage(), ex);
        }
        catch (Throwable ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}