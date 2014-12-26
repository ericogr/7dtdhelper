package br.com.sdtd.helper;

public interface Execution {
    String name();
    void init(GameService gameService);
    void execute();
}