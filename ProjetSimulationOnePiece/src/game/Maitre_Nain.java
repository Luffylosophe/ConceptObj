package game;

import java.util.ArrayList;

public class Maitre_Nain extends Nains {
	
	
	private Maitre_Nain(Map m) {
		super(m);
		nb_instances_nains--;
	}
	
	private static Maitre_Nain instance = null;

    public static final Maitre_Nain getInstance(Map m) 
    {
    	if (instance == null) {
    		instance = new Maitre_Nain(m);
    		
    		
    	}
    		
        return instance;
    }
    



	
	

}

