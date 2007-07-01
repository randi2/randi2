package de.randi2.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.randi2.utility.Config;
import de.randi2.utility.SystemException;

/**
 * Diese Klasse realisiert Downloads.
 * 
 * Beim Aufruf der doPost muessen alle Attribute der enum requestParameter als
 * Attribut an das request gebunden sein. Bei DATEI_ART ist das Objekt vom Typ
 * dateiArt und bei DATEI_NAME ist das Objekt vom Typ String.
 * 
 * @version $Id$
 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
 * 
 */
public class DownloadServlet extends HttpServlet {
	/**
	 * Die moeglichen requestParameter. Diese sind beide Pflicht beim Aufruf von
	 * doPost().
	 * 
	 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
	 * 
	 */
	public enum requestParameter {

		DATEI_ART, DATEI_NAME

	}

	/**
	 * Die moeglichen Datei-Arten. Durch diese Angabe wird der Downloadpfad
	 * generiert.
	 * 
	 * @author Daniel Haehn [dhaehn@stud.hs-heilbronn.de]
	 * 
	 */
	public enum dateiArt {

		STUDIENPROTOKOLL, RANDOMISATIONSERGEBNISSE

	}

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

		dateiArt aDateiArt = (dateiArt) request
				.getAttribute(requestParameter.DATEI_ART.toString());
		String aDateiName = (String) request
				.getAttribute(requestParameter.DATEI_NAME.toString());
		String aDateiPfad = "";

		if (aDateiName == null || aDateiName.trim().equals("")) {

			throw new SystemException("Ungueltiger Dateiname!");

		}

		if (aDateiArt == dateiArt.STUDIENPROTOKOLL) {

			aDateiPfad = Config.getProperty(Config.Felder.RELEASE_UPLOAD_PATH)
					+ aDateiName;

		} else if (aDateiArt == dateiArt.RANDOMISATIONSERGEBNISSE) {

			aDateiPfad = Config
					.getProperty(Config.Felder.RELEASE_UPLOAD_PATH_TMP)
					+ aDateiName;

		} else {

			throw new SystemException("Fehler beim auswaehlen der Datei!");

		}

		FileInputStream fileToDownload = new FileInputStream(aDateiPfad);
		ServletOutputStream out = response.getOutputStream();

		response.setContentType("application/x-download");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ aDateiName);
		response.setContentLength(fileToDownload.available());

		int c;
		while ((c = fileToDownload.read()) != -1) {
			out.write(c);
		}
		out.flush();
		out.close();
		fileToDownload.close();

	}
}
