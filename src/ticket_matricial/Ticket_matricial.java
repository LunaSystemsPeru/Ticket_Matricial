/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ticket_matricial;

import clases.Print_Venta_Nota;

/**
 *
 * @author luis
 */
public class Ticket_matricial {

    public static Print_Venta_Nota printin = new Print_Venta_Nota();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        printin.generar_ticket();
    }
    
}
