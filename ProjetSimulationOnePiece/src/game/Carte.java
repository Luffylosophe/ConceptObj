package game;

public class Carte {
	
	Case zone[][][];
	int nbObstacle;
	int nbPoneglyphe;
	
	public Carte() {
		System.out.println("Nous construisons la carte");
		zone = new Case[100][100][100];
		
	}

}
