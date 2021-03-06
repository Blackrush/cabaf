package fr.cabaf.backend.handler.morpion;


public interface ModeleMorpion {


	enum Etat {
		VIDE, CROIX, ROND;

		public Etat next() {
			if (this == CROIX) return ROND;
			if (this == ROND)  return CROIX;
			throw new IllegalStateException();
		}

		public Etat reset() {
			return CROIX;
		}
	}

	int TAILLE = 3;

	boolean cocher(int x, int y, Etat joueur);

	boolean estTerminee();

	Etat getGagnant();

    int getTaille();
    Etat getValue(int x,int y);

}