package LePackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
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

	public static void main(String[] args) {

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

    	String texteRequete = "select * from \"taxi\".dept";

		// définition de l'objet qui récupérera le résultat de l'exécution de la requête :
		ResultSet curseurResultat = null;
		try {
			curseurResultat = maReq.executeQuery(texteRequete);

			// tant qu'il y a encore une ligne résultat à lire
			while(curseurResultat.next()){
				maListeAR.add(new AR(curseurResultat.getInt(0), curseurResultat.getDouble(1) ,
						curseurResultat.getDouble(4), curseurResultat.getDouble(7), curseurResultat.getDouble(2)
						, curseurResultat.getDouble(5)));
				maListeAS.add(new AS(curseurResultat.getInt(0), curseurResultat.getDouble(1) ,
						curseurResultat.getDouble(5), curseurResultat.getDouble(7), curseurResultat.getDouble(3)
						, curseurResultat.getDouble(6)));
			 }

			// on ferme le flux résultat
			 curseurResultat.close();

			// on ferme l'objet lié à la connexion
			 maConnect.close();
		} catch (SQLException e) {
		    System.out.println("La requête ne retourne aucun résultat !!!");
		    System.exit(0);
		}

		int i;

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