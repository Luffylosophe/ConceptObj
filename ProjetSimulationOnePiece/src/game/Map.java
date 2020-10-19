package game;

import java.util.ArrayList;
import java.util.Collections;
public class Map {
	private ArrayList<ArrayList<Case>> map;
	
	// --------------------------------------------------------------------
	// Listes spécifiques
	
	private ArrayList<Humains> humains = new ArrayList<Humains>();
	private ArrayList<Nains> nains = new ArrayList<Nains>();
	private ArrayList<Hommes_Poissons> homme_poissons = new ArrayList<Hommes_Poissons>();
	private ArrayList<Geant> geants = new ArrayList<Geant>();
	private ArrayList<Eau> eaux = new ArrayList<Eau>();
	private ArrayList<Montagne> montagnes = new ArrayList<Montagne>();
	private ArrayList<Cadavre> cadavre = new ArrayList<Cadavre>();
	
	// Listes générales
	
	private ArrayList<Personnage> personnages = new ArrayList<Personnage>();
	private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	
	// --------------------------------------------------------------------
	
	private final int TAILLE_MAP = 50;
	private final int NB_HUMAINS = 6;
	private final int NB_GEANTS = 4;
	private final int NB_HOMME_POISSONS = 5;
	private final int NB_NAINS = 7;
	private final int NB_MONTAGNES = 5;
	private final int NB_EAU = 6;
	private final int NB_PONEGLYPHES = 4;
	private final int TAILLE_SAFE_ZONE = 10;
	
	public Map() {
		
		System.out.println("GENERATION DE LA MAP...");
		printPropertiesSimulation();
		map = new ArrayList<ArrayList<Case>>();
		this.generateCharacters();
		this.generateMap();
		this.printMap();
		
		
	}
	
	private void generateCharacters() {
		
		for(int i = 0; i < NB_HUMAINS; i++) {
			Humains h = new Humains();
			this.humains.add(h);
			this.personnages.add(h);
		}
		for(int i = 0; i < NB_GEANTS; i++) {
			Geant g = new Geant();
			this.geants.add(g);
			this.personnages.add(g);
		}
		for(int i = 0; i < NB_HOMME_POISSONS; i++) {
			Hommes_Poissons p = new Hommes_Poissons();
			this.homme_poissons.add(p);
			this.personnages.add(p);
		}
		for(int i = 0; i < NB_NAINS; i++) {
			Nains n = new Nains();
			this.nains.add(n);
			this.personnages.add(n);
		}
		// Permet de mélanger pour que tous les humains ne jouent pas en même temps par exemple
		Collections.shuffle(this.personnages);
	}
	
	private void generateMap() {
		for(int i = 0; i < TAILLE_MAP; i++) {
			ArrayList<Case> subList=new ArrayList<Case>();
			for(int j = 0; j < TAILLE_MAP; j++) {
				Case c;
				if(i < TAILLE_SAFE_ZONE && j < TAILLE_SAFE_ZONE) {
					// Case safe pour les Humains
					c = new Case(i,j,true,false,false,false);
					
				}
				else if(i >= TAILLE_MAP-TAILLE_SAFE_ZONE && j < TAILLE_SAFE_ZONE) {
					// Case safe pour les Homme-Poissons
					c = new Case(i,j,false,true,false,false);
				}
				else if(i < TAILLE_SAFE_ZONE && j >= TAILLE_MAP-TAILLE_SAFE_ZONE) {
					// Case safe pour les Géants
					c = new Case(i,j,false,false,true,false);
				}
				else if(i >= TAILLE_MAP-TAILLE_SAFE_ZONE && j >= TAILLE_MAP-TAILLE_SAFE_ZONE) {
					// Case safe pour les Nains
					c = new Case(i,j,false,false,false,true);
				}
				else {
					// Le reste du plateau
					c = new Case(i,j,false,false,false,false);
				}
				subList.add(c);
			}
			this.map.add(subList);
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
		for( ArrayList<Case> tab : this.map) {
			for(Case c : tab) {
				if(c.isSafeForGeant) {
					System.out.printf("x");
				}
				else if(c.isSafeForHommePoisson) {
					System.out.printf("x");
				}
				else if(c.isSafeForHumain) {
					System.out.printf("x");
				}
				else if(c.isSafeForNain) {
					System.out.printf("x");
				}
				else {
					System.out.printf("o");
				}
				System.out.printf(" ");
			}
			System.out.println("");
		}
			
		
	}
	
	
}
