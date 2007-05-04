package de.randi2.utility;

import org.apache.log4j.Logger;

/**
 * @author Andreas Freudling [afreudling@hs-heilbronn.de]
 * @version $Id$
 *
 */
public class SystemException extends Exception {
    
    /**
     * @param fehlermeldungIntern
     */
    public SystemException(String fehlermeldungIntern) {
	super(fehlermeldungIntern);
	Logger.getLogger(this.getClass()).error(fehlermeldungIntern, this);
    }

}
