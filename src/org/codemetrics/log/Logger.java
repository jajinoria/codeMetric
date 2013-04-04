
package org.codemetrics.log;

/**
 *
 * @author Daniel & Jose & Johanna
 */
public abstract class Logger {
    protected String filename;
    
    public Logger(String filename){
        this.filename = filename;
    }
    
    public abstract void log();
}
