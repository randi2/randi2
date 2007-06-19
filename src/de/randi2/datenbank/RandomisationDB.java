package de.randi2.datenbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.utility.NullKonstanten;

/**
 * Die Klasse Block bietet einen Persitenzmechanismus fuer die Block-basierten
 * Randomisationen an.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id: Block.java 2445 2007-05-07 09:13:25Z jthoenes $
 */
public final class RandomisationDB extends Filter {

	/**
	 * Name der Blocktabelle
	 */
	private static final String BLOCK_TABLE = "Block";

	/**
	 * Felder der Blocktabelle
	 * 
	 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
	 * @version $Id: Block.java 2445 2007-05-07 09:13:25Z jthoenes $
	 * 
	 */
	private enum Block {
		ID("blockId"), StudieID("Studie_studienId"), WERT("blockwert"), KOMBINATION(
				"strataKombination");

		private String name = null;

		private Block(String n) {
			this.name = n;
		}

		public String toString() {
			return this.name;
		}
	}

	/**
	 * Niemand soll diese Klasse instanzieren koennen.
	 * 
	 */
	private RandomisationDB() {
	}

	/**
	 * Speichert einen Block in der Datenbank.
	 * 
	 * @param block
	 *            Der zu speichernde Block.
	 * @param s
	 *            Die Studie zu der der Block gespeichert werden soll.
	 * @throws DatenbankExceptions
	 *             Bei Fehlern in der Datenbank.
	 * @throws RandomisationsException
	 *             Tritt in der Form
	 *             {@link RandomisationsException#NOCH_RANDOMISATIONS_WERTE_VORHANDEN}
	 *             auf, falls versucht wird einen Block zu speichern, zu dem
	 *             noch Werte existieren.
	 */
	public static synchronized void speichernBlock(long[] block, StudieBean s,
			String strataKombination) throws RandomisationsException,
			DatenbankExceptions {

		Connection c = ConnectionFactory.getInstanz().getConnection();
		String sql = "";
		try {

			sql = "SELECT COUNT(" + Block.ID + ") FROM " + BLOCK_TABLE
					+ " WHERE " + Block.StudieID + " = ? ";

			if (strataKombination == null) {
				sql += " AND " + Block.KOMBINATION + " IS NULL";
			} else {
				sql += " AND " + Block.KOMBINATION + " = ?";
			}

			PreparedStatement pstmt;
			ResultSet rs;
			pstmt = c.prepareStatement(sql);

			pstmt.setLong(1, s.getId());
			if (strataKombination != null) {
				pstmt.setString(2, strataKombination);
			}

			rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);

			if (count > 0) {
				throw new RandomisationsException(
						RandomisationsException.NOCH_RANDOMISATIONS_WERTE_VORHANDEN);
			}

		} catch (SQLException e) {
			throw new DatenbankExceptions(e, sql,
					DatenbankExceptions.SUCHEN_ERR);
		}

		sql = "";

		try {

			sql = "INSERT INTO " + BLOCK_TABLE + " ( " + Block.StudieID + ", "
					+ Block.KOMBINATION + ", " + Block.WERT
					+ " ) VALUES (?,?,?)";

			for (int i = 0; i < block.length; i++) {
				PreparedStatement pstmt;
				pstmt = c.prepareStatement(sql);

				pstmt.setLong(1, s.getId());
				pstmt.setString(2, strataKombination);
				pstmt.setLong(3, block[i]);

				pstmt.execute();
			}

		} catch (SQLException e) {
			throw new DatenbankExceptions(e, sql,
					DatenbankExceptions.SCHREIBEN_ERR);
		}

		ConnectionFactory.getInstanz().closeConnection(c);

	}

	/**
	 * Gibt den naechsten Wert fuer die Randomisation zu dieser Studie zurueck.
	 * 
	 * @return Den naechsten Wert oder {@link NullKonstanten#NULL_INT} falls
	 *         keine Werte mehr zu dieser Studie existieren.
	 */
	public static synchronized long getNext(StudieBean s,
			String strataKombination) throws DatenbankExceptions {
		Connection c = ConnectionFactory.getInstanz().getConnection();
		String sql = "";
		long id = NullKonstanten.NULL_LONG;
		long wert = NullKonstanten.NULL_LONG;
		try {

			sql = "SELECT " + Block.ID + ", " + Block.WERT + " FROM "
					+ BLOCK_TABLE + " WHERE " + Block.StudieID + " = ? ";

			if (strataKombination == null) {
				sql += " AND " + Block.KOMBINATION + " IS NULL";
			} else {
				sql += " AND " + Block.KOMBINATION + " = ?";
			}
			sql += " ORDER BY " + Block.ID + " ASC";

			PreparedStatement pstmt;
			ResultSet rs;
			pstmt = c.prepareStatement(sql);

			pstmt.setLong(1, s.getId());
			if (strataKombination != null) {
				pstmt.setString(2, strataKombination);
			}

			rs = pstmt.executeQuery();
			if (rs.next()) {
				id = rs.getLong(1);
				wert = rs.getLong(2);
			}
		} catch (SQLException e) {
			throw new DatenbankExceptions(e, sql,
					DatenbankExceptions.SUCHEN_ERR);
		}

		sql = "";

		if (id != NullKonstanten.NULL_LONG) {
			try {
				sql = "DELETE FROM " + BLOCK_TABLE + " WHERE " + Block.ID
						+ " = ?";

				PreparedStatement pstmt = c.prepareStatement(sql);
				pstmt.setLong(1, id);
				pstmt.execute();
			} catch (SQLException e) {
				throw new DatenbankExceptions(e, sql,
						DatenbankExceptions.LOESCHEN_ERR);
			}
		}

		ConnectionFactory.getInstanz().closeConnection(c);
		return wert;
	}

	/**
	 * Gibt die Anzahl der Patienten zu allen Studienarmen der Studie zurueck,
	 * welche die selben Strata-Kombinationen wie die uebergebenen hat.
	 * 
	 * @param studienID
	 *            Die Id der Studie deren Studienarme untersucht werden sollen.
	 * @param strataKombination
	 *            Die Strata-Kombinationen zu denen die Anzahl der Patienten
	 *            ermittelt werden soll.
	 * @return Eine HashMap, die angibt wieviele Patienten zu einem Studienarm
	 *         zugeordnet sind. Die erste Stelle der HashMap ist mit der Id des
	 *         Studienarms als {@link Long} belegt. Die zweite Stelle der
	 *         HashMap ist mit die Anzahl der zugeordneten Patienten als
	 *         {@link Integer}.
	 */
//	 Erstmal auskommentiert, weil vermtl. nur zum testen der Stratabasierten-Blockrandomsation noetig
	/*public static HashMap<Long, Integer> getAnzahlPatientenZuStudienarmen(
			long studienID, String strataKombination) {
		return null;
	}*/

	/**
	 * Gibt die Anzahl der Patienten zu allen Studienarmen der Studie zurueck.
	 * 
	 * @param studienID
	 *            Die Id der Studie deren Studienarme untersucht werden sollen.
	 * @return Eine HashMap, die angibt wieviele Patienten zu einem Studienarm
	 *         zugeordnet sind. Die erste Stelle der HashMap ist mit der Id des
	 *         Studienarms als {@link Long} belegt. Die zweite Stelle der
	 *         HashMap ist mit die Anzahl der zugeordneten Patienten als
	 *         {@link Integer}.
	 * @throws DatenbankExceptions 
	 */
	public static HashMap<Long, Integer> getAnzahlPatientenZuStudienarmen(
			long studienID) throws DatenbankExceptions {
		HashMap<Long, Integer> patInArmen = new HashMap<Long, Integer>();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		Connection con=null;
		Connection con2=null;
		String sql ="SELECT studienarm.studienarmID FROM studienarm, studie WHERE studie.studienID=? AND studie.studienID = studienarm.studienarmID " ;
		String sql2 ="select count(*) anzahl from studienarm, patient where patient.studienarm_studienarmID = studienarm.studienarmID and studienarm.studienarmid=?";
		try {
			con = ConnectionFactory.getInstanz().getConnection();
			con2 = ConnectionFactory.getInstanz().getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, studienID);
			rs = pstmt.executeQuery();
			while(rs.next()) {				
				pstmt2 = con2.prepareStatement(sql2);
				pstmt2.setLong(1, rs.getLong("studienarmID")); 
				rs2 = pstmt2.executeQuery();
				if(rs2.next()) { 
					patInArmen.put(rs.getLong("studienarmID"), rs2.getInt("anzahl"));	
				}				
			}			
		} catch (DatenbankExceptions e) {
			throw e;
		} catch (SQLException e) {
			throw new DatenbankExceptions(e,sql,DatenbankExceptions.SUCHEN_ERR);
		} finally {
			ConnectionFactory.getInstanz().closeConnection(con);
			ConnectionFactory.getInstanz().closeConnection(con2);
		}
		
		return patInArmen;
	}

}
