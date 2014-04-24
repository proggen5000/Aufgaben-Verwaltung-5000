package database;

import java.sql.*;

public class Queries {
	private Queries(){}

	/**
	 * F�hrt eine SQL-Abfrage mit eindeutigem R�ckgabewert aus.
	 * @param table Tabelle, in der gesucht werden soll.
	 * @param column Spalte(n), in der gesucht werden soll.
	 * @param where Bedingung, mit der gefiltert werden soll.
	 * @return Object Eindeutiges Ergebnis der Datenbankabfrage
	 * @throws SQLException Wird bei Fehlern bei Ausf�hrung der Anweisung geworfen.
	 */
	public static Object scalarQuery(String table, String column, String where) throws SQLException{
		PreparedStatement query;
		query = Connect.getConnection().prepareStatement("SELECT "+column+" FROM "+table+" WHERE "+where);
		ResultSet rs = query.executeQuery();
		return rs.getObject(0);	
	}
	
	/**
	 * F�hrt eine SQL-Abfrage mit eindeutigem R�ckgabewert aus.
	 * @param sql SELECT-Abfrage mit eindeutigem Ergebnis
	 * @return Object Eindeutiges Ergebnis der Datenbankabfrage.
	 * @throws SQLException Wird bei Fehlern bei Ausf�hrung der SQL-Anweisung geworfen.
	 */
	public static Object  scalarQuery(String sql) throws SQLException{
			Statement query = Connect.getConnection().createStatement();
			ResultSet rs = query.executeQuery(sql);
			return rs.getObject(0);
	}
	
	/**
	 * F�hrt eine SQL-Abfrage mit Zeilen als R�ckgabewert aus.
	 * @param table Tabelle, in der gesucht werden soll.
	 * @param column Spalte(n), in der gesucht werden soll.
	 * @param where Bedingung, mit der gefiltert werden soll.
	 * @return ResultSet Repr�sentiert das Abfrageergebnis der SQL-Abfrage
	 * @throws SQLException Wird bei Fehlern bei Ausf�hrung der SQL-Anweisung geworfen.
	 */
	public static ResultSet rowQuery(String column, String table, String where) throws SQLException{
		PreparedStatement query;
		query = Connect.getConnection().prepareStatement("SELECT "+column+" FROM "+table+" WHERE "+where);
		ResultSet rs = query.executeQuery();
		return rs;
	}
	
	/**
	 * F�hrt eine SQL-Abfrage mit Zeilen als R�ckgabewert aus.
	 * @param sql SQL-Abfrage mit Zeilen als R�ckgabewert.
	 * @return ResultSet Repr�sentiert das Abfrageergebnis der SQL-Abfrage
	 * @throws SQLException Wird bei Fehlern bei Ausf�hrung der SQL-Anweisung geworfen.
	 */
	public static ResultSet rowQuery(String sql) throws SQLException{
		Statement query = Connect.getConnection().createStatement();
		ResultSet rs = query.executeQuery(sql);
		return rs;	
	}
	
	/**
	 * F�hrt ein Update-Befehl aus.
	 * @param table Tabelle, die aktualisiert werdensoll.
	 * @param updateString Gibt Atrribute und Parameter an, die aktualisiert werden.
	 * @param where Gibt eine Bedingung an, welche Datens�tze aktualisiert werden.
	 * @return boolean Gibt an, ob das Update erfolgreich ausgef�hrt wurde.
	 * @throws SQLException Wird bei Fehlern bei Ausf�hrung der SQL-Anweisung geworfen.
	 */
	public static boolean updateQuery(String table, String updateString, String where) throws SQLException{
		PreparedStatement query = Connect.getConnection().prepareStatement("UPDATE "+table+" SET "+updateString+" WHERE "+where);
		query.executeQuery();
		return(query.getUpdateCount()>0);
	}
	
	/**
	 * F�hrt ein Update-Befehl aus.
	 * @param sql Vollwertige SQL-Update-Anweisung
	 * @return boolean Gibt an, ob das Update erfolgreich ausgef�hrt wurde.
	 * @throws SQLException Wird bei Fehlern bei Ausf�hrung der SQL-Anweisung geworfen.
	 */
	public static boolean updateQuery(String sql) throws SQLException{
		Statement query = Connect.getConnection().createStatement();
		query.executeUpdate(sql);
		return (query.getUpdateCount()>0);
	}

	/**
	 * F�hrt ein DELETE-Befehl aus.
	 * @param table Tabelle, in der der Datensatz gel�scht werden soll.
	 * @param where Bedingung, mit der die zu l�schenden Datens�tze ausgew�hlt werden sollen.
	 * @return boolean Gibt an, ob der Befehl erfolgreich ausgef�hrt wurde.
	 * @throws SQLException Wird bei Fehlern bei Ausf�hrung der SQL-Anweisung geworfen.
	 */
	public static boolean deleteQuery(String table, String where) throws SQLException{
		PreparedStatement query = Connect.getConnection().prepareStatement("DELETE FROM "+table+" WHERE "+where);
		query.executeQuery();
		return true;
	}
	 
	/**
	 * F�hrt einen DELETE-Befehl aus.
	 * @param sql Vollwertige SQL-Delete-Anweisung
	 * @return boolean Gibt an, ob der Befehl erfolgreich ausgef�hrt wurde.
	 * @throws SQLException
	 */
	public static boolean deleteQuery(String sql) throws SQLException{
		Statement query = Connect.getConnection().createStatement();
		query.executeUpdate(sql);
		return (query.getUpdateCount()>0);
	}
	
	/**
	 * F�hrt einen INSERT-Befehl aus.
	 * @param table Tabelle, in der ein Datensatz eingef�gt werden soll.
	 * @param columns Spalten, in die Daten eingetragen werden sollen.
	 * @param values Werte, die in die Spalten eingetragen werden sollen.
	 * @return int Der Prim�rschl�ssel des eingef�gten Datensatzes.
	 * @throws SQLException Wird bei Fehlern bei Ausf�hrung der SQL-Anweisung geworfen.
	 */
	public static int insertQuery(String table, String columns, String values) throws SQLException{
		PreparedStatement query = Connect.getConnection().prepareStatement("INSERT INTO "+table+" ("+columns+") VALUES ("+values+")");
		query.executeQuery();
		ResultSet generatedKeys = query.getGeneratedKeys();
		if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creating row failed, no generated key obtained.");
        }
	}
	
	/**
	 * F�hrt einen INSERT-Befehl aus.
	 * @param sql Vollwertige SQL-Insert-Anweisung.
	 * @return int Der Prim�rschl�ssel des eingef�gten Datensatzes.
	 * @throws SQLException Wird bei Fehlern bei Ausf�hrung der SQL-Anweisung geworfen.
	 */
	public static int insertQuery(String sql) throws SQLException{
		Statement query = Connect.getConnection().createStatement();
		query.executeUpdate(sql);
		ResultSet generatedKeys = query.getGeneratedKeys();
		if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new SQLException("Creating row failed, no generated key obtained.");
        }
	}
}
