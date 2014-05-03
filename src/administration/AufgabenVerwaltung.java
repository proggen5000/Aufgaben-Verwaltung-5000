package administration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Queries;
import entities.Aufgabe;



public class AufgabenVerwaltung {
	/**
	 * Ersellt eine neue Aufgaben in der Datenbank
	 * @param aufgabe die gespeichert werden soll
	 * @return Aufgabe, so wie sie in der Datenbank gespeichert wurde
	 */
	public static Aufgabe neu (Aufgabe aufgabe){
		// returns null if error else returns inserted obj with ID			

		String values = aufgabe.getGruppe().getId() + ", " 
				+ aufgabe.getErsteller().getId() + ", '"
				+ aufgabe.getName() + "', '" 
				+ aufgabe.getBeschreibung() + "', " 
				+ aufgabe.getStatus() + ", "
				+ aufgabe.getErstellungsdatum() + ", "
				+ aufgabe.getDeadline();
		long id = -1;
		try {
			id = Queries.insertQuery("Aufgaben", "AufgabenGruppeID, ErstellerID, Name, Beschreibung, Status, Erstellungsdatum, Deadline", values);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (id == -1){
			return null; 
		}
		else{
			return get(id);
		}
	}

	/**
	 * Die Daten werden auf die des übergebenen Objekts geupdated.
	 * @param aufgabe die aktualisiert werden soll. 
	 * @return Aufgabe so wie sie in der Datenbank steht
	 */
	public static Aufgabe bearbeiten (Aufgabe aufgabe){
		//Aktualisieren der Aufgabe
		String table = "Aufgaben";
		// AufgabenGruppeID, ErstellerID, Name, Beschreibung, Status, Erstellungsdatum, Deadline
		String updateString = "AufgabenGruppeID = " + aufgabe.getGruppe().getId()
				+ ", ErstellerID = " + aufgabe.getErsteller().getId() + ", Name = '" + aufgabe.getName()
				+ "', Beschreibung = '" + aufgabe.getBeschreibung() + "', Status = " + aufgabe.getStatus()
				+ ", Erstellungsdatum = " + aufgabe.getDeadline() + ", Deadline = " + aufgabe.getDeadline();
		String where = "AufgabeID = " + aufgabe.getId();

		try {
			if (Queries.updateQuery(table, updateString, where) == true) {
				return get(aufgabe.getId());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		};
		return null;
	}

	/**
	 * Löscht die übergebene Aufgabe aus der Datenbank
	 * @param aufgabe die gelöscht werden soll
	 * @return boolean ob gelöscht oder nicht
	 */
	public static boolean loeschen (Aufgabe aufgabe){
		String table = "Aufgabe";
		String where = "AufgabeID = " + aufgabe.getId();
		try {
			Queries.deleteQuery("aufgaben_dateien", "AufgabeID = " + aufgabe.getId());
			Queries.deleteQuery("aufgaben_mitglieder", "AufgabeID = " + aufgabe.getId());
			return Queries.deleteQuery(table, where);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Überprüft, ob eine Aufgabe-ID in der Datenbank vorhanden ist
	 * @param id die auf vorhandensein geprüft werden soll
	 * @return boolean ob sie vorhanden ist oder nicht
	 */
	public static boolean vorhanden (long id){
		return AufgabenVerwaltung.get(id) != null;
	}	

	/**
	 * Gibt die Aufgabe mit der angegebenen Aufgabe-ID aus der Datenbank zurück
	 * @param id der Aufgabe nach der gesucht werden soll 
	 * @return Aufgabe, nach der gesucht wurde
	 */
	public static Aufgabe get (long id){
		//Suchen der Aufgabe anhand der ID
		Aufgabe aufgabe_neu = null;
		try {
			ResultSet rs = Queries.rowQuery("*", "Aufgaben", "AufgabeID = " + id);
			rs.next();
			aufgabe_neu = createAufgabeByRow(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return aufgabe_neu;
	}

	public static ArrayList<Aufgabe> getListe(){
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM Aufgaben";
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				// AufgabenGruppeID, ErstellerID, Name, Beschreibung, Status, Erstellungsdatum, Deadline
				while(rs.next()){
					//add every result in resultset to ArrayList
					al.add(createAufgabeByRow(rs));
				}
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zur�ckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Gibt alle Aufgaben einer Datei zurück
	 * @param dateiID nach welcher gesucht werden muss
	 * @return ArrayList alle Aufgaben für die gesuchte ID
	 */
	public static ArrayList<Aufgabe> getListeVonDatei(long dateiID){
		// returnd eine ArrayListe aller Aufgabe die zu einer bestimmten datei geh�hren
		String sql = "SELECT * FROM aufgaben JOIN aufgaben_dateien ON aufgaben.AufgabeID = aufgaben_dateien.Aufgaben_AufgabeID JOIN dateien ON dateien.DateiID = aufgaben_dateien.Dateien_DateiID WHERE DateiID = " + dateiID;
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				al.add(createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine lehere Liste zur�ckgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Gibt alle Aufgaben einer Gruppe zurück
	 * @param gruppenID nach welcher gesucht werden muss
	 * @return ArrayList alle Aufgaben für die gesuchte ID
	 */
	public static ArrayList<Aufgabe> getListeVonGruppe(long gruppenID){
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM Aufgaben WHERE AufgabenGruppeID = " + gruppenID;
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				al.add(createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Findet alle Aufgaben eines bestimmten Mitglieds (sowohl erstellt, als auch zugewiesen)
	 * @param mitgliedID ID des Mitglieds
	 * @return Liste mit Aufgaben des angegebenen Mitglieds
	 */
	public static ArrayList<Aufgabe> getListeVonMitglied(long mitgliedID){
		String sql = "SELECT * FROM  `aufgaben_mitglieder` INNER JOIN  `aufgaben` USING ( AufgabeID ) WHERE MitgliedID = "+ mitgliedID +" OR ErstellerID = "+ mitgliedID +" GROUP BY AufgabeID";
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				al.add(createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}


	/**
	 * Findet alle Aufgaben eines bestimmten Teams
	 * @param teamID ID des Teams
	 * @return Liste mit Aufgaben des angegebenen Teams
	 */
	public static ArrayList<Aufgabe> getListeVonTeam(long teamID){
		String sql = "SELECT * FROM aufgaben INNER JOIN aufgabengruppen USING(AufgabenGruppeID) WHERE TEAM = " + teamID + " GROUP BY AufgabeID";
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);

			while(rs.next()){
				//add every result in resultset to ArrayList
				al.add(createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}

	/**
	 * Findet alle Aufgaben unter Angabe eines Mitglieds (sowohl erstellt, als auch zugewiesen) und eines Teams
	 * @param mitgliedID
	 * @param teamID
	 * @return Liste mit Aufgaben des Mitglieds und Teams
	 */
	public static ArrayList<Aufgabe> getListeVonMitgliedVonTeam(long mitgliedID, long teamID){
		// returnd eine ArrayListe aller Aufgabe
		String sql = "SELECT * FROM  `aufgaben_mitglieder` INNER JOIN  `aufgaben` USING ( AufgabeID ) INNER JOIN aufgabengruppen USING(AufgabenGruppeID) WHERE (MitgliedID = " + mitgliedID + " OR ErstellerID = " + mitgliedID + ") AND TEAM = " + teamID + " GROUP BY AufgabeID";
		ArrayList<Aufgabe> al = new ArrayList<Aufgabe>();
		try {
			ResultSet rs = Queries.rowQuery(sql);
			while(rs.next()){
				//add every result in resultset to ArrayList
				al.add(createAufgabeByRow(rs));
			}
		} catch (SQLException e) {
			// Falls ein Fehler auftritt soll eine leere Liste zurückgegeben werden
			e.printStackTrace();
			al = null;
		}
		return al;
	}
	/**
	 * Hilfsfunktion zum einfach erstellen einer Aufgabe aus einem ResultSet
	 * kann innerhalb des gesamten Packages verwendet werden
	 * @param rs aus dem die Aufgabe erstellt werden soll
	 * @return Aufgabe
	 */
	static Aufgabe createAufgabeByRow(ResultSet rs){
		try {
			Aufgabe a= new Aufgabe(rs.getLong("AufgabeID"),
					AufgabengruppenVerwaltung.get(rs.getLong("AufgabenGruppeID")),
					MitgliederVerwaltung.get(rs.getLong("ErstellerID")), rs.getString("Name"),
					rs.getString("Beschreibung"), rs.getInt("Status"), rs.getLong("Erstellungsdatum"), rs.getLong("Deadline"));
			return a;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
