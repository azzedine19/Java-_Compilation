package compileMd;

import java.util.Scanner;

import compilation.DbCompilation;
import compilation.Projet;

import gestionFichier.WorkFolder;



/**
 * Classe de lancement du projet
 * @author nicolas
 */
public class Start {

	/**
	 * M�thode de d�marage de l'application.
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args){
		
		
		
		var db = DbCompilation.getConnexion();
	
		
		Scanner keyb = new Scanner(System.in);
		System.out.println("Chemin du r�pertoire ?");
		
		String chemin = keyb.nextLine();
		keyb.close();
		
		try {
			int IdChem = 
			db.getIdUrlChemin(chemin);
			System.out.println(IdChem);
			//db.saveOrActivePath(chemin);
			
			System.out.println("Traitement du r�pertoire : '" + chemin + "'");
			
			WorkFolder.initialise(chemin);
			
			WorkFolder repertoireDeTravail = WorkFolder.getFolder();
			
			repertoireDeTravail.getFichiers().forEach(x -> x.action());
			repertoireDeTravail.getFichiers().forEach(x -> Projet.ajouterALaCompilation(x));
			
			Projet.lancerLaCompilation();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
