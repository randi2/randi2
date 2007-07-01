package de.randi2.controller.listener;

import com.missiondata.fileupload.OutputStreamListener;

/**
 * Diese Klasse ueberwacht HTTP Uploads (z.B. zur Ueberwachung via AJAX).
 * 
 * @version $Id$
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * 
 */
public class FileUploadListener implements OutputStreamListener {
	private FileUploadStats fileUploadStats = new FileUploadStats();

	public FileUploadListener(long totalSize) {
		fileUploadStats.setTotalSize(totalSize);
	}

	public void start() {
		fileUploadStats.setCurrentStatus(FileUploadStatus.START);
	}

	public void bytesRead(int byteCount) {
		fileUploadStats.incrementBytesRead(byteCount);
		fileUploadStats.setCurrentStatus(FileUploadStatus.READING);
	}

	public void error(String s) {
		fileUploadStats.setCurrentStatus(FileUploadStatus.ERROR);
	}

	public void done() {
		fileUploadStats.setBytesRead(fileUploadStats.getTotalSize());
		fileUploadStats.setCurrentStatus(FileUploadStatus.DONE);
	}

	public FileUploadStats getFileUploadStats() {
		return fileUploadStats;
	}

	public static class FileUploadStats {

		private long totalSize = 0;

		private long bytesRead = 0;

		private double percentComplete = 0.0;

		private long startTime = System.currentTimeMillis();

		private FileUploadStatus currentStatus = FileUploadStatus.NONE;

		public long getTotalSize() {
			return totalSize;
		}

		public void setTotalSize(long totalSize) {
			this.totalSize = totalSize;
		}

		public long getBytesRead() {
			return bytesRead;
		}

		public long getElapsedTimeInMilliseconds() {
			return (System.currentTimeMillis() - startTime);
		}

		public FileUploadStatus getCurrentStatus() {
			return currentStatus;
		}

		public double getPercentComplete() {
			if (totalSize != 0) {
				percentComplete = (double) bytesRead / (double) totalSize;
			}
			return percentComplete;
		}

		public void setCurrentStatus(FileUploadStatus currentStatus) {
			this.currentStatus = currentStatus;
		}

		public void setBytesRead(long bytesRead) {
			this.bytesRead = bytesRead;
		}

		public void incrementBytesRead(int byteCount) {
			this.bytesRead += byteCount;
		}
	}

	enum FileUploadStatus {
		START("start"), NONE("none"), READING("reading"), ERROR("error"), DONE(
				"done");

		private String type;

		FileUploadStatus(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}
}
