package de.randi2.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.missiondata.fileupload.MonitoredDiskFileItemFactory;

import de.randi2.controller.FileUploadListener.FileUploadStats;
import de.randi2.controller.FileUploadListener.FileUploadStatus;
import de.randi2.utility.Config;
import de.randi2.utility.Parameter;

/**
 * Diese Klasse realisiert HTTP POST Uploads.
 * 
 * @version $Id$
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * 
 */
public class FileUploadServlet extends HttpServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String saveFilePath = Config
					.getProperty(Config.Felder.RELEASE_UPLOAD_PATH);
			FileUploadListener listener = new FileUploadListener(request
					.getContentLength());
			request.getSession().setAttribute("FILE_UPLOAD_STATS",
					listener.getFileUploadStats());
			DiskFileItemFactory factory = new MonitoredDiskFileItemFactory(
					listener);
			factory.setRepository(new File(saveFilePath));
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(request);
			for (Iterator i = items.iterator(); i.hasNext();) {
				FileItem fileItem = (FileItem) i.next();

				if (!fileItem.isFormField()) {
					fileItem.write(new File(saveFilePath + fileItem.getName()));
					request.setAttribute(Parameter.studie.STUDIENPROTOKOLL
							.toString(), fileItem.getName());
				} else {
					Logger.getLogger(this.getClass()).debug(
							(fileItem.getFieldName() + "==" + fileItem
									.getString()));
					request.setAttribute(fileItem.getFieldName(), fileItem
							.getString());

				}
			}
			Logger.getLogger(this.getClass()).debug("Upload erfolgreich");

		} catch (Exception e) {

			FileUploadStats stats = new FileUploadListener.FileUploadStats();
			stats.setCurrentStatus(FileUploadStatus.ERROR);
			request.getSession().setAttribute("FILE_UPLOAD_STATS", stats);
			e.printStackTrace();
		}
		request.getRequestDispatcher("DispatcherServlet").forward(request,
				response);
	}
}
