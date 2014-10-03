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

	public static void main(String[] args)  {

		// crÃ©ation d'une liste
		List<AR> maListeAR =  new ArrayList<AR>();
		List<AS> maListeAS =  new ArrayList<AS>();

		try{
			Class.forName("org.postgresql.Driver");
		} catch (Exception e) {
		    System.out.println("Driver PostgreSQL introuvable !!!");
		    System.exit(0);
		}

		//Création d'un objet de type Connection
    	Connection maConnect = null;

    	try{
    		String url = "jdbc:postgresql://localhost:5432/csebillet";
    		maConnect = DriverManager.getConnection(url, "c.sebillet", "passe");
    	}catch(Exception e){
    		System.out.println("Une erreur est survenue lors de la connexion à la base de donnée");
    	}

    	Statement maReq = null;

    	try{
    		maReq = maConnect.createStatement();
    	}catch(Exception e){

    	}

    	String texteRequete = "select * from \"taxi\".tarif";

		// définition de l'objet qui récupérera le résultat de l'exécution de la requête :
		ResultSet curseurResultat = null;
		try {
			curseurResultat = maReq.executeQuery(texteRequete);
		} catch (SQLException e) {

		}

		// récupération des détails du résultats
		try {
			ResultSetMetaData detailsDonnees = curseurResultat.getMetaData();
			// tant qu'il y a encore une ligne résultat à lire
			while(curseurResultat.next()){
				maListeAR.add(new AR(curseurResultat.getInt(0), curseurResultat.getDouble(1) ,Double.parseDouble(part[4]),Double.parseDouble(part[7]),
						Double.parseDouble(part[2]),Double.parseDouble(part[5])));
				maListeAS.add(new AS(Integer.parseInt(part[0]),Double.parseDouble(part[1]),Double.parseDouble(part[5]),Double.parseDouble(part[7]),
						Double.parseDouble(part[3]),Double.parseDouble(part[6])));
			 }
		} catch (SQLException e) {

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