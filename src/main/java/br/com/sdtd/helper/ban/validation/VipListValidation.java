package br.com.sdtd.helper.ban.validation;

import br.com.sdtd.helper.Player;
import br.com.sdtd.helper.ban.VipUsersSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VipListValidation implements PlayerValidation {
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final VipUsersSetup vipUsersSetup;
    
    public VipListValidation(VipUsersSetup vipUsersSetup) {
        this.vipUsersSetup = vipUsersSetup;
    }

    @Override
    public boolean isValid(Player player) {
        return this.vipUsersSetup.existUserBySteamId(player.getSteamId());
    }
}
