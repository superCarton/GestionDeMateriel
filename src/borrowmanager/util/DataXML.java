package borrowmanager.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Classe d'enregistrement de fichiers de données au format XML
 * 
 * @author F. Dechavanne
 * @version 1.0.0
 * 
 */
public abstract class DataXML {
	// Dossier de sauvegarde
	//
	private static String dossier = "";

	// XStream
	//
	private static XStream xstream = new XStream(new DomDriver());

	/**
	 * Enregistre une liste au format XML
	 * 
	 * @param liste
	 *            : la liste a enregistrer
	 * @param nom
	 *            : le nom de fichier a utiliser
	 * @param version
	 *            : la version du fichier
	 * @return succ�s de l'op�ration
	 */
	public static boolean store(List<?> liste, String nom, String version) {
		// Controler la validité des param�tres
		//
		if (liste == null || nom == null || version == null) {
			// Indiquer l'erreur
			//
			System.err.println("DataXML : Param�tre null !");
			return false;
		}

		// Construire le nom de fichier a utiliser
		//
		String nomCompletFichier = dossier + nom + "-" + version + ".xml";
		if (version == "") {
			nomCompletFichier = dossier + nom + ".xml";
		}

		// Vérifier que le dossier existe déjà
		//
		verifierExistenceDossier();

		// Créer le fichier logique
		//
		File fichier = new File(nomCompletFichier);

		// Créer un écrivain
		//
		FileWriter ecrivain;
		try {
			ecrivain = new FileWriter(fichier);
		} catch (Exception e) {
			System.err
					.println("DataXML :  erreur a la creation de l'ecrivain !");
			return false;
		}

		// Enregistrer le XML
		//
		xstream.toXML(liste, ecrivain);

		// Fermer le flux
		//
		try {
			ecrivain.close();
		} catch (Exception e) {
			System.err
					.println("DataXML :  erreur a la fermeture de l'ecrivain !");
			return false;
		}

		// Afficher un message
		//
		System.out.println("DataXML : enregistrement du fichier "
				+ nomCompletFichier + " OK");
		return true;
	}

	/**
	 * Enregistre un dictionnaire au format XML
	 * 
	 * @param dictionnaire
	 *            : le dictionnaire a enregistrer
	 * @param nom
	 *            : le nom de fichier a utiliser
	 * @param version
	 *            : la version du fichier
	 * @return succès de l'opération
	 */
	public static boolean store(Map<?, ?> dictionnaire, String nom,
			String version) {
		// Controler la validité des paramètres
		//
		if (dictionnaire == null || nom == null || version == null) {
			// Indiquer l'erreur
			//
			System.err.println("DataXML : Param�tre null !");
			return false;
		}

		// Construire le nom de fichier a utiliser
		//
		String nomCompletFichier = dossier + nom + "-" + version + ".xml";
		if (version == "") {
			nomCompletFichier = dossier + nom + ".xml";
		}

		verifierExistenceDossier();

		// Créer le fichier logique
		//
		File fichier = new File(nomCompletFichier);

		// Créer un écrivain
		//
		FileWriter ecrivain;
		try {
			ecrivain = new FileWriter(fichier);
		} catch (Exception e) {
			System.err
					.println("DataXML :  erreur a la creation de l'ecrivain !");
			return false;
		}

		// Enregistrer le XML
		//
		xstream.toXML(dictionnaire, ecrivain);

		// Fermer le flux
		//
		try {
			ecrivain.close();
		} catch (Exception e) {
			System.err
					.println("DataXML :  erreur a la fermeture de l'ecrivain !");
			return false;
		}

		// Afficher un message
		//
		System.out.println("DataXML : enregistrement du fichier "
				+ nomCompletFichier + " OK");
		return true;
	}

	/**
	 * Charge une liste ou un dictionnaire enregistré au format XML
	 * 
	 * @param nom
	 *            : le nom de fichier a charger
	 * @param version
	 *            : la version du fichier
	 * @return le dictionnaire ou la liste chargée, null si une erreur est
	 *         survenue
	 */
	public static Object load(String nom, String version) {
		// Controler la validité des paramètres
		//
		if (nom == null || version == null) {
			// Indiquer l'erreur
			//
			System.err.println("DataXML : Param�tre null !");
			return false;
		}

		// Construire le nom de fichier a utiliser
		//
		String nomCompletFichier;
		if (version.equals("")) {
			nomCompletFichier = dossier + nom + ".xml";
		} else {
			nomCompletFichier = dossier + nom + "-" + version + ".xml";
		}

		// Créer le fichier logique
		//
		File fichier = new File(nomCompletFichier);

		// Créer un écrivain
		//
		FileReader lecteur;
		try {
			lecteur = new FileReader(fichier);
		} catch (Exception e) {
			System.err.println("DataXML :  erreur a la creation du lecteur !");
			return null;
		}

		// Enregistrer le XML
		//
		Object objet = xstream.fromXML(lecteur);

		// Fermer le flux
		//
		try {
			lecteur.close();
		} catch (Exception e) {
			System.err.println("DataXML :  erreur a la fermeture du lecteur !");
			return null;
		}

		// Afficher un message
		//
		System.out.println("DataXML : Chargement du fichier "
				+ nomCompletFichier + " OK");

		return objet;
	}

	/**
	 * Définit le dossier a utiliser pour sauvegarder
	 * 
	 * @param nouveauDossier
	 *            : le nouveau dossier de sauvegarde
	 */
	public static void definirDossier(String nouveauDossier) {
		dossier = nouveauDossier;
	}

	/**
	 * Donne le dossier de sauvegarde utilisé par la classe
	 * 
	 * @return le dossier de sauvegarde actuel
	 */
	public static String recupererDossier() {
		return dossier;
	}

	/**
	 * Controle l'existence du dossier de sauvegarde et le crée si besoin est
	 */
	private static void verifierExistenceDossier() {
		// Créer le lien vers le dossier logique
		//
		File dossierCible = new File(dossier);

		// Controler que le dossier existe et que c'est bien un dossier
		//
		if (!(dossierCible.exists() && dossierCible.isDirectory())) {
			// Si ce n'est pas le cas, alors le créer
			//
			dossierCible.mkdirs();
		}
	}
}
