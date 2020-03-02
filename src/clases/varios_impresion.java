/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.ArrayList;

/**
 *
 * @author gerenciatecnica
 */
public class varios_impresion {

    public static String centrar_texto(int largo, String titulo) {
        String texto;
        int largo_actual = titulo.length();
        int faltantes = (largo - largo_actual) / 2;
        String espacio = "";
        if (faltantes >= 0) {
            for (int i = 0; i < faltantes; i++) {
                espacio = espacio + " ";
            }
            texto = espacio + titulo + espacio;
        } else {
            texto = titulo.substring(0, largo);
        }

        return texto;
    }

    public static String texto_izquierda(int largo, String titulo) {
        String texto;
        int largo_actual = titulo.length();
        int faltantes = (largo - largo_actual);
        String espacio = "";
        if (faltantes >= 0) {
            for (int i = 0; i < faltantes; i++) {
                espacio = espacio + " ";
            }
            texto = titulo + espacio;
        } else {
            texto = titulo.substring(0, largo);
        }
        return texto;
    }

    public static String texto_derecha(int largo, String texto) {
        String nuevo_texto;
        int largo_actual = texto.length();
        int faltantes = (largo - largo_actual);
        String espacio = "";
        for (int i = 0; i < faltantes; i++) {
            espacio = espacio + " ";
        }
        nuevo_texto = espacio + texto;
        return nuevo_texto;
    }

    public static String remove(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public static String enter() {
        String texto = "\n";
//        String espacio = "";
//        for (int i = 0; i < 40; i++) {
//            espacio = espacio + " ";
//        }
//        texto = espacio;
        return texto;
    }

    public static String linea() {
        String texto;
        String espacio = "";
        for (int i = 0; i < 40; i++) {
            espacio = espacio + "-";
        }
        texto = espacio;
        return texto;
    }
}
