package game;

import java.util.ArrayList;

public abstract class Personnage {
	protected Case c;
	protected int PV, PE, PA, degats;
	protected ArrayList<Poneglyphe> poneglyphes = new ArrayList<Poneglyphe>();
	protected Poneglyphe currentPoneglyphe=null;
	protected boolean isInFight= false;
	
	public abstract void move(Map m); // fonction qui permet de se deplacer sur la map
	protected abstract void attaquer(Map m, Case cible); // fonction qui permet d'attaquer un personnage
	protected abstract ArrayList<Case> getPossibleMoves(Map m);
	protected abstract ArrayList<Case> goBack(Map m);
	protected abstract boolean isInSafeZone(Map m);
	
	
	protected void mourir(Map map) { // fonction qui cr�e un cadavre l� ou le personnage meurt
		
		if(this instanceof Humains) {
			map.humains.remove(this);
		}
		else if(this instanceof Geant) {
			map.geants.remove(this);
		}
		else if(this instanceof Nains) {
			map.nains.remove(this);
		}
		else {
			map.homme_poissons.remove(this);
		}
		Cadavre cadavre = new Cadavre();
		this.c.setPersonnage(null);
		this.c.setObstacle(cadavre);
		map.obstacles.add(cadavre);
		map.cadavres.add(cadavre);
		
	}
	// Echange poneglyphes avec alliés
	protected void parler(Case target) {
		ArrayList<Poneglyphe> unknownPoneglyphes = new ArrayList<Poneglyphe>();
		Personnage personnage = target.getPersonnage();
		for(Poneglyphe poneglyphe : personnage.poneglyphes) {
			if(this.poneglyphes.contains(poneglyphe)==false) {
				unknownPoneglyphes.add(poneglyphe);
			}
		}
		if(unknownPoneglyphes.size()==1) this.currentPoneglyphe = unknownPoneglyphes.get(0);
		else if(unknownPoneglyphes.size()>=1){
			int index = Utilitaires.randInt(0, unknownPoneglyphes.size()-1);
			this.currentPoneglyphe = unknownPoneglyphes.get(index);
		}
		
		
		unknownPoneglyphes = new ArrayList<Poneglyphe>();
		for(Poneglyphe poneglyphe : this.poneglyphes) {
			if(personnage.poneglyphes.contains(poneglyphe)==false) {
				unknownPoneglyphes.add(poneglyphe);
			}
		}
		if(unknownPoneglyphes.size()==1) this.currentPoneglyphe = unknownPoneglyphes.get(0);
		else if(unknownPoneglyphes.size()>=1){
			int index = Utilitaires.randInt(0, unknownPoneglyphes.size()-1);
			this.currentPoneglyphe = unknownPoneglyphes.get(index);
		}
	}
    protected void addPoneglyphe(Poneglyphe p) {
    	if(this.poneglyphes.contains(p)==false) {
    		this.poneglyphes.add(p);
    	}
    }
    protected ArrayList<Poneglyphe> getPoneglyphesArray(){
    	return this.poneglyphes;
    }
    protected int getPV() {
		return PV;
	}
    protected void setPV(int pV) {
		this.PV = pV;
	}
    protected int getDegats() {
		return degats;
	}
    protected void setDegats(int degats) {
		this.degats=degats;
	}
    protected int getPE() {
		return PE;
	}
    protected void setPE(int pE) {
		this.PE = pE;
	}
    protected int getPA() {
		return PA;
	}
    protected void setPA(int pA) {
		this.PA = pA;
	}
	protected void rencontre(Map m, Case target){
		Personnage p = target.getPersonnage();
		if( (p instanceof Pirate == this instanceof Pirate) || (p instanceof Marine == this instanceof Marine)) {
			System.out.println("** Partage savoir **");
			this.parler(target);
		}
		else {
			System.out.println("** Combat! **");
			this.isInFight=true;
			target.getPersonnage().isInFight=true;
			this.attaquer(m, target);
		}

	}
	
	protected Case selectNextMove(Map m) {
		if(PV<=0) {
			System.out.println("ICI");
			return null;
		}
		if(this.currentPoneglyphe!=null || this.PE <= m.TAILLE_MAP/2 || this.PA==0) {
			this.isInFight=false;
			ArrayList<Case> moves = this.goBack(m);
			if(moves.size()==1) {
				return moves.get(0);
			}
			else if(moves.size()!=0) {
				return moves.get(Utilitaires.randInt(0, moves.size()-1));
			}
			else {
				return null;
			}
		}
		else {
			ArrayList<Case> moves = getPossibleMoves(m);
			if(moves.size()==1) {
				return moves.get(0);
			}
			else if(moves.size()!=0) {
				return moves.get(Utilitaires.randInt(0, moves.size()-1));
			}
			else return null;
		}
	}
	
	protected void updatePosition(Map m,Case nextCase) {
		int x = this.c.getPosX();
		int y = this.c.getPosY();
		m.getCase(x,y).setPersonnage(null);
		this.setCase(nextCase);
		x = this.c.getPosX();
		y = this.c.getPosY();
		m.getCase(x,y).setPersonnage(this);
		this.PE--;
	}
	protected void setCase(Case nextCase) {
		this.c=nextCase;
	}
	
	protected void noMorePE(Map m) {
		if(this.PE==0 && this.isInSafeZone(m)==false) {
			System.out.println("MEURS!");
			this.mourir(m);
		}
	}
	
    
}
