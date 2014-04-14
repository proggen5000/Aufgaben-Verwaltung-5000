package database;

import java.sql.*;

public class Queries {
	private Queries(){}
	
	public static Object scalarQuery(String column, String table, String where, String param, String condition) throws SQLException{
		PreparedStatement query;
		if(where == ""){
			query = Connect.getConnection().prepareStatement("SELECT "+column+" FROM "+table+" WHERE "+param+" LIKE ?");
			
		}else{
			query = Connect.getConnection().prepareStatement("SELECT "+column+" FROM "+table+" WHERE "+where +" AND "+param+" LIKE ?");
		}
		ResultSet rs = query.executeQuery();
		return rs.getObject(0);
	}
	
	public static Object  scalarQuery(String sql) throws SQLException{
		Statement query = Connect.getConnection().createStatement();
		ResultSet rs = query.executeQuery(sql);
		return rs.getObject(0);
	}
	
	public static ResultSet rowQuery(String column, String table, String where, String param, String condition) throws SQLException{
		PreparedStatement query;
		if(where == ""){
			query = Connect.getConnection().prepareStatement("SELECT "+column+" FROM "+table+" WHERE "+param+" LIKE ?");
			
		}else{
			query = Connect.getConnection().prepareStatement("SELECT "+column+" FROM "+table+" WHERE "+where +" AND "+param+" LIKE ?");
		}
		ResultSet rs = query.executeQuery();
		return rs;
	}
	
	public static ResultSet rowQuery(String sql) throws SQLException{
		Statement query = Connect.getConnection().createStatement();
		ResultSet rs = query.executeQuery(sql);
		return rs;		
	}
	
	public static boolean updateQuery(String table, String updateString, String where) throws SQLException{
		try{
			PreparedStatement query = Connect.getConnection().prepareStatement("UPDATE "+table+" SET "+updateString+" WHERE "+where);
			query.executeQuery();
			return(query.getUpdateCount()>0);
		}catch(SQLException e){
			return false;
		}	
	}
	
	public static boolean updateQuery(String sql){
		try{
			Statement query = Connect.getConnection().createStatement();
			query.executeUpdate(sql);
			return (query.getUpdateCount()>0);
		}catch (SQLException e){
			return false;
		}	
	}

	public static boolean deleteQuery(String sql) throws SQLException{
		try{
			Statement query = Connect.getConnection().createStatement();
			query.executeUpdate(sql);
			return (query.getUpdateCount()>0);
		}catch (SQLException e){
			return false;
		}		
	}
	
	public static boolean insertQuery(String sql) throws SQLException{
		try{
			Statement query = Connect.getConnection().createStatement();
			query.executeUpdate(sql);
			return (query.getUpdateCount()>0);
		}catch (SQLException e){
			return false;
		}	
	}
	
	

}