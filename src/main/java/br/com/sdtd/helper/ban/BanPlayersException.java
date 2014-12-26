package br.com.sdtd.helper.ban;

/**
 *
 * @author erico
 */
public class BanPlayersException extends RuntimeException {
    public BanPlayersException(String message) {
        super(message);
    }
    
    public BanPlayersException(String message, Throwable ex) {
        super(message, ex);
    }
    
    public BanPlayersException(Throwable ex) {
        super(ex);
    }
}