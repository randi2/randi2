package de.randi2.utility;

import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Wrapped eine Tabelle die nachher im CSV oder Excel-Format ausgegeben werden
 * kann.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id$
 * 
 */
public class Tabelle {

	/**
	 * Die Kopfzeile
	 */
	private String kopfzeile[];

	/**
	 * Der Inhalt der Tabelle zeilenweise.
	 */
	private Vector<String[]> inhalt;

	/**
	 * Spaltentrenner
	 */
	private static final String SPALTENTRENNER = ",";

	/**
	 * Zeilentrenner
	 */
	private static final String ZEILENTRENNER = "\n";

	/**
	 * Konstruktor.
	 * 
	 * @param anzSpalten
	 *            Die Anzahl der Spalten
	 */
	public Tabelle(int anzSpalten) {
		kopfzeile = null;
		this.inhalt = new Vector<String[]>();
	}

	/**
	 * Konstruktor.
	 * 
	 * @param kopfzeile
	 *            Die Kopfzeile
	 */
	public Tabelle(String[] kopfzeile) {
		this.kopfzeile = kopfzeile;
		this.inhalt = new Vector<String[]>();
	}

	/**
	 * Fuegt der Tabelle eine Zeile an.
	 * 
	 * @param zeile
	 *            Die Zeile
	 */
	public void addZeile(String[] zeile) {
		this.inhalt.add(zeile);
	}

	/**
	 * Gibt einen CSV-String zurueck·
	 * 
	 * @return Der CSV-String.
	 */
	public String getCSVString() {
		StringBuffer csv = new StringBuffer();
		if (this.kopfzeile != null) {
			for (int i = 0; i < kopfzeile.length; i++) {
				if (i < kopfzeile.length - 1) {
					csv.append("\"" + kopfzeile[i] + "\"" + SPALTENTRENNER);
				} else {
					csv.append("\"" + kopfzeile[i] + "\"" + ZEILENTRENNER);
				}
			}

		}

		for (String zeile[] : this.inhalt) {
			for (int i = 0; i < zeile.length; i++) {
				if (i < zeile.length - 1) {
					csv.append("\"" + zeile[i] + "\"" + SPALTENTRENNER);
				} else {
					csv.append("\"" + zeile[i] + "\"" + ZEILENTRENNER);
				}
			}
		}

		return csv.toString();
	}

	/**
	 * Erzeugt ein Excel-Sheet und gibt es zurueck.
	 * 
	 * @return Das Excel-Sheet.
	 */
	public HSSFWorkbook getXLS() {

		HSSFWorkbook excelMappe = new HSSFWorkbook();
		HSSFSheet excelSheet = excelMappe.createSheet();

		HSSFRow reihe = null;
		HSSFCell zelle = null;

		HSSFCellStyle titelStyle = excelMappe.createCellStyle();
		HSSFCellStyle datenStyle = excelMappe.createCellStyle();

		HSSFFont titelFont = excelMappe.createFont();
		HSSFFont datenFont = excelMappe.createFont();

		titelFont.setFontHeightInPoints((short) 9);
		titelFont.setColor((short) HSSFFont.COLOR_NORMAL);
		titelFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		datenFont.setFontHeightInPoints((short) 9);
		datenFont.setColor((short) HSSFFont.COLOR_NORMAL);
		datenFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		titelStyle.setFont(titelFont);
		titelStyle.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
		titelStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		titelStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titelStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		datenStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		datenStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		datenStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		datenStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

		titelStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		datenStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

		// set the font
		datenStyle.setFont(datenFont);

		// set the sheet name in Unicode
		excelMappe.setSheetName(0, "Randomisationsergebnisse");
		// in case of compressed Unicode
		// wb.setSheetName(0, "HSSF Test",
		// HSSFWorkbook.ENCODING_COMPRESSED_UNICODE );
		// create a sheet with 30 rows (0-29)

		reihe = excelSheet.createRow((short) 0);
		for (int i = 0; i < kopfzeile.length; i++) {
			zelle = reihe.createCell((short) i);
			zelle.setCellStyle(titelStyle);
			zelle.setCellValue(new HSSFRichTextString(kopfzeile[i]));
		}

		int i = 0;
		for (String zeile[] : this.inhalt) {
			i++;
			reihe = excelSheet.createRow((short) i);
			for (int j = 0; j < zeile.length; j++) {
				zelle = reihe.createCell((short) j);
				zelle.setCellStyle(datenStyle);
				zelle.setCellValue(new HSSFRichTextString(zeile[j]));
			}
		}

		return excelMappe;

		// wb.write(out);
		// out.close();
	}

}
