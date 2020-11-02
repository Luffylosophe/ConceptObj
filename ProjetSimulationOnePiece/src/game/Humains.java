package game;

import java.util.ArrayList;

public class Humains extends Pirate {
	public Humains() {
	}
	@Override
	public void move(Map m) {
		Case nextMove = selectNextMove(m);
		
		if(nextMove!=null) {
			if(nextMove.getObstacle()!=null && nextMove.getObstacle() instanceof Poneglyphe) {
				Poneglyphe poneglyphe = (Poneglyphe)nextMove.getObstacle();
				this.currentPoneglyphe=poneglyphe;
				System.out.println("Humain : Poneglyphe " + poneglyphe.getId() + " recupéré.");
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
			for(int j = y -1; j <= y+1; j++) 
				if(i>=0 && j>=0 && i<m.TAILLE_MAP && j<m.TAILLE_MAP){
					if(i!=x || j!=y) {			// Oblige a bouger
						Case currentCase=m.getCase(i, j);
						// Empeche d'aller dans les safes zone enemies
						if(!currentCase.isSafeForGeant && !currentCase.isSafeForHommePoisson && !currentCase.isSafeForNain) {
							if(currentCase.getPersonnage()==null && currentCase.getObstacle()==null) {
								availableCases.add(currentCase);
							}
							else if(currentCase.getObstacle() instanceof Poneglyphe && this.currentPoneglyphe==null) {
								ArrayList<Case> onlyIssue = new ArrayList<Case>();
								onlyIssue.add(currentCase);
								return onlyIssue;
							}
							else if(currentCase.getPersonnage()!=null && currentCase.getObstacle()==null) {
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
							maitre.addPoneglyphe(this.currentPoneglyphe);
							
							System.out.println("Humain : Poneglyphe "+this.currentPoneglyphe.getId()+" a la base!");
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
	
	private void updatePoneglyphe(Map m) {
		if(this.isInSafeZone(m)) {
			Maitre_Humain master = getMaster(m);
			this.poneglyphes = (ArrayList<Poneglyphe>) master.poneglyphes.clone();
		}
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
	
	private Maitre_Humain getMaster(Map m) {
		for(Personnage master : m.maitres) {
			if(master instanceof Maitre_Humain) {
				return (Maitre_Humain) master;
			}
		}
		System.out.println("Maitre Humain manquant !!");
		return null;
	}
}
