package game;

import java.util.ArrayList;

public abstract class Personnage {
	Case c;
	int PV, PM, PE, PA;
	protected ArrayList<Poneglyphe> poneglyphes = new ArrayList<Poneglyphe>();
	protected Poneglyphe currentPoneglyphe=null;
	
	public abstract void move(Map m); // fonction qui permet de se deplacer sur la map
	public abstract void attaquer(Case cible); // fonction qui permet d'attaquer un personnage
	public abstract void parler(); // fonction qui permet d'�changer un message avec un alli�
	public abstract void mourir(); // fonction qui cr�e un cadavre l� ou le personnage meurt
	protected abstract boolean isInSafeZone(Map m);
	
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
    
    
}
