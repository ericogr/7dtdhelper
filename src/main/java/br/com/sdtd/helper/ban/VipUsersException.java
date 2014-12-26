package br.com.sdtd.helper.ban;

public class VipUsersException extends RuntimeException {
    public VipUsersException(String message) {
        super(message);
    }
    
    public VipUsersException(String message, Throwable t) {
        super(message, t);
    }
    
    public VipUsersException(Throwable t) {
        super(t);
    }
}
