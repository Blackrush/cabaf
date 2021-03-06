/**
 * @author Brieuc DE TAPPIE INFOC1
 */
public class DiabalikModele {

    Case[][] plateau = new Case[7][7];
    public DiabalikModele()
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
        if(x<0 || y<0 || xD<0 || yD<0|| y>plateau.length|| x>plateau.length|| yD>plateau.length|| yD>plateau.length) return false;
        if(plateau[xD][yD].getProprietaire()!=0 || plateau[x][y].getProprietaire()!=proprietaire) return false;
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
    public boolean passe(int x,int y, int xD,int yD, int propritetaire)
    {
        if(!plateau[x][y].getBalle()) return false;
        if(x<0 || y<0 || xD<0 || yD<0|| y>plateau[0].length|| x>plateau.length|| xD>plateau.length|| yD>plateau[0].length) return false;
        if(plateau[xD][yD].getProprietaire()!=propritetaire || plateau[x][y].getProprietaire()!=propritetaire) return false;
        if(xD==x || yD==y || xD-x==yD-y) {
            plateau[x][y].setBalle(false);
            plateau[xD][yD].setBalle(true);
        }
        return true;
    }
    public boolean aGagne(int proprietaire)
    {
        int ligne;
        if(proprietaire==1) ligne=plateau.length;
        else if(proprietaire==2) ligne=0;
        else return false;
        //On considere qu'il y a autant de ligne que de colonne
        for(int i=0;i<ligne;i++)
        {
            if(plateau[ligne][i].getBalle()) return true;
        }
        return false;
    }
    public boolean cheminMenacer;

    public int changerTour(int j) {
        if(j==1){return 2;}
        else{return 1;}
    }
}
