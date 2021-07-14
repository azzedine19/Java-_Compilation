package texteFichier;

import java.nio.file.Path;
import java.util.List;

import gestionFichier.WorkFolder;

/**
 * Interface devant être implémentée par tous les fichiers compilables.
 * @author nicolas
 *
 */
public interface ICompilableFile {

	/**
	 * Chemin physique du fichier sur le disque dur.
	 * @return Chemin
	 */
	public Path getChemin();
	
	/**
	 * Permets d'obtenir toutes les lignes qui sont présentes dans le fichier.
	 */
	public List<String> getlignes();
	
	/**
	 * Permet de savoir si le fichier est un nouveau chapitre.
	 * @return Vrai si le nom termine par "-"
	 */
	public boolean isNouveauChapitre();
	
	/**
	 * Permets d'obtenir le nom du projet qui est trouvé à partir du nom du fichier ou du nom du répertoire de travail.
	 * @return Le nom du projet
	 * @throws Exception 
	 */
	public default String getNomDuProjet() {
		
		String nomDuProjet;
		String nomDuFichier = getChemin().getFileName().toString();
		
		int indexUnder = nomDuFichier.indexOf("_");
		
		if(indexUnder == -1) {
			
			try {
				nomDuProjet = WorkFolder.getFolder().getNomDuProjetDefaut();
			} catch (Exception e) {
				nomDuProjet = "erreur_nom_projet";
			}
			
		} else {
			
			nomDuProjet = nomDuFichier.substring(0, indexUnder);
			
		}
		
		return nomDuProjet;
	}
	
	public default String getTexte(String entete) {
		
		String texteCompile = entete;
		
		for (String ligne : this.getlignes()) {
			
			if(ligne.isEmpty() == false) {
				
				texteCompile += System.lineSeparator() + System.lineSeparator() + ligne;
				
			}
			
		}
		
		return texteCompile;
		
	}
	
}
