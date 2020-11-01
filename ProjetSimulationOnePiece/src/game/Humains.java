package game;

import java.util.ArrayList;

public class Humains extends Pirate {

	public Humains() {
	}
	@Override
	public void move(Map m) {
		// TODO Auto-generated method stub
		Case nextMove = selectNextMove(m);
		if(nextMove!=null) {
			if(nextMove.getObstacle()!=null && nextMove.getObstacle() instanceof Poneglyphe) {
				Poneglyphe poneglyphe = (Poneglyphe)nextMove.getObstacle();
				this.poneglyphes.add(poneglyphe);
				System.out.println("Humain : Poneglyphe " + poneglyphe.getId() + " recupéré.");
			}
			else if(nextMove.getPersonnage()==null && nextMove!=null) {
				this.updatePosition(m,nextMove);
			}
			
		}

	}

	private ArrayList<Case> getPossibleMoves(Map m){
		ArrayList<Case> availableCases = new ArrayList<Case>();
		int x = c.getPosX();
		int y = c.getPosY();
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y -1; j <= y+1; j++) 
				if(i>=0 && j>=0 && i<m.TAILLE_MAP && j<m.TAILLE_MAP){
					if(i!=x || j!=y) {			// Oblige a bouger
						Case currentCase=m.getCase(i, j);
						if(currentCase.getPersonnage()==null && currentCase.getObstacle()==null) {
							availableCases.add(currentCase);
						}
						else if(currentCase.getObstacle() instanceof Poneglyphe && this.poneglyphes.size()==0) {
							ArrayList<Case> onlyIssue = new ArrayList<Case>();
							onlyIssue.add(currentCase);
							return onlyIssue;
						}
						//ajouter les autres cas de figure
					}
				}
			}
		return availableCases;
	}
	
	private ArrayList<Case> goBack(Map m){
		ArrayList<Case> availableCases = new ArrayList<Case>();
		int x = c.getPosX();
		int y = c.getPosY();
		for(int i = x-1; i <= x; i++) {
			for(int j = y-1 ; j <= y ; j++) {
				if(i>=0 && j>=0 && i<m.TAILLE_MAP && j<m.TAILLE_MAP){
					if(i!=x || j!=y) {			// Oblige a bouger
						Case currentCase=m.getCase(i, j);
						if(currentCase.getPersonnage()==null && currentCase.getObstacle()==null) {
							availableCases.add(currentCase);
						}
						else if(currentCase.getPersonnage() instanceof Maitre_Humain) {
							Maitre_Humain maitre = (Maitre_Humain) currentCase.getPersonnage();
							maitre.addPoneglyphe(this.poneglyphes.get(0));
							
							System.out.println("Humain : Poneglyphe "+this.poneglyphes.get(0).getId()+" a la base!");
							this.poneglyphes.remove(0);
							
						}
					}
				}
			}
		}
		return availableCases;
	}
	private Case selectNextMove(Map m) {
		if(this.poneglyphes.size()!=0) {
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
	@Override
	protected boolean isInSafeZone(Map m) {
		// TODO Auto-generated method stub
		if(this.c.getPosX() < m.TAILLE_SAFE_ZONE && this.c.getPosY() < m.TAILLE_SAFE_ZONE) return true;
		else return false;
	}
}
