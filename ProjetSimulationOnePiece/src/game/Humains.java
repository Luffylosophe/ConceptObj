package game;

public class Humains extends Pirate {

	public Humains() {
	}
	@Override
	public void move() {
		// TODO Auto-generated method stub
		
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
	protected boolean isInSafeZone() {
		// TODO Auto-generated method stub
		if(this.c.getPosX() < Map.TAILLE_SAFE_ZONE && this.c.getPosY() < Map.TAILLE_SAFE_ZONE) return true;
		else return false;
	}
}
