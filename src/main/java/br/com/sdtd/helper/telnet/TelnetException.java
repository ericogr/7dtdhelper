package br.com.sdtd.helper.telnet;

/**
 *
 * @author erico
 */
public class TelnetException extends RuntimeException {
    public TelnetException(String message, Exception ex) {
        super(message, ex);
    }
    
    public TelnetException(Exception ex) {
        super(ex);
    }
    
    public TelnetException(String message) {
        super(message);
    }
}
