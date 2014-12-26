package br.com.sdtd.helper.telnet;

public interface Command<R> {
    
    /**
     * Command name
     * @return the command name
     */
    String name();
    
    /**
     * reset all buffered data before start
     * @return true if reset is needed
     */
    boolean reset();

    /**
     * Get the next command to be executed.
     * When the process starts, its executed.
     * After every processData, this method is called too.
     * If no command is needed, return an empty string.
     * @return the next command
     */
    String getNextCommand();

    /**
     * Process data from stream (line)
     * @param data
     * @return returns true if you need more lines to build the result
     */
    boolean processData(String data);

    /**
     * If isResultReady() is true, get result returns the result.
     * @return the result of executed command.
     */
    R getResult();
}
