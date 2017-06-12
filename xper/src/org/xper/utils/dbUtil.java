package org.xper.utils;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sun.java2d.loops.Blit;

public class dbUtil {
	Connection conn;

	public dbUtil() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://172.30.6.27:3306/ram_170105_3dma?autoReconnect=true&useSSL=false", "xper_rw", "up2nite");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public String getMstickSpec(Long id) {
		String stickSpec = "";
		try {
			Statement stmt = conn.createStatement();
			String query = "select mstickspec from stimobjdata where id = " + id;
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			stickSpec = rs.getString("mstickspec");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stickSpec;
	}

	public String getTextureType(Long id) {
		String textureType = "";
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT extractvalue(javaspec, '/StimSpec/shape/textureType') as 'tt' FROM stimobjdata WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			textureType = rs.getString("tt");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return textureType;
	}

	public boolean getDoClouds(Long id) {
		boolean doClouds = false;
		try {
			Statement stmt = conn.createStatement();
			String query = "SELECT extractvalue(javaspec, '/StimSpec/shape/doClouds') as 'dc' FROM stimobjdata WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			doClouds = Boolean.parseBoolean(rs.getString("dc"));
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return doClouds;
	}

	public RGBColor getStimColor(Long id) {
		float red = 0, green = 0, blue = 0;
		try {
			Statement stmt = conn.createStatement();

			String query = "SELECT extractvalue(javaspec, '/StimSpec/shape/color/red') as 'red' FROM stimobjdata WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			red = Float.parseFloat(rs.getString("red"));

			query = "SELECT extractvalue(javaspec, '/StimSpec/shape/color/green') as 'green' FROM stimobjdata WHERE id = " + id;
			rs = stmt.executeQuery(query);
			rs.next();
			green = Float.parseFloat(rs.getString("green"));

			query = "SELECT extractvalue(javaspec, '/StimSpec/shape/color/blue') as 'blue' FROM stimobjdata WHERE id = " + id;
			rs = stmt.executeQuery(query);
			rs.next();
			blue = Float.parseFloat(rs.getString("blue"));

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new RGBColor(red,green,blue);
	}
	
	public float[] getLightPosition(Long id) {
		float x = 0, y = 0, z = 0;
		try {
			Statement stmt = conn.createStatement();

			String query = "SELECT extractvalue(javaspec, '/StimSpec/shape/lightingPos/x') as 'x' FROM stimobjdata WHERE id = " + id;
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			x = Float.parseFloat(rs.getString("x"));

			query = "SELECT extractvalue(javaspec, '/StimSpec/shape/lightingPos/y') as 'y' FROM stimobjdata WHERE id = " + id;
			rs = stmt.executeQuery(query);
			rs.next();
			y = Float.parseFloat(rs.getString("y"));

			query = "SELECT extractvalue(javaspec, '/StimSpec/shape/lightingPos/z') as 'z' FROM stimobjdata WHERE id = " + id;
			rs = stmt.executeQuery(query);
			rs.next();
			z = Float.parseFloat(rs.getString("z"));

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new float[]{x,y,z,1f};
	}
	
	public void writeFaceSpec(Long id, String faceSpec) {
		try {
			String query = "update stimobjdata_vert set faceSpec = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, faceSpec);
			stmt.setLong(2, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void writeVertSpec(Long id, String vertSpec) {
		try {
			String query = "update stimobjdata_vert set vertSpec = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, vertSpec);
			stmt.setLong(2, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void writeThumbnail(Long id, byte[] thumbnail) {
		try {
			String query = "update thumbnail set data = ? where id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setBytes(1, thumbnail);
			stmt.setLong(2, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void finalize() {
		close();
	}

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
