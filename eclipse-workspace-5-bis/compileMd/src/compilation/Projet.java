package compilation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import gestionFichier.BaseFile;
import gestionFichier.WorkFolder;
import texteFichier.ICompilableFile;
import texteFichier.IComptableFile;

/**
 * Classe qui reprÃ©sente un projet d'Ã©criture.
 * @author nicolas
 *
 */
public final class Projet {
	
	
	/**
	 * Nom du projet.
	 */
	private String nom;
	
	/**
	 * Liste des fichiers compilables qui constituent le projet.
	 */
	private ArrayList<ICompilableFile> fichiers ;
	
	/**
	 * Liste statique de tous les projets en cour de traitement.
	 */
	private static ArrayList<Projet> projets ;
	
	/**
	 * La construction d'un projet sans nom est impossible.
	 */
	private Projet() {
		
	}
	
	/**
	 * Constructeur par dÃ©faut avec le nom.
	 * @param nomDuProjet
	 */
	private Projet(String nomDuProjet) {
		this.nom = nomDuProjet;
		this.fichiers = new ArrayList<ICompilableFile>();
	}
	
	/**
	 * MÃ©thode permÃ©tant d'ajouter un fichier Ã  la compilation en cour.
	 * Cette mÃ©thode crÃ©Ã© le projet si c'est nÃ©cessaire.
	 * @param fichierX
	 */
	public static void ajouterALaCompilation(BaseFile fichierX) {
		
		if(Projet.projets == null) Projet.projets = new ArrayList<Projet>();
		
		if(fichierX instanceof ICompilableFile) {
			
			ICompilableFile fichierAAjouter = (ICompilableFile) fichierX;
					
			Projet projetDuFichier = Projet.projets.stream()
				.filter(p -> p.nom.equals(fichierAAjouter.getNomDuProjet()))
				.findFirst()
				.orElse(null);
				//.orElse(new Projet(fichierAAjouter.getNomDuProjet()));
			
			if(projetDuFichier == null) {
				projetDuFichier = new Projet(fichierAAjouter.getNomDuProjet());
				Projet.projets.add(projetDuFichier);
				System.out.println("   CrÃ©ation du projet : " + projetDuFichier.nom);
			}
			
			projetDuFichier.fichiers.add(fichierAAjouter);
			
			System.out.println("   Un fichier a ete ajoute au projet : " + projetDuFichier.nom +
					" (total :" + projetDuFichier.fichiers.size() + ")");
			
		}
		
	}
	
	/**
	 * Permet d'effectuÃ© une compilation qui a Ã©tÃ© prÃ©parÃ©e Ã  l'avance en
	 * ajoutant les fichiers avec "ajouterALaCompilation".
	 * @param cheminRepertoireTravail
	 * @throws Exception 
	 */
	public static void lancerLaCompilation() throws Exception {
		
		System.out.println("-----------------" +
							System.lineSeparator() +
							"Compilation debut !");
		
		if(Projet.projets == null) Projet.projets = new ArrayList<Projet>();
		
		//Pour chaque projets
		for (Projet projet : projets) {
			
			String nomDuFichier = projet.nom + "_compile.txt";	//Préparation du nom du fichier final.
			
			File fichierCompile = new File(WorkFolder			//Préparation de la représentation du fichier.
					.getFolder()
					.getRepertoire()
					.toFile(),			//Conversion du path en file pour le constructeur de "File".
					nomDuFichier);
			
			if(fichierCompile.exists()) fichierCompile.delete();	//Suppression du fichier s'il existe.
			
			String texteCompile = "# " + projet.nom;	//Ajout du titre.
			
			int numChapitre = 0;	//Variable pour les numéros de chapitres (initialisé à 0).
			
			//Pour chaque fichiers contenues dans le projet.
			for (ICompilableFile fichier : projet.fichiers) {
				
				String entete = "";	//Par défaut un fichier n'a pas d'entête.
				
				if(fichier.isNouveauChapitre()) {	//Le fichier est un nouveau chapitre.
					
					numChapitre++;	//Incrémentation du nombre de chapitres.
					entete += System.lineSeparator() + System.lineSeparator() + "## " + numChapitre + ".";	//Ajout entête.
					
				} else {	//Le fichier n'est pas un nouveau chapitre
					
					//S'il ne sagit pas du premier fichier on ajoute le séparateur de scènes.
					if(!texteCompile.equals("# " + projet.nom)) entete += System.lineSeparator() + System.lineSeparator() + "##### â�‚";
					
				}
				
				
				texteCompile += fichier.getTexte(entete);
			}
			
			sauvegarderFichier(fichierCompile.toPath(), texteCompile);	//Création du fichier !
			
			System.out.println("Compilation fin !" + System.lineSeparator() + "-----------------");
		}
		
	}
	
	/**
	 * Sauve un fichier compilÃ© sur le disque dur et affiche ses statistiques.
	 * @param cheminFichier
	 * @param texteFichier
	 */
	private static void sauvegarderFichier(Path cheminFichier, String texteFichier) {
		
		try {
			
			Files.writeString(cheminFichier, texteFichier);	//Inscription du fichier
			
			IComptableFile compile = (IComptableFile) BaseFile.Fabrik(cheminFichier);	//Création d'une instance de IComptableFile
			
			System.out.println("   CrÃ©ation de : " + cheminFichier.getFileName() +
					" NbrMots : " + compile.getNbrMots() + 
					" NbrCaracteres : " + compile.getNbrCaracteres());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//static void setStatistiques(int nbMots, int nbCaracteres) {
			
		//}
	}
}
