package de.randi2.utility;
import java.util.*;
import java.io.*;

/**
 * @author Andreas Freudling afreudling@stud.hs-heilbronn.de
 *Achtung Klasse wurde nur im Rahmen des Config Managements eingebaut, Verwendung auf eigene Gefahr
 */
public class Config
{
    private static Config singleton = null;
    private Properties debugConf = null;
    private Properties releaseConf=null; 
    
    private Config()
    {
	String releaseDateiname = "conf//release//leasrelease.conf";
	String debugDateiname = "conf//debug//debug.conf";
	
	debugConf = new Properties();
	// release mit Oberproperty
	releaseConf=new Properties(debugConf); // 
	try
	{
	    // DebugConf wird gefuellt
	    debugConf.load(new FileInputStream(debugDateiname));
	    
	    // ReleaseConf wird gefuellt
	    releaseConf.load(new FileInputStream(releaseDateiname));

	}catch (Exception e)
	{
	    e.printStackTrace();
	    //System.exit(1);
	}
    }
    public static String getProperty(Felder feld)
    {
	if (singleton == null)
	{
	    singleton = new Config();
	}
	return singleton.releaseConf.getProperty(feld+"");
    }    
    
    public enum Felder
    {
	DEBUG_SELENIUM_SERVER_HOST,
	DEBUG_SELENIUM_SERVER_PORT,
	DEBUG_SELENIUM_FIREFOX_LOCATION,
	DEBUG_SELENIUM_START_URL;
    }

    /**
         * @param args
         */
    public static void main(String[] args)
    {
	for(Felder f:Felder.values())
	
	System.out.println(Config.getProperty(f));
	
    }
	
}
