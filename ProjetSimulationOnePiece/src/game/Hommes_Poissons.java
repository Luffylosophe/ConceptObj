package game;

import java.util.ArrayList;

public class Hommes_Poissons extends Marine {
	
	public Hommes_Poissons(Map m) {
		this.PA = m.PA_HOMMES_POISSONS;
		this.PV = m.PV_HOMMES_POISSONS;
		this.PE = m.PE_HOMMES_POISSONS;
		this.degats = m.DEGATS_HOMMES_POISSONS;
	}

	@Override
	public void move(Map m) {
		Case nextMove = selectNextMove(m);
		this.restorePE(m);
		
		if(nextMove!=null) {
			if(nextMove.getObstacle()!=null && nextMove.getObstacle() instanceof Poneglyphe) {
				Poneglyphe poneglyphe = (Poneglyphe)nextMove.getObstacle();
				this.currentPoneglyphe=poneglyphe;
				System.out.println("Homme-poisson : Poneglyphe " + poneglyphe.getId() + " recupéré.");
			}
			else if(nextMove.getPersonnage()==null) {
				this.updatePosition(m,nextMove);
			}
			else if(nextMove.getPersonnage()!=null && nextMove.getObstacle()==null) {
				this.rencontre(m, nextMove);
			}
		}
		this.updatePoneglyphe(m);
		this.noMorePE(m);
	}
	

	protected ArrayList<Case> getPossibleMoves(Map m){
		ArrayList<Case> availableCases = new ArrayList<Case>();
		int x = this.c.getPosX();
		int y = this.c.getPosY();
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y -1; j <= y+1; j++) {
				if(i>=0 && j>=0 && i<m.TAILLE_MAP && j<m.TAILLE_MAP){
					if(i!=x || j!=y) {			// Oblige a bouger
						Case currentCase=m.getCase(i, j);
						if(!currentCase.isSafeForGeant && !currentCase.isSafeForHumain && !currentCase.isSafeForNain) {
							if(currentCase.getPersonnage()==null && currentCase.getObstacle()==null) {
								availableCases.add(currentCase);
							}
							else if(currentCase.getPersonnage()!=null && currentCase.getObstacle()==null && this.isInSafeZone(m)==false) {
								if(currentCase.getPersonnage().isInFight==false) {
									availableCases.add(currentCase);
								}
								else if(currentCase.getPersonnage().isInFight && this.isInFight && currentCase.getPersonnage().getClass()!=this.getClass()) {
									ArrayList<Case> onlyIssue = new ArrayList<Case>();
									onlyIssue.add(currentCase);
									return onlyIssue;
								}
							}
							else if(currentCase.getObstacle() instanceof Poneglyphe && this.currentPoneglyphe==null && !this.poneglyphes.contains(currentCase.getObstacle())) {
								ArrayList<Case> onlyIssue = new ArrayList<Case>();
								onlyIssue.add(currentCase);
								return onlyIssue;
							}
							else if(currentCase.getPersonnage()==null && currentCase.getObstacle() instanceof Eau) {
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
		for(int i = x; i <= x+1; i++) {
			for(int j = y-1 ; j <= y ; j++) {
				if(i>=0 && j>=0 && i<m.TAILLE_MAP && j<m.TAILLE_MAP){
					if(i!=x || j!=y) {			// Oblige a bouger
						Case currentCase=m.getCase(i, j);
						if(currentCase.getPersonnage()==null && currentCase.getObstacle()==null) {
							availableCases.add(currentCase);
						}
						else if(currentCase.getPersonnage() instanceof Maitre_Homme_Poisson && this.currentPoneglyphe!=null) {
							Maitre_Homme_Poisson maitre = (Maitre_Homme_Poisson) currentCase.getPersonnage();
							maitre.addPoneglyphe(this.currentPoneglyphe);
							
							System.out.println("Homme-poisson : Poneglyphe "+this.currentPoneglyphe.getId()+" a la base!");
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
			ciblepv = ciblepv - this.degats;
			cible.getPersonnage().setPV(ciblepv);
			this.setPA(this.PA-1);
		// si on a assez de point d'attaque la cible se voit inflig� des degats et on perd 1 PA
		}
		else {
			System.out.println(this.getClass()+" : Je ne peux pas attaquer");
			this.isInFight=false;
			// One ne peut pas attaquer
		}
		if (ciblepv <= 0) {
			System.out.println("FONCTION MOURIR");
			cible.getPersonnage().mourir(m);
			this.isInFight=false;
			//on fait appel � la fonction mouir
		}
		else {
			System.out.println(" Il reste encore "+cible.getPersonnage().getPV()+" point de vie au "+race);
			if(!cible.getPersonnage().isInFight) this.isInFight=false;
		}
			
	}
		
	private Maitre_Homme_Poisson getMaster(Map m) {
		for(Personnage master : m.maitres) {
			if(master instanceof Maitre_Homme_Poisson) {
				return (Maitre_Homme_Poisson) master;
			}
		}
		System.out.println("Maitre Hhomme-poisson manquant !!");
		return null;
	}
	
	private void updatePoneglyphe(Map m) {
		if(this.isInSafeZone(m)) {
			Maitre_Homme_Poisson master = getMaster(m);
			this.poneglyphes = (ArrayList<Poneglyphe>) master.poneglyphes.clone();
		}
	}

	@Override
	protected boolean isInSafeZone(Map m) {
		// TODO Auto-generated method stub
		if(this.c.getPosX() >= m.TAILLE_MAP-m.TAILLE_SAFE_ZONE && this.c.getPosY() < m.TAILLE_SAFE_ZONE) return true;
		else return false;
	}
	protected void restorePE(Map m) {
		if(this.isInSafeZone(m) && this.PE < m.PE_HOMMES_POISSONS) this.PE+=3;
		if(this.isInSafeZone(m) && this.PA < m.PA_HOMMES_POISSONS) this.PA+=1;
	}
}
