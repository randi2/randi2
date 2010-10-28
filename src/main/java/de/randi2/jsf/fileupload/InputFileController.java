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

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;

/**
 * <p>The InputFileController is responsible for the file upload
 * logic as well as the file deletion object.  A users file uploads are only
 * visible to them and are deleted when the session is destroyed.</p>
 */
@ManagedBean(name="inputFileController")
@SessionScoped
public class InputFileController{

	    // File sizes used to generate formatted label
	    public static final long MEGABYTE_LENGTH_BYTES = 1048000l;
	    public static final long KILOBYTE_LENGTH_BYTES = 1024l;

	    // files associated with the current user
	    private final List<InputFileData> fileList =
	            Collections.synchronizedList(new ArrayList<InputFileData>());
	    // latest file uploaded by client
	    private InputFileData currentFile;
	    // file upload completed percent (Progress)
	    private int fileProgress;

	    /**
	     * <p>Action event method which is triggered when a user clicks on the
	     * upload file button.  Uploaded files are added to a list so that user have
	     * the option to delete them programatically.  Any errors that occurs
	     * during the file uploaded are added the messages output.</p>
	     *
	     * @param event jsf action event.
	     */
	    public void uploadFile(ActionEvent event) {
	        InputFile inputFile = (InputFile) event.getSource();
	        FileInfo fileInfo = inputFile.getFileInfo();
	        if (fileInfo.getStatus() == FileInfo.SAVED) {
	            // reference our newly updated file for display purposes and
	            // added it to our history file list.
	            currentFile = new InputFileData(fileInfo);

	            synchronized (fileList) {
	                fileList.add(currentFile);
	            }

	        }

	    }

	    /**
	     * <p>This method is bound to the inputFile component and is executed
	     * multiple times during the file upload process.  Every call allows
	     * the user to finds out what percentage of the file has been uploaded.
	     * This progress information can then be used with a progressBar component
	     * for user feedback on the file upload progress. </p>
	     *
	     * @param event holds a InputFile object in its source which can be probed
	     *              for the file upload percentage complete.
	     */
	    public void fileUploadProgress(EventObject event) {
	        InputFile ifile = (InputFile) event.getSource();
	        fileProgress = ifile.getFileInfo().getPercent();
	    }

	    /**
	     * <p>Allows a user to remove a file from a list of uploaded files.  This
	     * methods assumes that a request param "fileName" has been set to a valid
	     * file name that the user wishes to remove or delete</p>
	     *
	     * @param event jsf action event
	     */
	    public void removeUploadedFile(ActionEvent event) {
	        // Get the inventory item ID from the context.
	        FacesContext context = FacesContext.getCurrentInstance();
	        Map<String,String> map = context.getExternalContext().getRequestParameterMap();
	        String fileName = (String) map.get("fileName");

	        synchronized (fileList) {
	            InputFileData inputFileData;
	            for (int i = 0; i < fileList.size(); i++) {
	                inputFileData = (InputFileData)fileList.get(i);
	                // remove our file
	                if (inputFileData.getFileInfo().getFileName().equals(fileName)) {
	                    fileList.remove(i);
	                    break;
	                }
	            }
	        }
	    }

	    public InputFileData getCurrentFile() {
	        return currentFile;
	    }

	    public int getFileProgress() {
	        return fileProgress;
	    }

	    public List<InputFileData> getFileList() {
	        return fileList;
	    }
	    
	    public boolean isFileOnServer() {
			return !fileList.isEmpty();
		}
}

