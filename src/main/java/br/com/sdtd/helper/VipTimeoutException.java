package br.com.sdtd.helper;

import br.com.sdtd.helper.ban.VipUsersException;

public class VipTimeoutException extends VipUsersException {
    public VipTimeoutException(String message) {
        super(message);
    }
    
    public VipTimeoutException(String message, Exception ex) {
        super(message, ex);
    }
    
    public VipTimeoutException(Exception ex) {
        super(ex);
    }
}