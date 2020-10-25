package game;

import java.util.Random;

public class Utilitaires {
	static public void repeat(String l, int count) {
		for(int i=0; i< count; i++) {
			if(i!=count-1) System.out.printf(l);
			else System.out.println(l);
		}
	}
	
	static public int randInt(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	}
}
