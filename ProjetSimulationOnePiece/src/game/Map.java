package game;

import java.util.ArrayList;
public class Map {
	ArrayList<ArrayList<Case>> map;
	final int TAILLE_MAP = 60;
	final int NB_HUMAINS = 6;
	final int NB_GEANTS = 4;
	final int NB_HOMME_POISSONS = 5;
	final int NB_NAINS = 7;
	final int NB_MONTAGNES = 5;
	final int NB_EAU = 6;
	final int NB_PONEGLYPHES = 4;
	
	public Map() {
		
		System.out.println("GENERATION DE LA MAP...");
		printPropertiesSimulation();
		map = new ArrayList<ArrayList<Case>>();
		
		
	}
	
	private void generateMap() {
		for(int i = 0; i < TAILLE_MAP; i++) {
			for(int j = 0; j < TAILLE_MAP; j++) {
				if(i<10 && j<10) {
					// Case safe pour les Humains
					Case c = new Case(i,j,true,false,false,false);				
				}
				else if(i>=50 && j<10) {
					// Case safe pour les Homme-Poissons
					Case c = new Case(i,j,false,true,false,false);
				}
				else if(i < 10 && j >= 50) {
					// Case safe pour les Géants
					Case c = new Case(i,j,false,false,true,false);
				}
				else if(i >= 50 && j >= 50) {
					// Case safe pour les Nains
					Case c = new Case(i,j,false,false,false,true);
				}
				else {
					// Le reste du plateau
				}
			}
		}
	}
	private void printPropertiesSimulation() {
		System.out.println("");
		Utilitaires.repeat("*",80);
		System.out.println(" Taille de la map : " + TAILLE_MAP + "x" + TAILLE_MAP);
		System.out.println(" Nombre d'humains : " + NB_HUMAINS);
		System.out.println(" Nombre de géants : " + NB_GEANTS);
		System.out.println(" Nombre de nains : " + NB_NAINS);
		System.out.println(" Nombre d'homme poissons : " + NB_HOMME_POISSONS);
		System.out.println(" Nombre de cases obstacles type 'eau' : "+ NB_EAU);
		System.out.println(" Nombre de cases obstacles type 'montagne' : " + NB_MONTAGNES);
		System.out.println(" Nombre de ponéglyphes : " + NB_PONEGLYPHES);
		System.out.println(" Les alliances sont");
		System.out.println("     - Humains et Nains sous la bannière des marines");
		System.out.println("     - Homme-Poissons et Géants sous la bannière des pirates");
		Utilitaires.repeat("*",80);
		System.out.println("\n\n\n\n");
		
	}
	
	public void printMap() {
		System.out.println("a faire");
	}
	
	
}
