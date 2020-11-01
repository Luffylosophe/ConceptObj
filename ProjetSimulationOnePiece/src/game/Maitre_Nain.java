package game;

import java.util.ArrayList;

public class Maitre_Nain extends Nains {
	
	
	private Maitre_Nain() {
		
	}
	
	private static Maitre_Nain instance = null;

    public static final Maitre_Nain getInstance() 
    {
    	if (instance == null) {
    		instance = new Maitre_Nain();
    		
    		
    	}
    		
        return instance;
    }
    



	
	

}

