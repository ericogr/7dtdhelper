package br.com.sdtd.helper;

import br.com.sdtd.helper.ban.VipUsersException;
import br.com.sdtd.helper.command.Ban;
import br.com.sdtd.helper.telnet.Telnet;
import br.com.sdtd.helper.telnet.Command;
import br.com.sdtd.helper.command.Connect;
import br.com.sdtd.helper.command.Kick;
import br.com.sdtd.helper.command.ListPlayers;
import br.com.sdtd.helper.command.Say;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GameService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Options options;
    
    private Telnet telnet;
    
    private GameService(Options options) {
        this.options = options;
    }
    
    public static GameService connect(Options options) throws VipConnectionException {
        GameService gameService = new GameService(options);
        
        gameService.init();
        
        return gameService;
    }
    
    private void init() throws VipConnectionException {
        log.info("Connecting server " + this.options.getServer().getAddress() + ":" + this.options.getServer().getPort());
        this.telnet = new Telnet(this.options.getServer().getAddress(), this.options.getServer().getPort(), true);
        
        try {
            this.telnet.open();
        }
        catch (IOException ex) {
            throw new VipUsersException("Error opening connection", ex);
        }
        
        Command<Boolean> connCommand = new Connect(this.options.getServer().getPassword());
        
        if (execute(connCommand)) {
            log.info("Logged!");
        }
        else {
            throw new VipConnectionException("Invalid server password");
        }
    }
    
    public Options getOptions() {
        return this.options;
    }
    
    public <T> T execute(Command<T> command) {
        try {
            Future<T> future = telnet.executeCommand(command);
            return future.get(this.options.getCommandTimeout(), TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException ex) {
            throw new VipTimeoutException("Command takes too long to be execute: " + command.name(), ex);
        }
    }
    
    public void kickPlayer(Player pkick, String message) {
        log.info("kicking player " + pkick.toString() + " with message [" + message + "]");
                
        Command<Boolean> kickCommand = new Kick(pkick, message);

        if (execute(kickCommand)) {
            log.info("Player kicked: {}", pkick.toString());
        }
        else {
            log.error("Player not logged in {}", pkick.toString());
        }
    }
    
    public void banPlayer(Player pkick, int minutes) {
        log.info("Ban player " + pkick.toString() + " for " + minutes + " minutes");
                
        Command<Void> banCommand = new Ban(pkick, minutes);

        execute(banCommand);
        
        log.info("Ban executed for {}", pkick.toString());
    }
    
    public List<Player> getPlayersOnline() {
        Command<List<Player>> lpCommand = new ListPlayers();
        return execute(lpCommand);
    }
    
    public void say(String message) {
        Command<Void> lpCommand = new Say(message);
        execute(lpCommand);
    }
}
