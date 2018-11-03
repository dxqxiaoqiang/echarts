package com.app.pie_data.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager {
	
	private static DbManager dbManager;
	private static String url="jdbc:mysql://192.168.20.11:3306/sxzbdeveloper?useUnicode=true&characterEncoding=UTF-8",
	user="worker",
	pass="1234";
	public static DbManager getDbManager(){
		if(dbManager==null) dbManager = new DbManager();
		return dbManager;
	}
	public DbManager(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static  Connection getConn(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url,user,pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public void close(Connection conn,Statement st,ResultSet rs){
		if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if(st!=null){
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
//	public static void findById(Integer id) {
//	
//		String sql = "select * from   b_project";
//		PreparedStatement st = null;
//		ResultSet rs = null;
//		try {
//	st = conn.prepareStatement(sql);
//		//st.setInt(1, id);
//			rs = st.executeQuery();
//			while(rs.next()){
//				System.out.println(rs.getInt("id")+"   "+rs.getString("name"));
//			}
//	} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		finally{
//			DbManager.getDbManager().close(conn, st, rs);
//		}
//	}
//	
//   public static void main(String[] args) {
//		DbManager.findById(2);
//	}
//	
	 
	
}
