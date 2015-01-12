/**
 * Created by Cyril Alves on 09/01/2015.
 */
import javax.swing.*;
import java.awt.*;

public class DiabalikSwing {

    private ModeleDiabalikSimple modele;
    private char action;

    public DiabalikSwing() {
        this(new ModeleDiabalikSimple() );
    }


    public DiabalikSwing(NetworkModelDiabalik networkModelDiabalik) {

        for (int i = 0; i < modele.plateau.length; i++) {
            System.out.print("+---");
            for (int j = 0; j < modele.plateau.length; j++) {
                System.out.println('+');
            }
        }

        for (int i = 0; i < modele.plateau.length; i++) {
            for (int j = 0; j < modele.plateau.length; j++) {

            }
        }

        System.out.println("Action :" +"\n"+
                "           Deplacer[D]" +"\n"+
                "           Passer  [P]"+"\n"+
                "           Fin de tour[F]");

        Clavier.lire_char(action);

        switch (action)
        {
            case 'D': modele.deplacer (2,2,2,2,2); break;
            case 'P': modele.passe (2,2,2,2,2); break;
            case 'F': modele.changerTour(networkModelDiabalik.getJoueur()); break;
        }
    }



    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DiabalikSwing();
            }
        });
    }
}


