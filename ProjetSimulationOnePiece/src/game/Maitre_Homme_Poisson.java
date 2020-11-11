package game;

import java.util.ArrayList;

public class Maitre_Homme_Poisson extends Hommes_Poissons {
	
	private Maitre_Homme_Poisson(Map m) {
		super(m);
		nb_instances_hommes_poissons--;
	}
	
	private static Maitre_Homme_Poisson instance = null;

    public static final Maitre_Homme_Poisson getInstance(Map m) 
    {
    	if (instance == null) {
    		instance = new Maitre_Homme_Poisson(m);
    		
    	}
    		
        return instance;
    }



}
