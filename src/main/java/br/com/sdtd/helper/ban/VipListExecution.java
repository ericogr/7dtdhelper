package br.com.sdtd.helper.ban;

import br.com.sdtd.helper.ban.validation.VipListValidation;
import br.com.sdtd.helper.ban.validation.PlayerValidation;
import br.com.sdtd.helper.GameService;
import br.com.sdtd.helper.GenericExecution;
import br.com.sdtd.helper.Player;
import java.util.List;

public class VipListExecution extends GenericExecution {
    private VipUsersSetup vipUsersSetup;
    private PlayerValidation playerValidation;

    @Override
    public void init(GameService gameService) {
        super.init(gameService);
        this.vipUsersSetup = VipUsersSetup.load();
        this.playerValidation = new VipListValidation(this.vipUsersSetup);
    }
    
    @Override
    public String name() {
        return getClass().getSimpleName();
    }
    
    @Override
    public void execute() {
        List<Player> playersOnline = getGameService().getPlayersOnline();
        
        int kicks = playersOnline.size() - this.vipUsersSetup.getSlotsAvailable();
        
        if (kicks > 0) {
            int kicked = 0;
            
            for (Player player : playersOnline) {
                boolean isVipUser = this.playerValidation.isValid(player);

                if (!isVipUser) {
                    //getGameService().kickPlayer(player, this.vipUsersSetup.getServerFullKickMessage());
                    getGameService().banPlayer(player, this.vipUsersSetup.getBanTimeMinutes());
                    kicked++;
                }

                if (kicked == kicks) {
                    break;
                }
            }
        }
    }
    
}