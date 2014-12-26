package br.com.sdtd.helper.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Telnet {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Socket socket;
    private ExecutorService executorService;
    
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public Telnet(String url, int port, boolean keepAline) {
        log.debug("opening connection " + url + ":" + port);
        
        try {
            this.socket = new Socket(url, port);
            this.socket.setKeepAlive(keepAline);
            this.executorService = Executors.newCachedThreadPool();
        } catch (IOException ex) {
            throw new TelnetDataErrorException(ex);
        }
    }
    
    public void open() throws IOException {
        log.debug("Setup data reader and writer.");
        
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.printWriter = new PrintWriter(socket.getOutputStream(), true);
    }
    
    public void close() throws IOException {
        log.debug("closing");
        
        this.printWriter.close();
        this.bufferedReader.close();
    }
    
    private synchronized <R> R call(Command<? extends R> command) {
        log.debug("Executing command " + command.name());
        
        try {
            if (command.reset()) {
                resetReaderBuffer();
            }
            
            boolean processing = true;
            while (processing) {
                if (!command.getNextCommand().isEmpty()) {
                    String serverCommand = command.getNextCommand();
                    log.debug("Sending command: " + serverCommand);
                    printWriter.println(serverCommand);
                }

                String line = bufferedReader.readLine();
                
                if (line == null) {
                    throw new TelnetDataErrorException("Data read error");
                }
                
                log.debug("processing data: " + line);
                processing = command.processData(line);
            }
            
            log.debug("command " + command.name() + " finished.");
            
            return command.getResult();
        }
        catch (IOException ex) {
            throw new TelnetDataErrorException(ex);
        }
    }
    
    public <R> Future<R> executeCommand(Command<? extends R> command) {
        Future<R> future = this.executorService.submit(() -> {
            return call(command);
        });
        
        return future;
    }
    
    private void resetReaderBuffer() throws IOException {
        log.debug("Flushing data");
        
        while(this.bufferedReader.ready()) {
            this.bufferedReader.skip(1);
        }
    }
}
