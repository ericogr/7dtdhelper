package br.com.sdtd.helper.ban;

import br.com.sdtd.helper.telnet.Command;
import br.com.sdtd.helper.GameService;
import br.com.sdtd.helper.GenericExecution;
import br.com.sdtd.helper.Player;
import br.com.sdtd.helper.command.SpawnEntity;
import java.util.List;

public class SpawnZombieExecution extends GenericExecution {
    private SpawnZombieSetup spawnZombieSetup;
    private long lastExec;
    
    @Override
    public void init(GameService gameService) {
        super.init(gameService);
        this.spawnZombieSetup = SpawnZombieSetup.load();
        this.lastExec = this.spawnZombieSetup.getStartDelay() + System.currentTimeMillis();
    }

    @Override
    public String name() {
        return getClass().getSimpleName();
    }

    @Override
    public void execute() {
        if (lastExec < System.currentTimeMillis()) {
            List<Player> playersOnline = getGameService().getPlayersOnline();
            
            playersOnline.forEach(player -> {
                for (int i = 0; i < this.spawnZombieSetup.getQuantity(); i++) {
                    Command<Void> command = new SpawnEntity(getRandomEntityName(), player);
                    this.getGameService().execute(command);
                }
            });
            
            lastExec = System.currentTimeMillis() + this.spawnZombieSetup.getInterval();
        }
    }
    
    private String getRandomEntityName() {
        int rnd = (int)(Math.random() * this.spawnZombieSetup.getEntityIds().size());
        
        return this.spawnZombieSetup.getEntityIds().get(rnd);
    }
    
}
