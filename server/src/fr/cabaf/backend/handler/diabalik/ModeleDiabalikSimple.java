package fr.cabaf.backend.handler.diabalik;

import fr.cabaf.backend.Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Brieuc DE TAPPIE INFOC2
 * @author Antoine CHAUVIN INFOB1
 */
public class ModeleDiabalikSimple implements ModeleDiabalik {

    Case[][] plateau = new Case[7][7];
    public ModeleDiabalikSimple()
    {
        for (int i=0;i<plateau.length;i++)
            for (int j=0;j<plateau[0].length;j++) plateau[i][j]=new Case();

        for(int i=0;i<plateau[0].length;i++)
        {
            plateau[0][i].setProprietaire(1);
            //plateau[0][i].setSupport(true);
        }
        plateau[0][3].setBalle(true);

        for(int i=0;i<plateau[0].length;i++)
        {
            plateau[6][i].setProprietaire(2);
            //plateau[8][i].setSupport(true);
        }
        plateau[6][3].setBalle(true);
    }

    public ModeleDiabalikSimple(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            for (int x = 0; x < 7; x++) {
                line = reader.readLine();

                for (int y = 0; y < 7; y++) {
                    Case c = new Case();
                    plateau[x][y] = c;

                    switch (line.charAt(y)) {
                        case '1':
                            c.setProprietaire(1);
                            break;
                        case '2':
                            c.setProprietaire(2);
                            break;
                        case 'x':
                            c.setBalle(true);
                            break;
                    }
                }
            }
            reader.close();
        } catch (IOException ignored) {

        }
    }

    public String toString()
    {
        String s="";
        for (int i=0;i<plateau.length;i++)
        {
            for (int j=0;j<plateau[0].length;j++) s+=plateau[i][j] + " ";
            s+="\n";
        }
        return s;
    }
    /* public static void main(String[] args) {

         Diabolik diab = new Diabolik();
         System.out.println(diab);
         int joueurCourant=1;
         boolean erreurSaisie=true;
         int choixRestant=0;
         while (!diab.aGagne(joueurCourant))
         {
             while (erreurSaisie || choixRestant<6)
             {
                 try{
                     System.out.println("Choisissez action 1,2 ou 3");
                     Scanner sc = new Scanner(System.in);
                     action=+sc.nextInt();
                 }
             }
         }

     }*/
    public boolean deplacer(int x,int y,int xD, int yD,int proprietaire)
    {

        //TODO :Verification que c'est dans le tableau
        if(!valeurOK(x,y,xD, yD,proprietaire)) return false;
        if(plateau[x][y].getBalle()) return false;
        if(plateau[xD][yD].getProprietaire()==0)
        {
            int dep=xD-x+yD-y;
            if(dep==-1 || dep==1)
            {
                plateau[xD][yD].setProprietaire(plateau[x][y].getProprietaire());
                plateau[x][y].setProprietaire(0);
                return true;
            }
        }
        return false;
    }
    public boolean passe(int x,int y, int xD,int yD, int proprietaire)
    {
        if(!valeurOK(x,y,xD, yD,proprietaire)) return false;
        if(plateau[xD][yD].getProprietaire()!=proprietaire || plateau[x][y].getProprietaire()!=proprietaire) return false;
        if(xD==x || yD==y || xD-x==yD-y && !trajetMenace(x,y,xD,yD,proprietaire)) {
            plateau[x][y].setBalle(false);
            plateau[xD][yD].setBalle(true);
        }
        return true;
    }
    private boolean trajetMenace(int x,int y, int xD,int yD, int proprietaire){
        if(!valeurOK(x,y,xD, yD,proprietaire)) return false;
        int proprietaireEnnemi;
        if (proprietaire==1) proprietaireEnnemi=2;
        else proprietaireEnnemi=1;

        if(xD == x) {
            if(yD>y) {
                for (int i = y; i < yD; i++) {
                    if (plateau[xD][i].getProprietaire() == proprietaireEnnemi) {
                        return true;
                    }
                }
            }
            if(yD<y) {
                for (int i = yD; i < y; i++) {
                    if (plateau[xD][i].getProprietaire() == proprietaireEnnemi) {
                        return true;
                    }
                }
            }
            return false;
        }
        if(yD == y) {
            if(xD>x) {
                for (int i = x; i < xD; i++) {
                    if (plateau[i][y].getProprietaire() == proprietaireEnnemi) {
                        return true;
                    }
                }
            }
            if(xD<x) {
                for (int i = xD; i < x; i++) {
                    if (plateau[i][y].getProprietaire() == proprietaireEnnemi) {
                        return true;
                    }
                }
            }
            return false;
        }
        if(xD-x==yD-y)
        {
            if(xD>x && yD>y) {
                for (int i = x, j = y; i < xD && j < yD; i++, j++) {
                    if (plateau[i][j].getProprietaire() == proprietaireEnnemi) {
                        return true;
                    }
                }
            }
            if(xD>x && yD<y) {
                for (int i = x, j = y; i < xD && j > yD; i++, j--) {
                    if (plateau[i][j].getProprietaire() == proprietaireEnnemi) {
                        return true;
                    }
                }
            }
            if(xD<x && yD<y) {
                for (int i = xD, j = yD; i < x && j < y; i++, j++) {
                    if (plateau[i][j].getProprietaire() == proprietaireEnnemi) {
                        return true;
                    }
                }
            }
            if(xD<x && yD>y) {
                for (int i = x, j = y; i > xD && j < yD; i--, j++) {
                    if (plateau[i][j].getProprietaire() == proprietaireEnnemi) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public boolean valeurOK(int x,int y, int xD,int yD, int proprietaire){
        if(!plateau[x][y].getBalle()) return false;
        if(x<0 || y<0 || xD<0 || yD<0|| y>plateau[0].length|| x>plateau.length|| xD>plateau.length|| yD>plateau[0].length) return false;
        else return true;
    }
    public boolean aGagne(int proprietaire)
    {
        int ligne;
        if(proprietaire==1) ligne=plateau.length-1;
        else if(proprietaire==2) ligne=0;
        else return false;
        //On considere qu'il y a autant de ligne que de colonne
        for(int i=0;i<plateau.length;i++)
        {
            Case it = plateau[ligne][i];
            if(it.getBalle() && it.getProprietaire() == proprietaire) {
                return true;
            }
        }
        return false;
    }
    public boolean antiJeu(int proprietaire){
        int x=-1,y=-1,oldX=-1,oldY=-1;
        int[][] tabVoisin;
        int proprietaireEnnemi;
        if (proprietaire==1) proprietaireEnnemi=2;
        else proprietaireEnnemi=1;
        int cptVoisin=0;
        int cptAdv=0;
        for(int i=0;i<6;i++) {
            if (plateau[i][0].getProprietaire() == proprietaire){
                x=i;
                y=0;
            }
        }
        if(x==-1) return false;
        while(cptVoisin!=6){
            tabVoisin=caseVoisine(x,y);
            if (plateau[tabVoisin[1][0]][tabVoisin[1][1]].getProprietaire()==proprietaire)
            {
                cptVoisin++;

            }
            for(int i=1;i<4;i++)
            {
                x=tabVoisin[i][0];
                y=tabVoisin[i][1];
                if (x!=-1 && y !=-1 &&  plateau[x][y].getProprietaire()==proprietaire) {
                    cptVoisin++;
                    break;
                }
                else x=-1;
            }
            if(x==-1) break;
            int xTmp,yTmp;
            for(int i=0;i<8;i=i+4)
            {
                xTmp=tabVoisin[i][0];
                yTmp=tabVoisin[i][1];
                if (xTmp!=-1 && y !=-1 && plateau[xTmp][yTmp].getProprietaire()==proprietaireEnnemi) {
                    cptAdv++;
                }
            }


        }
        System.out.println(cptVoisin +" " + cptAdv);
        if(cptVoisin<6 || cptAdv < 3)return false;
        else return true;
    }
    public int[][] caseVoisine(int x, int y){
        int[][] tabVal = new int[8][2];
        for(int i=0;i<8;i++){
            try{
            switch (i)
            {
                case 0:
                    tabVal[i][0]=x+1;
                    tabVal[i][1]=y;
                    break;
                case 1:
                    tabVal[i][0]=x+1;
                    tabVal[i][1]=y+1;
                    break;
                case 2:
                    tabVal[i][0]=x;
                    tabVal[i][1]=y+1;
                    break;
                case 3:
                    tabVal[i][0]=x-1;
                    tabVal[i][1]=y+1;
                    break;
                case 4:
                    tabVal[i][0]=x-1;
                    tabVal[i][1]=y;
                    break;
                case 5:
                    tabVal[i][0]=x-1;
                    tabVal[i][1]=y-1;
                    break;
                case 6:
                    tabVal[i][0]=x;
                    tabVal[i][1]=y-1;
                    break;
                case 7:
                    tabVal[i][0]=x+1;
                    tabVal[i][1]=y-1;
                    break;
            }
        }
            catch(Exception e)
            {

            }

        }
        return tabVal;
    }

    @Override
    public boolean estTerminee() {
        return aGagne(1) || aGagne(2) || antiJeu(1) || antiJeu(2);
    }

    public int getGagnant() {
        if(aGagne(1))
            return 1;
        else if(aGagne(2))
            return 2;
        else if(antiJeu(1))
            return 2;
        else if(antiJeu(2))
            return 1;
        else return 0;
    }
//    public boolean cheminMenacer;
    /*public boolean caseMenacer(int x, int y, int proprietaire)
    {
        if (proprietaire==1) proprietaire=2;
        else proprietaire=1;
        boolean menace=false;
        for(int i=x;i<plateau.length || x==i-1;i++)
        {
            if (plateau[i][y].getProprietaire()==proprietaire)
            {
                if(menace) return true;
                else menace=true;
                i=0;
            }
        }
        for(int i=y;i<plateau.length && y!=i-1;i++)
        {
            if (plateau[x][i].getProprietaire()==proprietaire)
            {
                if(menace) return true;
                else menace=true;
                i=0;
            }
        }

        for(int i=x,j=y;(i<plateau.length && y<plateau.length) || x==i-1;i++)
        {
            if (plateau[i][y].getProprietaire()==proprietaire)
            {
                if(menace) return true;
                else menace=true;
                i=0;
            }
        }
        for(int i=x;i<plateau.length || x==i-1;i++)
        {
            if (plateau[i][y].getProprietaire()==proprietaire)
            {
                if(menace) return true;
                else menace=true;
                i=0;
            }
        }
        return false;

    }*/
}
