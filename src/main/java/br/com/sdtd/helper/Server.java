package br.com.sdtd.helper;

public final class Server {
    private final String address;
    private final int port;
    private final String password;
    
    public Server(String address, int port, String password) {
        this.address = address;
        this.port = port;
        this.password = password;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}