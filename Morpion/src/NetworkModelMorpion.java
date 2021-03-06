import java.io.BufferedReader;
import java.io.IOException;

public class NetworkModelMorpion extends Thread implements ModeleMorpion {
    private final Client client;
    private final Etat joueur;

    private ModeleMorpionListener listener;
    private Etat gagnant;

    public NetworkModelMorpion(Client client, Etat joueur) {
        this.client = client;
        this.joueur = joueur;
    }

    private void onReceive(String line) {
        System.out.println("RECV " + line);

        String[] args = line.split(",");

        if (args[0].equalsIgnoreCase("cocher")) {
            int x = Integer.parseInt(args[1]);
            int y = Integer.parseInt(args[2]);
            Etat j = Etat.values()[Integer.parseInt(args[3])];

            listener.setCase(x, y, j);
        }

        if (args[0].equalsIgnoreCase("end_game")) {
            Etat j = Etat.values()[Integer.parseInt(args[1])];

            listener.endGame(j);
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = client.createBufferedReader();
            String line;
            while ((line = reader.readLine()) != null) {
                onReceive(line);
            }
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    @Override
    public void cocher(int x, int y) {
        client.println("cocher," + x + "," + y);
    }

    @Override
    public void recommencer() {

    }

    @Override
    public boolean estTerminee() {
        return gagnant != null;
    }

    @Override
    public boolean estGagnee() {
        return gagnant == joueur;
    }

    @Override
    public Etat getJoueur() {
        return joueur;
    }

    @Override
    public Etat getValeur(int x, int y) {
        return Etat.VIDE;
    }

    @Override
    public void setListener(ModeleMorpionListener listener) {
        this.listener = listener;
    }
}
