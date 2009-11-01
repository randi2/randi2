/* 
 * (c) 2008- RANDI2 Core Development Team
 * 
 * This file is part of RANDI2.
 * 
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.jsf.fileupload;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.File;


/**
 * <p>The InputFileSessionCleaner is responsible for cleaning up any files
 * that may have been uploaded by a session.  The InputFile component by
 * default stores files in an upload folder in the root directory of the
 * deployed application in a child folder named after session id.</p>
 * <p>In most deployment scenarios there is no need to keep files that
 * where uploaded during the session.  This class implements
 * HttpSessionListener and will clean up any uploaded files when the
 * #sessionDestroyed method is called be the Servlet container </p>
 * <p>Make sure that this been is properly register as a listener in the
 * application web.xml descriptor file.</p>
 *
 */
public class InputFileSessionCleaner implements HttpSessionListener {


	 public static final String FILE_UPLOAD_DIRECTORY = "upload";

	    /**
	     * This method is called by the servlet container when the session
	     * is about to expire. This method will attempt to delete all files that
	     * where uploaded into the folder which has the same name as the session
	     * id.
	     *
	     * @param event JSF session event.
	     */
	    public void sessionDestroyed(HttpSessionEvent event) {
	        // get the session id, so we know which folder to remove
	        String sessionId = event.getSession().getId();

	        String applicationPath = event.getSession().getServletContext().getRealPath(
	                event.getSession().getServletContext().getServletContextName());

	        String sessionFileUploadPath =
	                applicationPath + FILE_UPLOAD_DIRECTORY + sessionId;

	        File sessionfileUploadDirectory = new File(sessionFileUploadPath);

	        if (sessionfileUploadDirectory.isDirectory()) {
	            try {
	                sessionfileUploadDirectory.delete();
	            }
	            catch (SecurityException e) {
	               e.printStackTrace();
	            }
	        }

	    }

	    public void sessionCreated(HttpSessionEvent event) {

	    }

}

