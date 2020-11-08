package game;

import java.util.ArrayList;

public class Hommes_Poissons extends Marine {
	
	public Hommes_Poissons() {
		this.PA = 5;
		this.PV = 100;
		this.PE = 5;
	}

	@Override
	public void move(Map m) {
		Case nextMove = selectNextMove(m);
		
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
				System.out.println("FONCTION RENCONTRE");
			}
		}
		this.updatePoneglyphe(m);
	}
	

	private ArrayList<Case> getPossibleMoves(Map m){
		ArrayList<Case> availableCases = new ArrayList<Case>();
		int x = c.getPosX();
		int y = c.getPosY();
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y -1; j <= y+1; j++) {
				if(i>=0 && j>=0 && i<m.TAILLE_MAP && j<m.TAILLE_MAP){
					Case currentCase=m.getCase(i, j);
					if(!currentCase.isSafeForGeant && !currentCase.isSafeForHumain && !currentCase.isSafeForNain) {
						if(currentCase.getPersonnage()==null && currentCase.getObstacle()==null) {
							availableCases.add(currentCase);
						}
						else if(currentCase.getObstacle() instanceof Poneglyphe && this.currentPoneglyphe==null) {
							ArrayList<Case> onlyIssue = new ArrayList<Case>();
							onlyIssue.add(currentCase);
							return onlyIssue;
						}
						else if(currentCase.getPersonnage()==null && currentCase.getObstacle() instanceof Eau) {
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
	private Case selectNextMove(Map m) {
		if(this.currentPoneglyphe!=null) {
			ArrayList<Case> moves = this.goBack(m);
			if(moves.size()==1) {
				return moves.get(0);
			}
			else if(moves.size()!=0) {
				return moves.get(Utilitaires.randInt(0, moves.size()-1));
			}
			else {
				return null;
			}
		}
		else {
			ArrayList<Case> moves = getPossibleMoves(m);
			if(moves.size()==1) {
				return moves.get(0);
			}
			else if(moves.size()!=0) {
				return moves.get(Utilitaires.randInt(0, moves.size()-1));
			}
			else return null;
		}
	}
	

	private ArrayList<Case> goBack(Map m){
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
						else if(currentCase.getPersonnage() instanceof Maitre_Homme_Poisson) {
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
	
	private void updatePosition(Map m,Case nextCase) {
		int x = this.c.getPosX();
		int y = this.c.getPosY();
		m.getCase(x,y).setPersonnage(null);
		this.setCase(nextCase);
		x = this.c.getPosX();
		y = this.c.getPosY();
		m.getCase(x,y).setPersonnage(this);
	}
	@Override
	public void attaquer(Case cible) {
		Class race = cible.getPersonnage().getClass();
		int ciblepv = cible.getPersonnage().getPV();
		// on cherche les donn�es sur la cible
		
		if (this.PA > 0) {
			System.out.println("Un "+this.getClass()+" attaque un "+race);
			ciblepv = ciblepv - 20;
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
		
	

	@Override
	public void parler(Case cible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mourir() {
		// TODO Auto-generated method stub
		
	}
	public void setCase(Case c) {
		this.c=c;
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
}
