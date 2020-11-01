package game;

import java.util.ArrayList;

public abstract class Personnage {
	Case c;
	int PV, PM, PE, PA;
	protected ArrayList<Poneglyphe> poneglyphes = new ArrayList<Poneglyphe>();
	
	public abstract void move(Map m); // fonction qui permet de se deplacer sur la map
	public abstract void attaquer(); // fonction qui permet d'attaquer un personnage
	public abstract void parler(); // fonction qui permet d'�changer un message avec un alli�
	public abstract void mourir(); // fonction qui cr�e un cadavre l� ou le personnage meurt
	protected abstract boolean isInSafeZone();
	
    public void addPoneglyphe(Poneglyphe p) {
    	if(this.poneglyphes.contains(p)==false) {
    		this.poneglyphes.add(p);
    	}
    }
    public ArrayList<Poneglyphe> getPoneglyphesArray(){
    	return this.poneglyphes;
    }
}
