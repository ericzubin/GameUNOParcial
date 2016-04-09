/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorchat2;

/**
 *
 * @author Eric
 */
public class Carta {
    private String Color;
    private String Valor;

    Carta(String color, String valor) {
        Color=color;
        Valor=valor;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String Color) {
        this.Color = Color;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }
    
    
    
}
