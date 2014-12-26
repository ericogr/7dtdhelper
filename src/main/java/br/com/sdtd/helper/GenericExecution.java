package br.com.sdtd.helper;

public abstract class GenericExecution implements Execution {
    private GameService gameService;
    
    @Override
    public void init(GameService gameService) {
        this.gameService = gameService;
    }
    
    public GameService getGameService() {
        return this.gameService;
    }

}
