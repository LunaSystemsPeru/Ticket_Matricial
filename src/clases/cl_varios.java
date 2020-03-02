/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Administrador
 */
public class cl_varios {

    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    DecimalFormat formato = null;

    /*
    @param number = 123548.00
    return ######0.00 a dos decimales sin comas de miles
     */
    public String formato_numero(Double number) {
        simbolo.setDecimalSeparator('.');
        formato = new DecimalFormat("######0.00", simbolo);
        String numero = "";
        numero = formato.format(number);
        return numero;
    }

    /*
    @param number = 123548.00
    return ######0.000 a tres decimales sin comas de miles
     */
    public String formato_tc(Double number) {
        simbolo.setDecimalSeparator('.');
        formato = new DecimalFormat("######0.000", simbolo);
        String numero = "";
        numero = formato.format(number);
        return numero;
    }

    /*
    @param numero = #,###,##0.00 //para mostrar en totales , no suma
    return #,###,##0.00 con comas de miles a dos decimales
     */
    public String formato_totales(Double number) {
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        formato = new DecimalFormat("#,###,##0.00", simbolo);
        String numero = "";
        numero = formato.format(number);
        return numero;
    }

    
    public void solo_numeros(KeyEvent evt) {
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9')) {
            evt.consume();
        }
    }

    public void solo_precio(KeyEvent evt) {
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && car != '.') {
            evt.consume();
        }
    }

    public void limitar_caracteres(KeyEvent evt, JTextField txt, int longitud) {
        if (txt.getText().length() == longitud) {
            evt.consume();
        }
    }

   
    public String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
        return formateador.format(ahora);
    }
    
    public String getanio() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy");
        return formateador.format(ahora);
    }

    public String getFechaActual_sfs() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd");
        return formateador.format(ahora);
    }

    public String getFechaHora() {
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return hourdateFormat.format(date);

    }

    public String getHoraActual() {
        Calendar calendario = new GregorianCalendar();
        int hora, minutos, segundos;

        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);

        String hora_exacta = ceros_izquieda_numero(2, hora) + ":" + ceros_izquieda_numero(2, minutos) + ":" + ceros_izquieda_numero(2, segundos);

        return hora_exacta;
    }

    public String fecha_usuario(String fecha) {
        String m_fecha = null;
        try {
            DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fec = df.parse(fecha);
            m_fecha = dt.format(fec);
        } catch (ParseException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        }
        return m_fecha;
    }

    public String fecha_myql(String fecha) {
        String m_fecha = null;
        try {
            DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fec = dt.parse(fecha);
            m_fecha = df.format(fec);
        } catch (ParseException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        }
        return m_fecha;
    }

    // Suma los días recibidos a la fecha  
    public Date suma_dia(String fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(fecha);
            calendar.setTime(date); // Configuramos la fecha que se recibe
            calendar.add(Calendar.DAY_OF_YEAR, dias);  // numero de días a añadir, o restar en caso de días<0
        } catch (ParseException ex) {
            Logger.getLogger(cl_varios.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
        }
        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }

    public void centrar_celda(JTable table, int col) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(col).setCellRenderer(tcr);
    }

    public void derecha_celda(JTable table, int col) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(col).setCellRenderer(tcr);
    }

    public String leer_archivo(String nom_arc) {
        String linea = null;
        try {
            File Ffichero = new File(nom_arc);
            /*Si existe el fichero*/
            if (Ffichero.exists()) {
                /*Abre un flujo de lectura a el fichero*/
                BufferedReader Flee = new BufferedReader(new FileReader(Ffichero));
                String Slinea;
                /*Lee el fichero linea a linea hasta llegar a la ultima*/
                while ((Slinea = Flee.readLine()) != null) {
                    /*Imprime la linea leida*/
                    linea = Slinea;
                }
                /*Cierra el flujo*/
                Flee.close();
            } else {
                System.out.println("Fichero No Existe");
                linea = "NO ALMACEN";
            }
        } catch (IOException ex) {
            /*Captura un posible error y le imprime en pantalla*/
            System.out.println(ex.getMessage());
        }
        return linea;
    }

    public String obtener_periodo() {
        String periodo;
        Calendar now = Calendar.getInstance();
        periodo = now.get(Calendar.YEAR) + ceros_izquieda_numero(2, now.get(Calendar.MONTH) + 1);
        return periodo;
    }

    public int obtener_mes() {
        int periodo;
        Calendar now = Calendar.getInstance();
        periodo = now.get(Calendar.MONTH);
        return periodo;
    }

    public int obtener_anio() {
        int periodo;
        Calendar now = Calendar.getInstance();
        periodo = now.get(Calendar.YEAR);
        return periodo;
    }

    public String ceros_izquieda_numero(int cantidad, int numero) {
        return String.format("%0" + cantidad + "d", numero);

    }

    public String ceros_izquieda_letras(int cantidad, String numero) {
        while (numero.length() < cantidad) {
            numero = "0" + numero;
        }
        return numero;
    }

    public boolean esEntero(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public boolean esDecimal(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public Boolean validar_RUC(String ruc) {
        Boolean validado = false;
        int dig[] = new int[10];
        int factores[] = {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};
        //  System.out.println("digitos del ruc");
        for (int i = 0; i < 10; i++) {
            dig[i] = Integer.parseInt(ruc.charAt(i) + "");
            System.out.println(dig[i] + "\t");
        }
        int producto[] = new int[10];
        //   System.out.println("producto de cada digito");
        for (int i = 0; i < 10; i++) {
            producto[i] = dig[i] * factores[i];
            //   System.out.println(producto[i]);
        }
        int suma_producto = 0;
        //     System.out.println("suma total del producto");
        for (int i = 0; i < 10; i++) {
            suma_producto += producto[i];
        }
        //     System.out.println(suma_producto);
        //     System.out.println("Resultado de formula");
        int formula = 11 - (suma_producto % 11);
        //       System.out.println(formula);
        String resultado = formula + "";
//        System.out.println("longitud de resultado " + resultado.length());
        int longitud = resultado.length();
        String ultimo = resultado.charAt(longitud - 1) + "";
        //       System.out.println("ultimo digito " + ultimo);
        int dig11 = Integer.parseInt(ruc.charAt(10) + "");
        //       System.out.println("comparando " + ultimo + " = " + dig11);
        if (dig11 == Integer.parseInt(ultimo)) {
            validado = true;
        }
//        System.out.println(validado);
        return validado;
    }

    public String obtenerDireccionCarpeta() {
        File midireccion = new File(".");
        String path = null;
        try {
            path = midireccion.getCanonicalPath();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return path;
    }

    public static int diasDelMes(int mes, int año) {
        switch (mes) {
            case 0:  // Enero
            case 2:  // Marzo
            case 4:  // Mayo
            case 6:  // Julio
            case 7:  // Agosto
            case 9:  // Octubre
            case 11: // Diciembre
                return 31;
            case 3:  // Abril
            case 5:  // Junio
            case 8:  // Septiembre
            case 10: // Noviembre
                return 30;
            case 1:  // Febrero
                if (((año % 100 == 0) && (año % 400 == 0))
                        || ((año % 100 != 0) && (año % 4 == 0))) {
                    return 29;  // Año Bisiesto
                } else {
                    return 28;
                }
            default:
                throw new java.lang.IllegalArgumentException(
                        "El mes debe estar entre 0 y 11");
        }
    }
}
