package game;

public class Simulation {

	public static void main(String[] args) {
		startSimulation();
		// TODO Auto-generated method stub

	}
	private static void startSimulation() {
		System.out.println("LANCEMENT DE LA SIMULATION");
		Map sim = new Map();
		while(hasWon(sim)==false){
			update(sim);
			try {
				java.util.concurrent.TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		//fonction pour pause
		}
		System.out.println("FIN DE LA SIMULATION");
		// Afficher le vainqueur
	}
	private static void update(Map sim) {
		for(Personnage p : sim.personnages) {
			p.move(sim);
			
		}
		sim.shuffleCharacters();
		sim.printMap();
	}
	
	private static boolean hasWon(Map sim) {
		for(Personnage p : sim.maitres) {
			if(p.getPoneglyphesArray().size()==sim.NB_PONEGLYPHES) {
				return true;
			}
		}
		return false;
	}
}
