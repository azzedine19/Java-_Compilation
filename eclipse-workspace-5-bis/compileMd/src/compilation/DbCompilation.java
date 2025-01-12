package compilation;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DbCompilation {
	
	public Connection connBD; 
	   
    private static DbCompilation Connexion;
    
    public static DbCompilation getConnexion() {
       //si la connexion n'existe pas 
        if(Connexion == null) {
        	//j'instancie la connexion 
            Connexion = new DbCompilation();
        }
       
        return Connexion;
    }
	private DbCompilation() {
		
		String url = "jdbc:sqlite:C:/Users/Badro/Desktop/eclipse-workspace-5-bis/eclipse-workspace-5-bis/BDD/BDD.db";
			try {
				connBD = DriverManager.getConnection(url);
				} catch (Exception e) {
				
				System.out.println(e.getMessage());
				}
			
			insertTable();
			
		
		}
	 // methode insertion des tables
	 private void insertTable() {
		try {
			if (connBD != null) {
				
				 String sql = """
				 		CREATE TABLE IF NOT EXISTS chemin(
				 		id INTEGER PRYMARY KEY ,
				 		urlChemin text NOT NULL,
				 		last INTEGER NOT NULL
				 		)"""; 
				 
				 String sql2 ="""
				 		CREATE TABLE IF NOT EXISTS compilation(
						id INTEGER PRIMARY KEY,
						titreLivre TEXT NOT NULL,
						dtCompilation TEXT NOT NULL,
						nbMotsLivre INTEGER NOT NULL,
						nbMotsCompil INTEGER NOT NULL
						)""" ;
				 try(Statement stmt = connBD.createStatement();) {
					 stmt.execute(sql);
					 stmt.execute(sql2);
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		
	      
	     
	 }
	
	 public void finalize() {
		 try { 
			  this.connBD.close();
			 }catch(SQLException e) {
				e.printStackTrace();
			 }
	 }
	
	 // je r�cup�re l'dentifient du chemin 
	 public int getIdUrlChemin(String chemin) {
		 int Id = 0;
		 String query = " SELECT id FROM chemin WHERE urlChemin = ?";
		 try (PreparedStatement stmt = connBD.prepareStatement(query)) {
				stmt.setString(1, chemin);
				
				ResultSet result = stmt.executeQuery();
				while (result.next()) {
					Id = result.getInt("id");
				}
				//result.first();
				//return result.getInt("id");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		 		
		 return Id;
	 }
	 
	 /***
	  * Sauvegarde le chemin actif si il n'existe 
	  * pas ou l'Active si il existe
	  */
	 public void saveOrActivePath(String path) {
		 var id = getIdUrlChemin(path);
		 setActivePath(getActiveIdPath(), false);
		 if( id > 0) {
			 setActivePath(id, true);
		 }
		 else {
			 
			 String queryInsert = """
			 		INSERT INTO chemin (urlChemin, last) values(?, 1)
					""";
			 try (PreparedStatement stmt = connBD.prepareStatement(queryInsert)) {
					stmt.execute();
			} 
			 catch (Exception e) {
				System.out.println(e.getMessage());
			 }
	 	}
	 }
	 
	 
	 /***
	  * Renvoi le dernier chemin utilis�
	  */
	 public String getActivePath() {
		 String query = """
		 		SELECT urlChemin FROM chemin WHERE last = true
		 		""";
		 try (PreparedStatement stmt = connBD.prepareStatement(query)) {
				var result = stmt.executeQuery();
				// Move cursor to first row
				result.first();
				//Get result of firt column
				return result.getString(0);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}				 
				 
	 }
	 
	 /***
	  * Renvoi le dernier id du  -1 if not exist chemin utilis�
	  */
	 public int getActiveIdPath() {
		 String query = """
		 		SELECT id FROM chemin WHERE last = true
		 		""";
		 try (PreparedStatement stmt = connBD.prepareStatement(query)) {
				var result = stmt.executeQuery();
				// Move cursor to first row
				result.first();
				//Get result of firt column
				return result.getInt(0);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		}				 
				 
	 }
	 
	 public void setActivePath(int id, Boolean active) {
		 String queryUpdate = """
			 		Update chemin set last = ? where id = ?
					""";
		 try (PreparedStatement stmt = connBD.prepareStatement(queryUpdate)) {
			 stmt.setBoolean(0, active);
			 stmt.setInt(1, id);
			 stmt.executeUpdate();
		 }
		 catch (Exception e) {
				System.out.println(e.getMessage());
			}
	 }
	 //  setActivePath 
	 
	 public void setActivePath(String path, Boolean active) {
		 var id = getIdUrlChemin(path);
		 if( id > 0) {
			 String queryUpdate = """
				 		Update chemin set last = ? where id = ?
						""";
			 try (PreparedStatement stmt = connBD.prepareStatement(queryUpdate)) {
				 stmt.setBoolean(0, active);
				 stmt.setInt(1, id);
				 stmt.executeUpdate();
				 
			 }
			 catch (Exception e) {
					System.out.println(e.getMessage());
				}
		 }
	 }
}
