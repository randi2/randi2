package de.randi2.datenbank;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import de.randi2.datenbank.exceptions.DatenbankExceptions;
import de.randi2.model.exceptions.RandomisationsException;
import de.randi2.model.exceptions.StrataException;
import de.randi2.model.fachklassen.Strata;
import de.randi2.model.fachklassen.beans.StrataAuspraegungBean;
import de.randi2.model.fachklassen.beans.StrataBean;
import de.randi2.model.fachklassen.beans.StudieBean;
import de.randi2.utility.NullKonstanten;
import de.randi2.utility.Tabelle;

/**
 * Die Klasse Block bietet einen Persitenzmechanismus fuer die Block-basierten
 * Randomisationen an.
 * 
 * @author Johannes Thoenes [jthoenes@stud.hs-heilbronn.de]
 * @version $Id: Block.java 2445 2007-05-07 09:13:25Z jthoenes $
 */
public final class RandomisationDB {

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
		ID("blockId"), StudieID("Studie_studienID"), WERT("blockwert"), KOMBINATION(
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
				// throw new RandomisationsException(
				// RandomisationsException.NOCH_RANDOMISATIONS_WERTE_VORHANDEN);
			}

		} catch (SQLException e) {
			ConnectionFactory.getInstanz().closeConnection(c);
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

				Logger l = Logger.getLogger(RandomisationDB.class);
				l.debug(s.getId() + "," + strataKombination + "," + block[i]);

				pstmt.execute();
			}

		} catch (SQLException e) {
			throw new DatenbankExceptions(e, sql,
					DatenbankExceptions.SCHREIBEN_ERR);
		} finally{
			ConnectionFactory.getInstanz().closeConnection(c);
		}

	}

	/**
	 * Gibt den naechsten Wert fuer die Randomisation zu dieser Studie zurueck.
	 * 
	 * @param s
	 *            Studie zu der der naechste Wert gesucht werden soll.
	 * @param strataKombination
	 *            String aus der Methode
	 *            {@link Strata#getStratakombinationsString(java.util.Collection)}
	 *            oder {@link Strata#getStratakombinationsString(HashMap)} der
	 *            die Strata-Kombination des zu Randomisierenden Patienten
	 *            enthaelt.
	 * @return Den naechsten Wert oder {@link NullKonstanten#NULL_LONG} falls
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
			ConnectionFactory.getInstanz().closeConnection(c);
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
			finally{
				ConnectionFactory.getInstanz().closeConnection(c);
			}
		}
		

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
	 * @throws DatenbankExceptions
	 */
	// Erstmal auskommentiert, weil vermtl. nur zum testen der
	// Stratabasierten-Blockrandomsation noetig
	public static HashMap<Long, Integer> getAnzahlPatientenZuStudienarmen(
			long studienID, String strataKombination)
			throws DatenbankExceptions {
		HashMap<Long, Integer> patInArmen = new HashMap<Long, Integer>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;
		String sql = "select st.studienarmID,count(p.patientenID) anzahl from studie s, studienarm st, patient p "
				+ "where s.studienID=st.Studie_studienID and p.Studienarm_studienarmID=st.studienarmID and s.studienID=? and s.strata_gruppe = ? group by st.studienarmID";
		try {
			con = ConnectionFactory.getInstanz().getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, studienID);
			pstmt.setString(2, strataKombination);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				patInArmen.put(rs.getLong("st.studienarmID"), rs
						.getInt("anzahl"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new DatenbankExceptions(e, sql,
					DatenbankExceptions.SUCHEN_ERR);
		} finally {
			ConnectionFactory.getInstanz().closeConnection(con);
		}

		return patInArmen;
	}

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
		ResultSet rs = null;
		Connection con = null;
		String sql = "select st.studienarmID,count(p.patientenID) anzahl from studie s, studienarm st, patient p where s.studienID=st.Studie_studienID and p.Studienarm_studienarmID=st.studienarmID and s.studienID=? group by st.studienarmID";
		try {
			con = ConnectionFactory.getInstanz().getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, studienID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				patInArmen.put(rs.getLong("st.studienarmID"), rs
						.getInt("anzahl"));
			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new DatenbankExceptions(e, sql,
					DatenbankExceptions.SUCHEN_ERR);
		} finally {
			ConnectionFactory.getInstanz().closeConnection(con);
		}

		return patInArmen;
	}

	public static Tabelle getStatistikTabelle(StudieBean studie)
			throws DatenbankExceptions, StrataException {

		TreeSet<StrataBean> strata = new TreeSet<StrataBean>(Strata
				.getAll(studie));

		String kopfZeile[] = new String[6 + strata.size()];
		int i = 0;
		kopfZeile[i] = "Studienarm";
		i++;
		kopfZeile[i] = "Geburtsdatum";
		i++;
		kopfZeile[i] = "Geschlecht";
		i++;
		kopfZeile[i] = "Aufklärungsdatum";
		i++;
		kopfZeile[i] = "Körperoberfläche";
		i++;
		kopfZeile[i] = "Performancestatus";
		i++;

		for (StrataBean sB : strata) {
			kopfZeile[i] = sB.getName();
			i++;
		}

		Tabelle tab = new Tabelle(kopfZeile);

		HashMap<String, String> strataAusprMap = new HashMap<String, String>();
		for (StrataBean sB : strata) {
			for (StrataAuspraegungBean sA : sB.getAuspraegungen()) {
				String key = String.valueOf(sB.getId()) + "=" + String.valueOf(sA.getId());
				strataAusprMap.put(key, sA.getName());
			}
		}

		String zeile[] = new String[6 + strata.size()];

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection con = null;

		String sql = "SELECT sA.bezeichnung, p.geburtsdatum, p.geschlecht, "
				+ "p.aufklaerungsdatum, p.koerperoberflaeche, p.performancestatus "
				+ ", p.strata_gruppe FROM Studie s "
				+ "JOIN Studienarm sA ON (s.studienID = sA.Studie_studienID) "
				+ "JOIN Patient p ON(sA.studienarmID = p.Studienarm_studienarmID) "
				+ "WHERE s.studienID = ?";

		try {
			con = ConnectionFactory.getInstanz().getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, studie.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				i = 0;
				zeile[i] = rs.getString("sA.bezeichnung");
				i++;
				java.sql.Date gebSql = rs.getDate("p.geburtsdatum");
				java.util.Date gebUtil = new Date(gebSql.getTime());
				zeile[i] = DateFormat.getDateInstance(DateFormat.MEDIUM)
						.format(gebUtil);
				i++;
				zeile[i] = rs.getString("p.geschlecht");
				i++;
				java.sql.Date aufSql = rs.getDate("p.aufklaerungsdatum");
				java.util.Date aufUtil = new Date(aufSql.getTime());
				zeile[i] = DateFormat.getDateInstance(DateFormat.MEDIUM)
						.format(aufUtil);
				;
				i++;
				zeile[i] = rs.getString("p.koerperoberflaeche");
				i++;
				zeile[i] = rs.getString("p.performancestatus");
				i++;

				String strataGruppe = rs.getString("p.strata_gruppe");
				if (strataGruppe != null) {
					String strataAusp[] = strataGruppe.split("#");
					for (int j = 0; j < strataAusp.length; j++) {
						if (!strataAusp[j].trim().equals("")) {
							zeile[i] = strataAusprMap.get(strataAusp[j].trim());
							i++;
						}
					}
				}

				tab.addZeile(zeile.clone());

			}
			pstmt.close();
			rs.close();
		} catch (SQLException e) {
			throw new DatenbankExceptions(e, sql,
					DatenbankExceptions.SUCHEN_ERR);
		} finally {
			ConnectionFactory.getInstanz().closeConnection(con);
		}

		return tab;

	}

}
