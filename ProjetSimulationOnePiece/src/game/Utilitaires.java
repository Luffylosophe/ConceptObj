package game;

public class Utilitaires {
	static public void repeat(String l, int count) {
		for(int i=0; i< count; i++) {
			if(i!=count-1) System.out.printf(l);
			else System.out.println(l);
		}
	}
}
