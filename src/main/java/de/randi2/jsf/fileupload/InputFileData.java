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

import com.icesoft.faces.component.inputfile.FileInfo;

import java.io.File;

/**
 * <p>The InputFileData Class is a simple wrapper/storage for object that are
 * returned by the inputFile component.  The FileInfo Class contains file
 * attributes that are associated during the file upload process.  The File
 * Object is a standard java.io File object which contains the uploaded
 * file data. </p>
 */
public class InputFileData {

	// file info attributes
    private FileInfo fileInfo;
    // file that was uplaoded
    private File file;

    /**
     * Create a new InputFileDat object.
     *
     * @param fileInfo fileInfo object created by the inputFile component for
     *                 a given File object.
     */
    public InputFileData(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
        this.file = fileInfo.getFile();
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    /**
     * Method to return the file size as a formatted string
     * For example, 4000 bytes would be returned as 4kb
     *
     *@return formatted file size
     */
    public String getSizeFormatted() {
        long ourLength = file.length();
        
        // Generate formatted label, such as 4kb, instead of just a plain number
        if (ourLength >= InputFileController.MEGABYTE_LENGTH_BYTES) {
            return ourLength / InputFileController.MEGABYTE_LENGTH_BYTES + " MB";
        }
        else if (ourLength >= InputFileController.KILOBYTE_LENGTH_BYTES) {
            return ourLength / InputFileController.KILOBYTE_LENGTH_BYTES + " KB";
        }
        else if (ourLength == 0) {
            return "0";
        }
        else if (ourLength < InputFileController.KILOBYTE_LENGTH_BYTES) {
            return ourLength + " B";
        }
        
        return Long.toString(ourLength);
    }    
}

