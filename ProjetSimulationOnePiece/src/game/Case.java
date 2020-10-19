package game;

public class Case {
	private int posX,posY;
	final boolean isSafeForHumain, isSafeForNain, isSafeForHommePoisson, isSafeForGeant;
	Obstacle a;
	Personnage b;
	
	public Case(int x, int y,boolean sfHu,boolean sfHo,boolean sfGe,boolean sfNa) {
		this.posX=x;
		this.posY=y;
		this.isSafeForHumain=sfHu;
		this.isSafeForNain=sfNa;
		this.isSafeForHommePoisson=sfHo;
		this.isSafeForGeant=sfGe;
	}
}

