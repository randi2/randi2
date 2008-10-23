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
     * @param file     corresponding File object which as properties described
     *                 by the fileInfo object.
     */
    public InputFileData(FileInfo fileInfo, File file) {
        this.fileInfo = fileInfo;
        this.file = file;
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

