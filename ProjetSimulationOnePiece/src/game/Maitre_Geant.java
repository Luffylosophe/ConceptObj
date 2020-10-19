package game;


public class Maitre_Geant extends Geant {
	
	
	private Maitre_Geant() {
		
	}
	
	private static Maitre_Geant instance = null;


    public static final Maitre_Geant getInstance() 
    {
    	if (instance == null) {
    		instance = new Maitre_Geant();
    		System.out.println(" Salut je suis l'unique "+instance.toString());
    		
    	}
    		
        return instance;
    }


	
	

}