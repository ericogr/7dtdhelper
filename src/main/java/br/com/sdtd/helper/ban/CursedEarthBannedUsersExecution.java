package br.com.sdtd.helper.ban;

import br.com.sdtd.helper.ban.validation.PlayerValidation;
import br.com.sdtd.helper.ban.validation.CursedEarthPlayerValidation;
import br.com.sdtd.helper.GameService;

public class CursedEarthBannedUsersExecution extends GenericKickUsersExecution {
    
    private PlayerValidation playerValidation;
    private CursedEarthBannedUsersSetup cursedEarthBannedUsersSetup;
    
    @Override
    public String name() {
        return getClass().getSimpleName();
    }
    
    @Override
    public void init(GameService gameService) {
        super.init(gameService);
        this.cursedEarthBannedUsersSetup = CursedEarthBannedUsersSetup.load();
        this.playerValidation = new CursedEarthPlayerValidation();
    }
    
    @Override
    public PlayerValidation getBanService() {
        return this.playerValidation;
    }
    
    @Override
    public String getKickMessage() {
        return this.cursedEarthBannedUsersSetup.getKickMessage();
    }
}