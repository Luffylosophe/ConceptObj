package game;

import java.util.ArrayList;

public class Nains extends Pirate {
	
	public Nains() {
		this.PA = 5;
		this.PV = 100;
		this.PE = 5;
	}

	@Override
	public void move(Map m) {
		// TODO Auto-generated method stub
		Case nextMove = selectNextMove(m);
		
		
		if(nextMove!=null) {
			if(nextMove.getObstacle()!=null && nextMove.getObstacle() instanceof Poneglyphe) {
				Poneglyphe poneglyphe = (Poneglyphe)nextMove.getObstacle();
				this.currentPoneglyphe=poneglyphe;
				System.out.println("Nain : Poneglyphe " + poneglyphe.getId() + " recupéré.");
			}
			else if(nextMove.getPersonnage()==null) {
				this.updatePosition(m,nextMove);
			}
			else if(nextMove.getPersonnage()!=null && nextMove.getObstacle()==null) {
				this.rencontre(nextMove);
			}
		}
		this.updatePoneglyphe(m);
		
	}	
	
	protected ArrayList<Case> getPossibleMoves(Map m){
		ArrayList<Case> availableCases = new ArrayList<Case>();
		int x = c.getPosX();
		int y = c.getPosY();
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y -1; j <= y+1; j++) {
				if(i>=0 && j>=0 && i<m.TAILLE_MAP && j<m.TAILLE_MAP){
					Case currentCase=m.getCase(i, j);
					if(!currentCase.isSafeForGeant && !currentCase.isSafeForHommePoisson && !currentCase.isSafeForHumain) {
						if(currentCase.getPersonnage()==null && currentCase.getObstacle()==null) {
							availableCases.add(currentCase);
						}
						else if(currentCase.getObstacle() instanceof Poneglyphe && this.currentPoneglyphe==null) {
							ArrayList<Case> onlyIssue = new ArrayList<Case>();
							onlyIssue.add(currentCase);
							return onlyIssue;
						}
						else if(currentCase.getPersonnage()==null && currentCase.getObstacle() instanceof Montagne) {
							availableCases.add(currentCase);
						}
						else if(currentCase.getPersonnage()!=null && currentCase.getObstacle()==null) {
							if(currentCase.getPersonnage().isInFight==false) {
								availableCases.add(currentCase);
							}
						}
						//ajouter les autres cas de figure
					}
				}
			}
		}

		return availableCases;
	}
	
	protected ArrayList<Case> goBack(Map m){
		ArrayList<Case> availableCases = new ArrayList<Case>();
		int x = c.getPosX();
		int y = c.getPosY();
		for(int i = x; i <= x+1; i++) {
			for(int j = y ; j <= y+1 ; j++) {
				if(i>=0 && j>=0 && i<m.TAILLE_MAP && j<m.TAILLE_MAP){
					if(i!=x || j!=y) {			// Oblige a bouger
						Case currentCase=m.getCase(i, j);
						if(currentCase.getPersonnage()==null && currentCase.getObstacle()==null) {
							availableCases.add(currentCase);
						}
						else if(currentCase.getPersonnage() instanceof Maitre_Nain) {
							Maitre_Nain maitre = (Maitre_Nain) currentCase.getPersonnage();
							maitre.addPoneglyphe(this.currentPoneglyphe);
							
							System.out.println("Nain : Poneglyphe "+this.currentPoneglyphe.getId()+" a la base!");
							this.currentPoneglyphe=null;
						}
					}
				}
			}
		}
		if(availableCases.isEmpty()) {
			return this.getPossibleMoves(m);
		}
		return availableCases;
	}
	

	@Override
	public void attaquer( Case cible) {
		Class race = cible.getPersonnage().getClass();
		int ciblepv = cible.getPersonnage().getPV();
		// on cherche les donn�es sur la cible
		
		if (this.PA > 0) {
			System.out.println("Un "+this.getClass()+" attaque un "+race);
			ciblepv = ciblepv - 5;
			cible.getPersonnage().setPV(ciblepv);
			this.setPA(this.PA-1);
		// si on a assez de point d'attaque la cible se voit inflig� des degats et on perd 1 PA
		}
		
		else 
		{
			System.out.println(this.getClass()+" : Je ne peux pas attaquer");
			// One ne peut pas attaquer
		}
			if (ciblepv == 0) {
				System.out.println("FONCTION MOURIR");
				//on fait appel � la fonction mouir
			}
			else {
				System.out.println(" Il reste encore "+cible.getPersonnage().getPV()+" point de vie au "+race);
				
			}
			
		}



	
	public void setCase(Case c) {
		this.c=c;
	}
	
	private void updatePoneglyphe(Map m) {
		if(this.isInSafeZone(m)) {
			Maitre_Nain master = getMaster(m);
			this.poneglyphes = (ArrayList<Poneglyphe>) master.poneglyphes.clone();
		}
	}
	
	private Maitre_Nain getMaster(Map m) {
		for(Personnage master : m.maitres) {
			if(master instanceof Maitre_Nain) {
				return (Maitre_Nain) master;
			}
		}
		System.out.println("Maitre Nain manquant !!");
		return null;
	}
	
	@Override
	protected boolean isInSafeZone(Map m) {
		if(this.c.getPosX() >= m.TAILLE_MAP-m.TAILLE_SAFE_ZONE && this.c.getPosY() >= m.TAILLE_MAP-m.TAILLE_SAFE_ZONE) {
			return true;
		}
		else return false;
	}
	
}
