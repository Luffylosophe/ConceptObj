package game;

public class Case {
	private int posX,posY;
	final boolean isSafeForHumain, isSafeForNain, isSafeForHommePoisson, isSafeForGeant;
	private Obstacle obstacle = null;
	private Personnage personnage = null;
	
	public Case(int x, int y,boolean sfHu,boolean sfHo,boolean sfGe,boolean sfNa) {
		this.setPosX(x);
		this.setPosY(y);
		this.isSafeForHumain=sfHu;
		this.isSafeForNain=sfNa;
		this.isSafeForHommePoisson=sfHo;
		this.isSafeForGeant=sfGe;
	}
	
	public boolean isSafeForSomeone() {
		if(this.isSafeForHumain || this.isSafeForNain || this.isSafeForHommePoisson || this.isSafeForGeant) return true;
		else return false;
	}

	public Obstacle getObstacle() {
		return obstacle;
	}

	public void setObstacle(Obstacle o) {
		if(this.obstacle==null) {
			this.obstacle = o;
		}
		else {
			System.out.println("ERREUR dans setObstacle class Case.java : this.obstacle n'est pas null");
		}
	}

	public Personnage getPersonnage() {
		return personnage;
	}

	public void setPersonnage(Personnage p) {
		if(this.personnage==null) {
			this.personnage = p;
		}
		else {
			System.out.println("ERREUR dans SetPersonnage class Case.java : this.personnage n'est pas null");
		}
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}
	
}

