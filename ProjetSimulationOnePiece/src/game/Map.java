package game;

import java.util.Random;
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
	private ArrayList<Cadavre> cadavres = new ArrayList<Cadavre>();
	public ArrayList<Personnage> maitres = new ArrayList<Personnage>();
	
	// Listes générales
	
	public ArrayList<Personnage> personnages = new ArrayList<Personnage>();
	public ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
	
	// --------------------------------------------------------------------
	
	public final int TAILLE_MAP = 15;
	public final int NB_HUMAINS = 6;
	public final int NB_GEANTS = 4;
	public final int NB_HOMME_POISSONS = 5;
	public final int NB_NAINS = 7;
	public final int NB_MONTAGNES = 8;
	public final int NB_EAU = 10;
	public final int NB_PONEGLYPHES = 4;
	public final int TAILLE_SAFE_ZONE = 4;
	
	public Map() {
		this.generate();
	}
	
	public void generate() {
		System.out.println("GENERATION DE LA MAP...");
		this.printPropertiesSimulation();
		this.map = new ArrayList<ArrayList<Case>>();
		this.generateCharacters();
		this.generateMap();
		this.placePersonnage();
		this.placeObstacles();
		this.printMap();
	}
	
	public void shuffleCharacters() {
		Collections.shuffle(this.personnages);
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
		this.shuffleCharacters();
	}
	
	
	
	private void placeObstacles() {
		for(int i = 0; i < NB_MONTAGNES; i++) {
			int x=Utilitaires.randInt(0,TAILLE_MAP-1);
			int y=Utilitaires.randInt(1,TAILLE_MAP-1);
			Case currentCase=this.getCase(x, y);
			while(currentCase.isSafeForSomeone() || currentCase.getObstacle()!=null) {
				x=Utilitaires.randInt(0,TAILLE_MAP-1);
				y=Utilitaires.randInt(1,TAILLE_MAP-1);
				currentCase=this.getCase(x, y);
			}
			Montagne m = new Montagne();
			this.obstacles.add(m);
			currentCase.setObstacle(m);
		}
		for(int i = 0; i < NB_EAU; i++) {
			int x=Utilitaires.randInt(0,TAILLE_MAP-1);
			int y=Utilitaires.randInt(1,TAILLE_MAP-1);
			Case currentCase=this.getCase(x, y);
			while(currentCase.isSafeForSomeone() || currentCase.getObstacle()!=null) {
				x=Utilitaires.randInt(0,TAILLE_MAP-1);
				y=Utilitaires.randInt(1,TAILLE_MAP-1);
				currentCase=this.getCase(x, y);
			}
			Eau e = new Eau();
			this.obstacles.add(e);
			currentCase.setObstacle(e);
		}
		for(int i = 0; i < NB_PONEGLYPHES; i++) {
			int x=Utilitaires.randInt(0,TAILLE_MAP-1);
			int y=Utilitaires.randInt(1,TAILLE_MAP-1);
			Case currentCase=this.getCase(x, y);
			while(currentCase.isSafeForSomeone() || currentCase.getObstacle()!=null) {
				x=Utilitaires.randInt(0,TAILLE_MAP-1);
				y=Utilitaires.randInt(1,TAILLE_MAP-1);
				currentCase=this.getCase(x, y);
			}
			Poneglyphe p = new Poneglyphe(i+1);
			this.obstacles.add(p);
			currentCase.setObstacle(p);
		}
	}
	
	private void placePersonnage() {
		//Creation Humains (On randInt entre 1 et la taille de la safe zone pour eviter de toucher le maître
		for(Humains h : this.humains) {
			int x=Utilitaires.randInt(1,TAILLE_SAFE_ZONE-1);
			int y=Utilitaires.randInt(1,TAILLE_SAFE_ZONE-1);
			while(this.getCase(x,y).getPersonnage()!=null) {
				x=Utilitaires.randInt(1,TAILLE_SAFE_ZONE-1);
				y=Utilitaires.randInt(1,TAILLE_SAFE_ZONE-1);
			}
			Case currentCase=this.getCase(x, y);
			h.setCase(currentCase);
			currentCase.setPersonnage(h);
		}
		for(Nains n : this.nains) {
			int x=Utilitaires.randInt(TAILLE_MAP-TAILLE_SAFE_ZONE,TAILLE_MAP-1);
			int y=Utilitaires.randInt(TAILLE_MAP-TAILLE_SAFE_ZONE,TAILLE_MAP-1);
			while(this.getCase(x,y).getPersonnage()!=null) {
				x=Utilitaires.randInt(TAILLE_MAP-TAILLE_SAFE_ZONE,TAILLE_MAP-1);
				y=Utilitaires.randInt(TAILLE_MAP-TAILLE_SAFE_ZONE,TAILLE_MAP-1);
			}
			Case currentCase=this.getCase(x, y);
			n.setCase(currentCase);
			currentCase.setPersonnage(n);
		}
		for(Geant g : this.geants) {
			int x=Utilitaires.randInt(1,TAILLE_SAFE_ZONE-1);
			int y=Utilitaires.randInt(TAILLE_MAP-TAILLE_SAFE_ZONE,TAILLE_MAP-1);
			while(this.getCase(x,y).getPersonnage()!=null) {
				x=Utilitaires.randInt(1,TAILLE_SAFE_ZONE-1);
				y=Utilitaires.randInt(TAILLE_MAP-TAILLE_SAFE_ZONE,TAILLE_MAP-1);
			}
			Case currentCase=this.getCase(x, y);
			g.setCase(currentCase);
			currentCase.setPersonnage(g);
		}
		for(Hommes_Poissons p : this.homme_poissons) {
			int x=Utilitaires.randInt(TAILLE_MAP-TAILLE_SAFE_ZONE,TAILLE_MAP-1);
			int y=Utilitaires.randInt(1,TAILLE_SAFE_ZONE-1);
			while(this.getCase(x,y).getPersonnage()!=null) {
				x=Utilitaires.randInt(TAILLE_MAP-TAILLE_SAFE_ZONE,TAILLE_MAP-1);
				y=Utilitaires.randInt(1,TAILLE_SAFE_ZONE-1);
			}
			Case currentCase=this.getCase(x, y);
			p.setCase(currentCase);
			currentCase.setPersonnage(p);
		}
	}
	
	private void generateMap() {

		for(int i = 0; i < TAILLE_MAP; i++) {
			ArrayList<Case> subList=new ArrayList<Case>();
			for(int j = 0; j < TAILLE_MAP; j++) {
				Case c;
				if(i < TAILLE_SAFE_ZONE && j < TAILLE_SAFE_ZONE) {
					// Case safe pour les Humains
					c = new Case(i,j,true,false,false,false);
					if(i==0 && j==0) {
						Maitre_Humain h = Maitre_Humain.getInstance();
						c.setPersonnage(h);
						this.maitres.add(h);
					}
				}
				else if(i >= TAILLE_MAP-TAILLE_SAFE_ZONE && j < TAILLE_SAFE_ZONE) {
					// Case safe pour les Homme-Poissons
					c = new Case(i,j,false,true,false,false);
					if(i==TAILLE_MAP-1 && j==0) {
						Maitre_Homme_Poisson p = Maitre_Homme_Poisson.getInstance();
						c.setPersonnage(p);
						this.maitres.add(p);
					}
				}
				else if(i < TAILLE_SAFE_ZONE && j >= TAILLE_MAP-TAILLE_SAFE_ZONE) {
					// Case safe pour les Géants
					c = new Case(i,j,false,false,true,false);
					if(i==0 && j==TAILLE_MAP-1) {
						Maitre_Geant g = Maitre_Geant.getInstance();
						c.setPersonnage(g);
						this.maitres.add(g);
					}
				}
				else if(i >= TAILLE_MAP-TAILLE_SAFE_ZONE && j >= TAILLE_MAP-TAILLE_SAFE_ZONE) {
					// Case safe pour les Nains
					c = new Case(i,j,false,false,false,true);
					if(i==TAILLE_MAP-1 && j==TAILLE_MAP-1) {
						Maitre_Nain n = Maitre_Nain.getInstance();
						c.setPersonnage(n);
						this.maitres.add(n);
					}
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
	
	public Case getCase(int x, int y) {
		return this.map.get(x).get(y);
	}
	
	public void printMap() {
		for( ArrayList<Case> tab : this.map) {
			for(Case c : tab) {
				Personnage p = c.getPersonnage();
				Obstacle o = c.getObstacle();
				if(p!=null && o==null) {
					if(p instanceof Maitre_Humain) {
						System.out.printf("H");
					}
					else if(p instanceof Maitre_Nain) {
						System.out.printf("N");
					}
					else if(p instanceof Maitre_Homme_Poisson) {
						System.out.printf("F");
					}
					else if(p instanceof Maitre_Geant) {
						System.out.printf("G");
					}
					else if(p instanceof Humains) {
						System.out.printf("h");
					}
					else if(p instanceof Hommes_Poissons) {
						System.out.printf("f");
					}
					else if(p instanceof Geant) {
						System.out.printf("g");
					}
					else if(p instanceof Nains) {
						System.out.printf("n");
					}
				}
				else if(o!=null && p==null){
					if(o instanceof Cadavre) {
						System.out.printf("c");
					}
					else if(o instanceof Montagne) {
						System.out.printf("m");
					}
					else if(o instanceof Poneglyphe) {
						System.out.printf("P");
					}
					else if(o instanceof Eau) {
						System.out.printf("e");
					}
					
				}
				else if(o!=null && p!=null) {
					if(o instanceof Cadavre) {
						System.out.printf("C");
					}
					else if(o instanceof Montagne) {
						System.out.printf("M");
					}
					else if(o instanceof Eau) {
						System.out.printf("E");
					}
				}
				else {
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
						System.out.printf("_");
					}
				}
				System.out.printf(" ");
			}
			System.out.println("");
		}
		System.out.printf("\n\n\n\n");
			
		
	}
	
	
}
