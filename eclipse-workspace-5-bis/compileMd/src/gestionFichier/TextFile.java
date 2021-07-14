package gestionFichier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import texteFichier.IComptableFile;

/**
 * Classe gérant les fichiers texte simples. Ces fichiers doivent être comptés, mais pas compilé ni déplacé.
 * @author nicolas
 *
 */
public class TextFile extends BaseFile implements IComptableFile {

	/**
	 * Permets d'obtenir toutes les lignes qui sont présentes dans le fichier.
	 */
	private List<String> lignes;
	public List<String> getlignes() {
		return lignes;
	}
	
	/**
	 * Constructeur explicite de la classe.
	 * @param cheminDuFichier
	 */
	protected TextFile(Path cheminDuFichier) {
		super(cheminDuFichier);
		
		try {
			lignes = Files.readAllLines(this.getChemin());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Point d'entrée principal de la classe qui lance les actions devant être effectuées.
	 */
	@Override
	public void action() {
		
		System.out.println("TextFile : " + this.nomDuFichier +
				" NbrMots : " + this.getNbrMots() + 
				" NbrCaracteres : " + this.getNbrCaracteres());
		
	}
	
	/**
	 * Permets d'obtenir le nombre de mots contenus dans le fichier.
	 * @return Le nombre de mots contenus dans le fichier.
	 */
	@Override
	public int getNbrMots() {
		
		List<String> fichierLignes = getlignes();
		
		int nbrMots = 0;
		
		//Pattern.matches("[a-z]", "TESTING STRING a");
		
		for (String string : fichierLignes) {
			
			for (String mot : string.split(" ")) {
				
				for (char lettre : mot.toCharArray()) {
					if(Character.isLetter(lettre)) {
						nbrMots += 1;
						break;
					}
				}
				
			}
			
		}
		
		return nbrMots;
		
	}
	
	/**
	 * Permets d'obtenir le nombre de caractères contenus dans le fichier.
	 * @return Le nombre de caractères contenus dans le fichier.
	 */
	@Override
	public int getNbrCaracteres() {
		
		List<String> fichierLignes = getlignes();
		
		int nbrCaracteres = 0;
		
		for (String string : fichierLignes) {
			nbrCaracteres += string.length();
		}
		
		return nbrCaracteres;
		
	}
}
