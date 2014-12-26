package br.com.sdtd.helper.ban;

import br.com.sdtd.helper.ban.validation.TelizeCountryPlayerValidation;
import br.com.sdtd.helper.ban.validation.PlayerValidation;
import br.com.sdtd.helper.GameService;
import br.com.sdtd.helper.GenericExecution;
import br.com.sdtd.helper.Player;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BannedCountryExecution extends GenericExecution {
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private PlayerValidation banService;
    private BannedCountriesSetup bannedCountriesConfig;
    
    @Override
    public void init(GameService gameService) {
        super.init(gameService);
        this.bannedCountriesConfig = BannedCountriesSetup.load();
        this.banService = new TelizeCountryPlayerValidation(this.bannedCountriesConfig);
    }
    
    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
    
    @Override
    public void execute() {
        List<Player> playersOnline = getGameService().getPlayersOnline();
        
        playersOnline.stream()
                .filter(player -> !banService.isValid(player))
                .forEach(player -> {
                    this.log.info("Kick player {} in country ban list", player.getSteamId());
                    getGameService().kickPlayer(player, bannedCountriesConfig.getKickMessage());
                });
    }
}
