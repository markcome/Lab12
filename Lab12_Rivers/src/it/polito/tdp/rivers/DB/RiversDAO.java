package it.polito.tdp.rivers.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

public class RiversDAO {
	
	public List<Flow> getFlows(River river) {
		
		final String sql = "SELECT id, day, flow "
				+ "FROM flow "
				+ "WHERE river = ?";
		List<Flow> misurazioni = null;
		
		try {
			misurazioni = new ArrayList<Flow>();
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setInt(1, river.getId());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Flow flow = new Flow(rs.getInt("id"), rs.getDate("day").toLocalDate(), rs.getFloat("flow"), river);
				misurazioni.add(flow);
			}
			
			st.close();
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database");
		}
		
		return misurazioni;
	}

	public Map<Integer, River> getAllRiver() {
		
		final String sql = "SELECT * FROM river";
		Map<Integer, River> rivers = null;
		
		try {
			rivers = new HashMap<Integer, River>();
			
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				River river = new River(rs.getInt("id"), rs.getString("name"));
				rivers.put(river.getId(), river);
			}
			
			st.close();
			conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database");
		}
		
		return rivers;
}
	
	

}
