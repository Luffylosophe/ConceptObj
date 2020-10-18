package game;

public abstract class Personnage {
	
	int PV, PM, PE, PA;
	
	public abstract void seDeplacer(); // fonction qui permet de se deplacer sur la map
	public abstract void attaquer(); // fonction qui permet d'attaquer un personnage
	public abstract void parler(); // fonction qui permet d'échanger un message avec un allié
	public abstract void mourir(); // fonction qui crée un cadavre là ou le personnage meurt
	

}
