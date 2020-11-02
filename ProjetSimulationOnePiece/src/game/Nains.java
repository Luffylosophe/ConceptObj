package game;

import java.util.ArrayList;

public class Nains extends Pirate {

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
						//ajouter les autres cas de figure
					}
				}
			}
		}

		return availableCases;
	}
	
	private ArrayList<Case> goBack(Map m){
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
	public void attaquer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void parler() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mourir() {
		// TODO Auto-generated method stub
		
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
