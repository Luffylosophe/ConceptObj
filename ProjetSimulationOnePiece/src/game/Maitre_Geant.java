package game;

import java.util.ArrayList;

public class Maitre_Geant extends Geant {
	
	
	private Maitre_Geant() {
		
	}
	
	private static Maitre_Geant instance = null;


    public static final Maitre_Geant getInstance() 
    {
    	if (instance == null) {
    		instance = new Maitre_Geant();
    		
    	}
    		
        return instance;
    }

	

}