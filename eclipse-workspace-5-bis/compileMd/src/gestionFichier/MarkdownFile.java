package gestionFichier;

import java.nio.file.Path;
import texteFichier.ICompilableFile;

/**
 * Classe gérant les fichiers markdown. Ces fichiers doivent être comptés, compilés, mais pas déplacé.
 * @author nicolas
 *
 */
public class MarkdownFile extends TextFile implements ICompilableFile {

	/**
	 * Constructeur explicite de la classe.
	 * @param cheminDuFichier
	 */
	protected MarkdownFile(Path cheminDuFichier) {
		super(cheminDuFichier);
	}
	
	/**
	 * Point d'entrée principal de la classe qui lance les actions devant être effectuées.
	 */
	@Override
	public void action() {
		
		//Projet.ajouterALaCompilation(this);
		
		//System.out.println("MarkdownFile : " + this.nomDuFichier +
		//		" NbrMots : " + this.getNbrMots() + 
		//		" NbrCaracteres : " + this.getNbrCaracteres());
	}

	@Override
	public boolean isNouveauChapitre() {
		
		String nomDuFichier = getChemin().getFileName().toString();
		//String finNomFichier = nomDuFichier.substring(nomDuFichier.length() - 4, nomDuFichier.length() - 3);
		
		if(nomDuFichier.endsWith("-.md")) {
		//if(finNomFichier.equals("-")) {
			return true;
		}
		
		return false;
	}
	
}
