package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Adiacenza;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BordersDAO {
	
	public List<Country> loadAllCountries(Map<Integer,Country> countriesMap) {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				if(countriesMap.get(rs.getInt("ccode")) == null){
				
					Country c = new Country(
							rs.getInt("ccode"),
							rs.getString("StateAbb"), 
							rs.getString("StateNme")) ;
					countriesMap.put(c.getcCode(), c);
					list.add(c);
				} else 
					list.add(countriesMap.get(rs.getInt("ccode")));
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public List<Adiacenza> getAdiacenze(Map<Integer, Country> idMap, int anno){
		String sql = 
				"SELECT co.state1no as c1, co.state2no as c2 " + 
				"FROM contiguity AS co " + 
				"WHERE co.conttype = 1 AND co.year < ? " ;
		
		List<Adiacenza> a = new LinkedList<>() ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery() ;
			
			
			
			while( rs.next() ) {
				
				if(idMap.containsKey(rs.getInt("c1")) && idMap.containsKey(rs.getInt("c2"))){
					
					Adiacenza x = new Adiacenza(idMap.get(rs.getInt("c1")), idMap.get(rs.getInt("c2")));
					
					a.add(x);
	
				}

			}
			
			conn.close() ;
			
			return a ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	

	
	
	
}
