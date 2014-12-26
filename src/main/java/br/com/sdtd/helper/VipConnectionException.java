package br.com.sdtd.helper;

public class VipConnectionException extends Exception {
    public VipConnectionException(String message) {
        super(message);
    }
    
    public VipConnectionException(String message, Exception ex) {
        super(message, ex);
    }
    
    public VipConnectionException(Exception ex) {
        super(ex);
    }
}
