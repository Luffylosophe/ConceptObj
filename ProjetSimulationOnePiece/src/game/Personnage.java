package game;

import java.util.ArrayList;

public abstract class Personnage {
	protected Case c;
	protected int PV, PM, PE, PA;
	protected ArrayList<Poneglyphe> poneglyphes = new ArrayList<Poneglyphe>();
	protected Poneglyphe currentPoneglyphe=null;
	protected boolean isInFight= false;
	
	public abstract void move(Map m); // fonction qui permet de se deplacer sur la map
	public abstract void attaquer(Case cible); // fonction qui permet d'attaquer un personnage
	public abstract void parler(Case cible); // fonction qui permet d'�changer un message avec un alli�
	
	protected abstract boolean isInSafeZone(Map m);
	protected void mourir(Map map) { // fonction qui cr�e un cadavre l� ou le personnage meurt
		map.personnages.remove(this);
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
		this.c.setObstacle(cadavre);
		this.c.setPersonnage(null);
		map.obstacles.add(cadavre);
		map.cadavres.add(cadavre);
		
	}
    public void addPoneglyphe(Poneglyphe p) {
    	if(this.poneglyphes.contains(p)==false) {
    		this.poneglyphes.add(p);
    	}
    }
    public ArrayList<Poneglyphe> getPoneglyphesArray(){
    	return this.poneglyphes;
    }
	public int getPV() {
		return PV;
	}
	public void setPV(int pV) {
		PV = pV;
	}
	public int getPM() {
		return PM;
	}
	public void setPM(int pM) {
		PM = pM;
	}
	public int getPE() {
		return PE;
	}
	public void setPE(int pE) {
		PE = pE;
	}
	public int getPA() {
		return PA;
	}
	public void setPA(int pA) {
		PA = pA;
	}
	protected void rencontre(Case target){
		Personnage p = target.getPersonnage();
		if( (p instanceof Pirate == this instanceof Pirate) || (p instanceof Marine == this instanceof Marine)) {
			this.parler(target);
		}
		else {
			this.attaquer(target);
			this.isInFight=true;
			target.getPersonnage().isInFight=true;
		}

	}
    
}
