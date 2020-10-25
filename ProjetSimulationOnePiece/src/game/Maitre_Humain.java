package game;


public class Maitre_Humain extends Humains {
	
	
	private Maitre_Humain() {
	}
	
	private static Maitre_Humain instance = null;

    public static final Maitre_Humain getInstance() 
    {
    	if (instance == null) {
    		instance = new Maitre_Humain();
    		System.out.println(" Salut je suis l'unique "+instance.toString());
    		
    	}
    		
        return instance;
    }



}
