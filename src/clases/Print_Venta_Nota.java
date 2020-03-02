/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author gerenciatecnica
 */
public class Print_Venta_Nota {

    leer_numeros leer = new leer_numeros();
    cl_varios c_varios = new cl_varios();

    private int id_venta;
    private int id_almacen;

    public Print_Venta_Nota() {
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_almacen() {
        return id_almacen;
    }

    public void setId_almacen(int id_almacen) {
        this.id_almacen = id_almacen;
    }

    public void generar_ticket() {
        PrinterMatrix printer = new PrinterMatrix();

        //40 es el ancho de texto de un ticket de 56 mm
        //para las impresoras matriciales como la  Epson LX-300+II es 80 el ancho de caracteres
      
        int contar_productos = 3;

        String serie = "B001";
        String numero = "0000003";

        //  Extenso e = new Extenso();
        //   e.setNumber(101.85);
        printer.setOutSize(31 + contar_productos, 40);

        //imprimir cabezera
        printer.printTextLinCol(1, 1, varios_impresion.centrar_texto(40, "** CASA DE LA BIBLIA **"));
        printer.printTextLinCol(2, 1, varios_impresion.centrar_texto(40, "RUC: 20532059250"));
        printer.printTextLinCol(3, 1, varios_impresion.centrar_texto(40, "ESCOBEDO MOZO HENRY EDUARDO"));
        printer.printTextWrap(3, 4, 0, 40, "JR MANUEL RUIZ 4605 - CHIMBOTE");

        printer.printTextLinCol(7, 1, varios_impresion.centrar_texto(40, "SUCURSAL: JR MANUEL RUIZ 4605 - CHIMBOTE"));

        printer.printTextLinCol(8, 1, varios_impresion.centrar_texto(40, "BOLETA DE VENTA"));
        printer.printTextLinCol(9, 1, varios_impresion.centrar_texto(40, serie + " - " + numero));
        printer.printTextLinCol(10, 1, "FECHA EMISION: 14:00:25 PM" );

        //cargar detalle de productos
        int add_filas = 0;
        double suma_total = 0;
/*            String query = "SELECT p.id_producto, p.descripcion, pv.cantidad,"
                    + " pv.precio FROM productos_ventas AS pv "
                    + "INNER JOIN productos AS p ON p.id_producto = pv.id_producto "
                    + "WHERE  pv.id_ventas = '" + c_venta.getId_venta() + "'";
*/
            for (int i = 0; i < 3; i++) {
                String pdescripcion = "PRODUCTO " + i;
                //si cantidad de letras de descripcion es mayor a 68 , aplicar substring a 67
                if (pdescripcion.length() > 68) {
                    pdescripcion = pdescripcion.substring(0, 66);
                }
                if (pdescripcion.length() > 26 & pdescripcion.length() < 29) {
                    pdescripcion = pdescripcion.substring(0, 26);
                }

                int pcantidad = 1;
                double pprecio = 5;
                double pparcial = pprecio * pcantidad;
                suma_total += pparcial;
                //recomendable usar un formateador
                String sparcial = c_varios.formato_totales(pparcial);

                String texto_linea = pcantidad + " " + pdescripcion;

                //imprimir linea producto
                printer.printTextWrap(11 + add_filas, 15 + add_filas + 1, 0, 40, texto_linea);
                add_filas++;

                //si cantidad de letras de descripcion es mayor a 28 saltar una linea
                if (texto_linea.length() > 28) {
                    add_filas++;
                }

                //imprimir linea parcial
                printer.printTextLinCol(11 + add_filas, 29, " x " + varios_impresion.texto_derecha(9, sparcial));
            }

        //imprimir pie de ticket
        double total = suma_total;
        double subtotal = total / 1.18;
        double igv = total / 1.18 * 0.18;
        String numeros_texto = leer.Convertir(total + "", true) + " SOLES";

        add_filas++;
        add_filas++;

        printer.printTextLinCol(11 + add_filas, 1, varios_impresion.texto_derecha(30, "TOTAL"));
        printer.printTextLinCol(11 + add_filas, 31, varios_impresion.texto_derecha(10, c_varios.formato_totales(total)));

        add_filas++;
        printer.printTextWrap(11 + add_filas, 14 + add_filas + 1, 0, 40, numeros_texto);

        //mostrar en consola
        printer.show();

        //grabar en txt
        printer.toFile("impresion.txt");

        //enviar a imprimir
        //leer archivo
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("impresion.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if (inputStream == null) {
            return;
        }

        //comandos impresora para reiniciar y cortar
        byte[] initEP = new byte[]{0x1b, '@'};
        byte[] cutP = new byte[]{0x1d, 'V', 1};

        //inciiar servicio impresion
        //enviara a imprimir a impresora por defecto
        PrinterService printerService = new PrinterService();
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        
        printerService.printBytes(defaultPrintService.getName(), initEP);
        
        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;

        Doc document = new SimpleDoc(inputStream, docFormat, null);

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(document, attributeSet);

            } catch (PrintException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "error al imprimir \n" + ex.getLocalizedMessage());
            }
        } else {
            System.err.println("No existen impresoras instaladas");
        }
        //enviar comando de corte
        printerService.printBytes(defaultPrintService.getName(), cutP);
    }
}
