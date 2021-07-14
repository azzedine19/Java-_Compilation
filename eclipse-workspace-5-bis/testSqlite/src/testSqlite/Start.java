/**
 * 
 */
package testSqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe de lancement du projet.
 * @author nicolas
 */
public final class Start {

	/**
	 * Point d'entré
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn = null;
        try {
        	
            // Préparation de la chaine de connexion
            String url = "jdbc:sqlite:maBaseSQLite.db";
            
            // Création de la connexion à la base de donnée
            conn = DriverManager.getConnection(url);
            
            // Sortie console indiquant que tout s'est bien déroulé
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
	}
	
	

}
