package game;

public abstract class Personnage {
	
	int PV, PM, PE, PA;
	
	public abstract void move(); // fonction qui permet de se deplacer sur la map
	public abstract void attaquer(); // fonction qui permet d'attaquer un personnage
	public abstract void parler(); // fonction qui permet d'�changer un message avec un alli�
	public abstract void mourir(); // fonction qui cr�e un cadavre l� ou le personnage meurt
	

}
