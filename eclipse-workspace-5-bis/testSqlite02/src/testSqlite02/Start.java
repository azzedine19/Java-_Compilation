/**
 * 
 */
package testSqlite02;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import testSqlite02.orm.Utilisateur;

/**
 * Classe de lancement du projet.
 * @author nicolas
 */
public class Start {

	/**
	 * Point d'entré
	 * @param args
	 */
	public static void main(String[] args) {
		
		ConnectionSource conn = GetConnection();
		
		if(conn != null) {
			
			try {
				
				Dao<Utilisateur, String> utilisateurDao = DaoManager.createDao(conn, Utilisateur.class);
				
				// Création de la table si elle n'existe pas
				TableUtils.createTableIfNotExists(conn, Utilisateur.class);
				
				// Création d'un nouvel utilisateur
		        Utilisateur account = new Utilisateur();
		        account.nom = "M. Dupond";
		        
		        // Ajout de l'utilisateur à la table mémoire
		        utilisateurDao.create(account);
				
		        // On teste l'existence de l'utilisateur.
		        Utilisateur accountTest = utilisateurDao.queryForId("M. Dupond");
		        System.out.println("Account: " + accountTest.nom);
		        
				//Fermeture de la connexion
				conn.close();
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		
	}
	
	/**
	 * Retourne la connexion et créé la base si nécessaire.
	 * @return La connexion configurée pour le projet.
	 */
	private static ConnectionSource GetConnection() {
		
		ConnectionSource conn = null;
		
        try {
        	
            // Préparation de la chaine de connexion pour une base mémoire
            String url = "jdbc:sqlite:maBaseSQLite.db";
            
            // create a connection source to our database
            conn = new JdbcConnectionSource(url);
            
            // Sortie console indiquant que tout s'est bien déroulé
            System.out.println("Connection to SQLite has been established.");
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return conn;
	}
}
