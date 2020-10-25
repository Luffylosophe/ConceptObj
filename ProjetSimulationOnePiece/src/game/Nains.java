package game;

public class Nains extends Pirate {

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
		if(this.c.getPosX() >= Map.TAILLE_MAP-Map.TAILLE_SAFE_ZONE && this.c.getPosY() >= Map.TAILLE_MAP-Map.TAILLE_SAFE_ZONE) {
			return true;
		}
		else return false;
	}
	
}
