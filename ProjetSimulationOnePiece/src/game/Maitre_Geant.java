package game;

import java.util.ArrayList;

public class Maitre_Geant extends Geant {
	
	
	private Maitre_Geant(Map m) {
		super(m);
	}
	
	private static Maitre_Geant instance = null;


    public static final Maitre_Geant getInstance(Map m) 
    {
    	if (instance == null) {
    		instance = new Maitre_Geant(m);
    		
    	}
    		
        return instance;
    }

	

}