package LePackage;

/**
 * AR est la classe comprenant toutes les données pour un aller retour.
 *
 * @author BaptisteDeslaurier
 * @version 1.0
 */

public class AR extends Tarif{

	private double tarifARJS;
	private double tarifARNW;

	//*************   ACCESSEURS *************
	public double getTarifARJS()
	{
	    return tarifARJS;
	}

	public double getTarifARNW()
	{
	    return tarifARNW;
	}

	//*************   CONSTRUCTEUR   *************
	AR(int dept, double priseEnCharge, double horaireJS, double horaireNW, double tarifARJS, double tarifARNW){
		super(dept, priseEnCharge, horaireJS, horaireNW);
		this.tarifARJS = tarifARJS;
		this.tarifARNW = tarifARNW;
	}
}