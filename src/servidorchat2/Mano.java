/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorchat2;

import java.util.ArrayList;

/**
 *
 * @author Eric
 */
public class Mano {
          public static ArrayList<Carta> tusCartas =new ArrayList<Carta>();

    Mano(ArrayList<Carta> tusCartas) {
        this.tusCartas =new ArrayList<Carta>();
        this.tusCartas.addAll(tusCartas);
        //System.out.println("Desde la mano: "+tusCartas.size());
    }

    public static ArrayList<Carta> getTusCartas() {
        return tusCartas;
    }

    public  void setTusCartas(ArrayList<Carta> tusCartas) {
        Mano.tusCartas = tusCartas;
    }
    public Carta getCartaM(int Mano)
    {
        return tusCartas.get(Mano);
    }
       public void removerCarta(int Mano)
    {
        tusCartas.remove(Mano);
    }
 public  int sizeCartas() {
return tusCartas.size();
        }
    
}
