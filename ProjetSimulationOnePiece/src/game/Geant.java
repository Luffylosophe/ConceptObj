package game;

import java.util.ArrayList;

public class Geant extends Marine {
	
	public Geant() {
		this.PA = 5;
		this.PV = 100;
		this.PE = 30;
	}

	@Override
	public void move(Map m) {
		Case nextMove = selectNextMove(m);
		this.restorePE(m);
		
		if(nextMove!=null) {
			if(nextMove.getObstacle()!=null && nextMove.getObstacle() instanceof Poneglyphe) {
				Poneglyphe poneglyphe = (Poneglyphe)nextMove.getObstacle();
				this.currentPoneglyphe=poneglyphe;
				System.out.println("Geant : Poneglyphe " + poneglyphe.getId() + " recupéré.");
			}
			else if(nextMove.getPersonnage()==null) {
				this.updatePosition(m,nextMove);
			}
			else if(nextMove.getPersonnage()!=null && nextMove.getObstacle()==null) {
				this.rencontre(m,nextMove);
			}
		}
		this.updatePoneglyphe(m);
		this.noMorePE(m);
	}

	protected ArrayList<Case> getPossibleMoves(Map m){
		ArrayList<Case> availableCases = new ArrayList<Case>();
		int x = c.getPosX();
		int y = c.getPosY();
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y -1; j <= y+1; j++) {
				if(i>=0 && j>=0 && i<m.TAILLE_MAP && j<m.TAILLE_MAP){
					if(i!=x || j!=y) {			// Oblige a bouger
						Case currentCase=m.getCase(i, j);
						if(!currentCase.isSafeForHommePoisson && !currentCase.isSafeForHumain && !currentCase.isSafeForNain) {
							if(currentCase.getPersonnage()==null && currentCase.getObstacle()==null) {
								availableCases.add(currentCase);
							}
							else if(currentCase.getPersonnage()!=null && currentCase.getObstacle()==null && this.isInSafeZone(m)==false) {
								if(currentCase.getPersonnage().isInFight==false) {
									availableCases.add(currentCase);
								}
								else if(currentCase.getPersonnage().isInFight && this.isInFight) {
									ArrayList<Case> onlyIssue = new ArrayList<Case>();
									onlyIssue.add(currentCase);
									return onlyIssue;
								}
							}
							else if(currentCase.getObstacle() instanceof Poneglyphe && this.currentPoneglyphe==null) {
								ArrayList<Case> onlyIssue = new ArrayList<Case>();
								onlyIssue.add(currentCase);
								return onlyIssue;
							}
							else if(currentCase.getObstacle() instanceof Cadavre && currentCase.getPersonnage()==null) {
								availableCases.add(currentCase);
							}
							//ajouter les autres cas de figure
						}
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
		for(int i = x-1; i <= x; i++) {
			for(int j = y ; j <= y+1 ; j++) {
				if(i>=0 && j>=0 && i<m.TAILLE_MAP && j<m.TAILLE_MAP){
					if(i!=x || j!=y) {			// Oblige a bouger
						Case currentCase=m.getCase(i, j);
						if(currentCase.getPersonnage()==null && currentCase.getObstacle()==null) {
							availableCases.add(currentCase);
						}
						else if(currentCase.getPersonnage() instanceof Maitre_Geant && this.currentPoneglyphe!=null) {
							Maitre_Geant maitre = (Maitre_Geant) currentCase.getPersonnage();
							maitre.addPoneglyphe(this.currentPoneglyphe);
							
							System.out.println("Geant : Poneglyphe "+this.currentPoneglyphe.getId()+" a la base!");
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
	public void attaquer(Map m, Case cible) {
		Class race = cible.getPersonnage().getClass();
		int ciblepv = cible.getPersonnage().getPV();
		// on cherche les donn�es sur la cible
		
		if (this.PA > 0 && this.PV>0) {
			System.out.println("Un "+this.getClass()+" attaque un "+race);
			ciblepv = ciblepv - 50;
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
				cible.getPersonnage().mourir(m);
				this.isInFight=false;
				//on fait appel � la fonction mouir
			}
			else {
				System.out.println(" Il reste encore "+cible.getPersonnage().getPV()+" point de vie au "+race);
				
			}
			
		}

	private Maitre_Geant getMaster(Map m) {
		for(Personnage master : m.maitres) {
			if(master instanceof Maitre_Geant) {
				return (Maitre_Geant) master;
			}
		}
		System.out.println("Maitre Geant manquant !!");
		return null;
	}
	
	private void updatePoneglyphe(Map m) {
		if(this.isInSafeZone(m)) {
			Maitre_Geant master = getMaster(m);
			this.poneglyphes = (ArrayList<Poneglyphe>) master.poneglyphes.clone();
		}
	}
	
	@Override
	protected boolean isInSafeZone(Map m) {
		// TODO Auto-generated method stub
		if(this.c.getPosX() < m.TAILLE_SAFE_ZONE && this.c.getPosY() >= m.TAILLE_MAP-m.TAILLE_SAFE_ZONE) return true;
		else return false;
	}
}
