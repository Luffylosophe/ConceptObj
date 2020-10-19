package game;

<<<<<<< HEAD
public class Maitre_Homme_Poisson extends Hommes_Poissons {
	
	
	private Maitre_Homme_Poisson() {
		
	}
	
	private static Maitre_Homme_Poisson instance = null;

    public static final Maitre_Homme_Poisson getInstance() 
    {
    	if (instance == null) {
    		instance = new Maitre_Homme_Poisson();
    		System.out.println(" Salut je suis l'unique "+instance.toString());
    		
    	}
    		
        return instance;
    }


	
	
=======
public class Maitre_Homme_Poisson extends Hommes_Poissons{
>>>>>>> Prototype

}
