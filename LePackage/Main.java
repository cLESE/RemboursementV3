package LePackage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import LePackage.Calcul;

/**
 * main est le programme principal qui va permettre la création des liste pour aller retour et aller simple, la saisie de l'utilisateur et le calcul du remboursement.
 *
 * @author Clément Sébillet
 * @version 1.0
 */

public class Main {

	public static void main(String[] args) throws SQLException {

		try{
    		Class.forName("org.postgresql.Driver");
    	} catch (Exception e) {
    		System.out.println("Where is your PostgreSQL JDBC Driver? "
					+ "Include in your library path!");
			e.printStackTrace();
    	}//Fin catch

		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:postgresql://172..16.99.2:5432/csebillet","c.sebillet" ,"passe");


		Statement maRequete = connection.createStatement();
		String texteRequete = "select … from … where … ";

		// définition de l'objet qui récupérera le résultat de l'exécution de la requête :

		ResultSet curseurResultat = maRequete.executeQuery(texteRequete);

		// récupération des détails du résultats

		ResultSetMetaData detailsDonnees = curseurResultat.getMetaData();

		// crÃ©ation d'une liste
		List<AR> maListeAR =  new ArrayList<AR>();
		List<AS> maListeAS =  new ArrayList<AS>();

		String monFichierTarif = "/home/etudiant/SLAMJAVA/RemboursementV2/src/LePackage/tarif.txt";

		try{
    		//Ouverture d'un flux d'entrée à partir du fichier "docTarif.txt"
        	BufferedReader monBuffer = new BufferedReader(new FileReader(monFichierTarif));
        	String line = null;					//Variable qui contiendra chaque ligne du fichier

        	//Tant qu'il reste une ligne au fichier
        	while ((line = monBuffer.readLine()) != null)
        	{
        		//On découpe la ligne gràce au caractère ","
        		String[] part = line.split(",");
        		//On ajoute un objet de la classe Tarif à la brochure, à partir de la ligne du fichier découpée
        		maListeAR.add(new AR(Integer.parseInt(part[0]),Double.parseDouble(part[1]),Double.parseDouble(part[4]),Double.parseDouble(part[7]),
        							Double.parseDouble(part[2]),Double.parseDouble(part[5])));
        		maListeAS.add(new AS(Integer.parseInt(part[0]),Double.parseDouble(part[1]),Double.parseDouble(part[5]),Double.parseDouble(part[7]),
						Double.parseDouble(part[3]),Double.parseDouble(part[6])));
        	}
        	//Fermeture du buffer
        	monBuffer.close();
		} catch (Exception e) {
		    System.out.println("Fichier contenant les tarifs introuvable !!!");
		  }

		int i;
		/*
		// ajout d'Ã©lÃ©ments Ã  la liste
		for (i=0;i<10;i++){
			maListeAR.add(new AR((int)dept[i][0], dept[i][1], dept[i][4], dept[i][7],
					dept[i][2], dept[i][5]));
			maListeAS.add(new AS((int)dept[i][0], dept[i][1], dept[i][5], dept[i][7],
					dept[i][3], dept[i][6]));
		}
		*/

		Saisie maSaisie = new Saisie();

		boolean saisieOK = false;

		do{

			boolean trouve = false;
			i = 0;

			while(!trouve && i<maListeAR.size()){
				if(maSaisie.getNumDept()==maListeAR.get(i).getDept()){
					trouve = true;
				}else{
					i++;
				}
			}

			if(trouve){
				saisieOK = true;
			}
			else{
				Scanner deptObjet = new Scanner(System.in);
				System.out.println("Département non trouvé; veuillez resaisir");
				maSaisie.setNumDept(deptObjet.nextInt());
			}
		}while(!saisieOK);

		System.out.println("Résultat : " + Calcul.calculer(i, maListeAR, maListeAS, maSaisie));

	}

}