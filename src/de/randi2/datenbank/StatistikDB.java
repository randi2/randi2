package de.randi2.datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.randi2.datenbank.exceptions.DatenbankExceptions;

/**
 * 
 * @author Frederik Reifschneider [Reifschneider@stud.uni-heidelberg.de]
 * @version $Id$
 *
 */
public class StatistikDB {
	
	
	/**
	 * Konstanten fuer die View getVerteilungPatMW
	 * 
	 * @author Frederik Reifschneider
	 */
	private enum VIEW1 {
		/**
		 * Name der View
		 */
		NAME("verteilungPatMW"),
		/**
		 * Spalte studienID 
		 */
		STUDIE("studienID"),
		/**
		 * Spalte studienarmID
		 */
		STUDIENARM("studienarmID"),
		/**
		 * Spalte Gesamtzahl
		 */
		ANZAHLGESAMT("anzG"),
		/**
		 * Spalte Anzahl Maenner
		 */
		ANZAHLMAENNER("anzM"),
		/**
		 * Spalte Anzahl Frauen
		 */
		ANZAHLFRAUEN("anzF");
		
		/**
		 * Konstruktor
		 * @param text
		 * 			Wert fuer Enum Feld
		 */
		private VIEW1(String text) {
			this.text=text;
		}
		
		/**
		 * Name eines Feldes
		 */
		private String text ;
		
		/**
		 * Liefert Feld Inhalt
		 * @see java.lang.Enum#toString()
		 */
		public String toString() {
			return this.text;
		}
	}
	
	
	/**
	 * Liefert die Anzahl maennlicher, weiblicher, aller Patienten pro Studienarm, als 
	 * auch fuer die Gesamte Studie.
	 * @param studienID
	 * 			ID der Studie zu der die Statistik geliefert werden soll.
	 * @return
	 * 		2-dim Integer Array. 
	 * 		X = Postion 
	 * 		X = 0 liefert immer die Gesamtwerte fuer die Studie
	 * 		X = 1...N die der einzelnen Studienarme.
	 * 		getAnzPatMW()[X][0] -> ID des Studienarms/ bzw bei X = 0 der Studie
	 * 		getAnzPatMW()[X][1] -> Anzahl aller Patienten
	 * 		getAnzPatMW()[X][2] -> Anzahl maennlicher Patienten
	 * 		getAnzPatMW()[X][3] -> Anzazhl weiblicher Patienten
	 * 		
	 * @throws DatenbankExceptions 
	 */
	public static long[][] getVertPatMW(long studienID) throws DatenbankExceptions {
		Connection c = ConnectionFactory.getInstanz().getConnection();
		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long[][] daten=null;
		try {
			//ermitteln der Anzahl Studienarme (+ 1 fuer Gesamtzahl) 
			sql = " SELECT count(distinct sa."+Datenbank.FelderStudienarm.ID+") FROM "+Datenbank.Tabellen.STUDIENARM+" sa WHERE sa."+Datenbank.FelderStudienarm.STUDIE+" = ? ";
			pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, studienID);
			int anzahlReihen = -1;
			rs = pstmt.executeQuery();
			if(rs.next()) {				
				anzahlReihen = rs.getInt(1)+1;
			} else {
				throw new DatenbankExceptions(DatenbankExceptions.STATISTIK_FEHLENDE_PATIENTE);
			}
			if(anzahlReihen<1) {
				throw new DatenbankExceptions(DatenbankExceptions.STATISTIK_VIEW1);
			}
			//ermitteln der Studienarme und IDs. Fuellen des Arrays mit den Studienarm IDs
			sql ="SELECT "+Datenbank.FelderStudienarm.ID+" FROM "+Datenbank.Tabellen.STUDIENARM+" sa WHERE sa."+Datenbank.FelderStudienarm.STUDIE+" = ?";
			pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, studienID);
			rs = pstmt.executeQuery();
			daten = new long[anzahlReihen][4];
			daten[0][0] = studienID;
			int i = 1; 
			while(rs.next()) {
				daten[i][0] = rs.getLong(1); //studienarmID
				i++;
			}			
			//Abfrage der View
			sql = "SELECT * FROM "+VIEW1.NAME+" WHERE "+VIEW1.STUDIE+" = ?";
			pstmt = c.prepareStatement(sql);
			pstmt.setLong(1, studienID);
			rs = pstmt.executeQuery();
			i=1;
			while(rs.next()) {
				daten[i][1] = rs.getLong(3); //gesamtzahl
				daten[i][2] = rs.getLong(4); //maennlich
				daten[i][3] = rs.getLong(5); //weiblich
				//aufaddieren der der Teilergebnisse zur Gesamtzahl
				daten[0][1] += daten[i][1]; 
				daten[0][2] += daten[i][2]; 
				daten[0][3] += daten[i][3];
				i++;
			}
			//setze die restlichen Felder auf 0. Dies betrifft leere Studienarme
			for(;i<anzahlReihen;i++) {
				daten[i][1] = 0; //gesamtzahl
				daten[i][2] = 0; //maennlich
				daten[i][3] = 0; //weiblich
			}
		} catch (SQLException e) {
			throw new DatenbankExceptions(e,sql,DatenbankExceptions.STATISTIK_VIEW1);
		}
		return daten;
	}

}
