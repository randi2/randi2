package de.randi2.utility;
import java.util.Properties;
import org.apache.log4j.Logger;
import java.io.FileInputStream;;

/**
 * @author Andreas Freudling afreudling@stud.hs-heilbronn.de
 *@version $Id$ 
 *
 */
public class Config
{
    private static Config singleton = null;
    private Properties debugConf = null;
    private Properties releaseConf=null; 
    
    private Config()
    {
	String releaseDateiname = "conf/release/release.conf";
	String debugDateiname = "conf/debug/debug.conf";
	
	debugConf = new Properties();
	// release mit Oberproperty
	releaseConf=new Properties(debugConf); // 
	try
	{
	    // DebugConf wird gefuellt
	    debugConf.load(new FileInputStream(debugDateiname));
	    Logger.getLogger(this.getClass()).info("Debug-Konfiguration geladen: "+debugDateiname);
	    Logger.getLogger(this.getClass()).info("Release-Konfiguraion geladen: "+releaseDateiname);
	    // ReleaseConf wird gefuellt
	    releaseConf.load(new FileInputStream(releaseDateiname));

	}catch (Exception e)
	{
	    e.printStackTrace();
	    //System.exit(1);
	}
    }
    /**
     * @param feld
     * @return
     */
    public static synchronized String getProperty(Felder feld)
    {
	if (singleton == null)
	{
	    singleton = new Config();
	}
	return singleton.releaseConf.getProperty(feld+"");
    }    
    
    /**
     *
     *
     */
    public enum Felder
    {
	/**
	 * 
	 */
	DEBUG_SELENIUM_SERVER_HOST,
	/**
	 * 
	 */
	DEBUG_SELENIUM_SERVER_PORT,
	/**
	 * 
	 */
	DEBUG_SELENIUM_FIREFOX_LOCATION,
	/**
	 * 
	 */
	DEBUG_SELENIUM_START_URL,
	/**
	 * 
	 */
	RELEASE_MAIL_SERVER,
	/**
	 * 
	 */
	RELEASE_MAIL_ACCOUNT,
	/**
	 * 
	 */
	RELEASE_MAIL_PASSWORD,
	/**
	 * 
	 */
	RELEASE_MAIL_RANDI2MAILADRESSE,
	/**
	 * 
	 */
	RELEASE_MAIL_RANDI2NAME,
	/**
	 * 
	 */
	RELEASE_MAIL_RANDI2BOUNCE,
	/**
	 * 
	 */
	RELEASE_MAIL_DEBUG;
    }

	
}
