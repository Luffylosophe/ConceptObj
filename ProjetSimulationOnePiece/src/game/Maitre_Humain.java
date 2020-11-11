package game;

import java.util.ArrayList;

public class Maitre_Humain extends Humains {
	

	private Maitre_Humain(Map m) {
		super(m);
	}
	
	private static Maitre_Humain instance = null;

    public static final Maitre_Humain getInstance(Map m) 
    {
    	if (instance == null) {
    		instance = new Maitre_Humain(m);
    		
    	}
    		
        return instance;
    }

    
}
