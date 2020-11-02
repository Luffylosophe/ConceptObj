package game;

import java.util.ArrayList;

public class Simulation {

	public static void main(String[] args) {
		startSimulation(1);

	}
	private static void startSimulation(int pas) {
		System.out.println("LANCEMENT DE LA SIMULATION");
		Map sim = new Map();
		while(hasWon(sim)==false){
			update(sim);
			afficheTableauDesScores(sim);
			//sim.printEtatPartie(); 	// permet de voir qui a cb de poneglyphe
			try {
				//java.util.concurrent.TimeUnit.SECONDS.sleep(pas);
				java.util.concurrent.TimeUnit.MILLISECONDS.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		//fonction pour pause
		}
		System.out.println("FIN DE LA SIMULATION");
		System.out.println("**************************************");
		System.out.println(findWinner(sim));
		System.out.println("**************************************");
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
	
	private static void afficheTableauDesScores(Map sim) {
		
		int hauteur = sim.maitres.size()*2;
		int largeur = sim.NB_PONEGLYPHES*3 + 13;
		Utilitaires.repeat("*", largeur);
		System.out.println("**  TABLEAU DES SCORES **");
		Utilitaires.repeat("*", largeur);
		ArrayList<Personnage> masters = (ArrayList<Personnage>) sim.maitres.clone();
		for(int i = 0; i<largeur; i++) {
			System.out.printf("-");
		}
		System.out.printf("\n");
		int id=1;
		for(int i = 0; i<largeur;i++) {
			int place=i-13;
			if(i<13) {
				System.out.printf(" ");
			}
			else if(place%3==1) {
				System.out.print(id);
				id++;
			}
			else {
				System.out.printf("|");
			}
		}
		System.out.printf("\n");
		for(int i = 0; i <= hauteur; i++) {
			for(int j = 0; j < largeur; j++) {
				if(i==0) System.out.printf("-");
				else if(i%2==1 && j<=13) {
					Personnage master=masters.get(0);
					String toPrint="";
					if(master instanceof Maitre_Humain) {
						toPrint="Humain";
					}
					else if(master instanceof Maitre_Homme_Poisson) {
						toPrint="Homme-poisson";
					}
					else if(master instanceof Maitre_Nain) {
						toPrint="Nain";
					}
					else {
						toPrint="Géant";
					}
					System.out.printf(toPrint);
					masters.remove(0);
					j+=toPrint.length();
					while(j!=13) {
						System.out.printf(" ");
						j+=1;
					}
					id=1;
					while(j < largeur) {
						int place=j-13;
						
						if(place%3==1) {
							try {
								if(master.poneglyphes.get(id-1).getId()==id) {
									System.out.printf("X");
								}
								else System.out.printf(" ");
								id++;
							}
							catch(Exception e) {
								System.out.printf(" ");
							}

						}
						else {
							System.out.printf("|");
						}
						j++;
					}

					
				}
				else {
					System.out.printf("-");
				}
			}
			System.out.printf("\n");
		}
	}
	
	private static String findWinner(Map sim) {
		for(Personnage p : sim.maitres) {
			if(p.getPoneglyphesArray().size()==sim.NB_PONEGLYPHES) {
				if(p instanceof Maitre_Humain) {
					return "*    Victoire des humains !    *";
				}
				else if(p instanceof Maitre_Homme_Poisson) {
					return "*    Victoire des hommes-poissons !    *";
				}
				else if(p instanceof Maitre_Nain) {
					return "*    Victoire des nains !    *";
				}
				else {
					return "*   Victoire des géants !    *";
				}
			}
		}
		return null;
	}
}
