package br.com.sdtd.helper.ban;

import br.com.sdtd.helper.ban.validation.PlayerValidation;
import br.com.sdtd.helper.GenericExecution;
import br.com.sdtd.helper.Player;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GenericKickUsersExecution extends GenericExecution {
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Override
    public void execute() {
        List<Player> playersOnline = getGameService().getPlayersOnline();
        
        kickWhenPlayerIsBanned(playersOnline);
    }
    
    public abstract PlayerValidation getBanService();
    
    public abstract String getKickMessage();
    
    public void kickWhenPlayerIsBanned(List<Player> playersOnline) {
        playersOnline.stream()
                .filter(player -> !getBanService().isValid(player))
                .forEach(player -> {
                        this.log.info("Kick player {} in ban list", player.getSteamId());
                        getGameService().kickPlayer(player, getKickMessage());});
    }
}