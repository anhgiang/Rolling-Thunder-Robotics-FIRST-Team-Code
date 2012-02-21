/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.microedition.io.Connector;

/**
 * This class is intended to be used by subsystems to write debug information to
 * the cRio
 *
 * @author Clay
 */
public class LogFile {

    private static final LogFile instance = new LogFile();
    private FileConnection fileConnection;
    private DataOutputStream logFile;
    private Date time;
    private final static String FILENAME = "file:///logfile.txt";

    public LogFile() {
        try {
            fileConnection = (FileConnection) Connector.open(FILENAME, Connector.WRITE);
            if (!fileConnection.exists()) {
                fileConnection.create();
            }
            logFile = new DataOutputStream( 
                    fileConnection.openOutputStream(Long.MAX_VALUE)); //This ensures that we append
        } catch (Exception ioe) {
        }
    }

    public static LogFile getInstance() {
        return LogFile.instance;
    }
//Simple, Unformatted Writing

    public void writeString(String string) {
        try {
            logFile.writeChars(string);
        } catch (IOException ex) {
        }
    }

// Log Writing
    public void logString(String string) {
        try {
            //String t = time.toString();
            logFile.writeChars(time.getTime() + string + '\n');
        } catch (IOException ex) {
        }
    }
}
