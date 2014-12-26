package br.com.sdtd.helper.telnet;

/**
 *
 * @author erico
 */
public class TelnetDataErrorException extends TelnetException {
    public TelnetDataErrorException(String message) {
        super(message);
    }
    
    public TelnetDataErrorException(String message, Exception ex) {
        super(message, ex);
    }
    
    public TelnetDataErrorException(Exception ex) {
        super(ex);
    }
}
