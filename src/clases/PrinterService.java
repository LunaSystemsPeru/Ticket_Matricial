/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author gerenciatecnica
 */
public class PrinterService {

    public List<String> getPrinters() {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);

        List<String> printerList = new ArrayList<String>();
        for (PrintService printerService : printServices) {
            printerList.add(printerService.getName());
        }

        return printerList;
    }

//    @Override
//    public int print(Graphics g, PageFormat pf, int page)
//            throws PrinterException {
//        if (page > 0) { /* We have only one page, and 'page' is zero-based */
//
//            return NO_SUCH_PAGE;
//        }
//
//        /*
//         * User (0,0) is typically outside the imageable area, so we must
//         * translate by the X and Y values in the PageFormat to avoid clipping
//         */
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.translate(pf.getImageableX(), pf.getImageableY());
//        /* Now we perform our rendering */
//
//        g.setFont(new Font("Roman", 0, 8));
//        g.drawString("Hello world !", 0, 10);
//
//        return PAGE_EXISTS;
//    }
    public void printString(String printerName, String text) {

        // find the printService of name printerName
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);

        if (printService.length > 0) {
            PrintService service = findPrintService(printerName, printService);

            DocPrintJob job = service.createPrintJob();

            try {

                byte[] bytes;

                // important for umlaut chars
                bytes = text.getBytes("CP437");

                Doc doc = new SimpleDoc(bytes, flavor, null);

                job.print(doc, null);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void printBytes(String printerName, byte[] bytes) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            Doc doc = new SimpleDoc(bytes, flavor, null);
            try {
                printJob.print(doc, null);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.err.println("No existen impresoras instaladas");
        }

        /*
         DocPrintJob job = service.createPrintJob();

         try {

         Doc doc = new SimpleDoc(bytes, flavor, null);

         job.print(doc, null);

         } catch (Exception e) {
         e.printStackTrace();
         }*/
    }

    private PrintService findPrintService(String printerName,
            PrintService[] services) {
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }

        return null;
    }
}
