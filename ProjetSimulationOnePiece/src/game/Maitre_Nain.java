package game;


public class Maitre_Nain extends Nains {
	
	
	private Maitre_Nain() {
		
	}
	
	private static Maitre_Nain instance = null;

    public static final Maitre_Nain getInstance() 
    {
    	if (instance == null) {
    		instance = new Maitre_Nain();
    		System.out.println(" Salut je suis l'unique "+instance.toString());
    		
    	}
    		
        return instance;
    }


	
	

}

