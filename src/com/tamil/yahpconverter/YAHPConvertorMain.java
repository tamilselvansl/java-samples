package com.tamil.yahpconverter;

import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;
import static org.allcolor.yahp.converter.IHtmlToPdfTransformer.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author Tamilselvan Teivasekamani
 *
 *
 * A Class to convert HTML file content to desired ".PDF" file by using YAHP-Converter (https://github.com/allcolor/YaHP-Converter)
 *
 * Have to include yahp.jar (from "/lib") file alone to run this sample application. nothing needed apart from this single jar (converted all in one)
 *
 */

public class YAHPConvertorMain {

    private String htmlFileName = "C:\\test.html";
    private String pdfFileName = "c:\\test.pdf";
    private StringBuilder htmlContent = new StringBuilder();

    private void doProcess() {
        BufferedReader br = null;
        FileOutputStream out;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(htmlFileName));
            while ((sCurrentLine = br.readLine()) != null) {
                htmlContent.append(sCurrentLine);
            }

            File fout = new File(pdfFileName);

            out = new FileOutputStream(fout);

            CYaHPConverter converter = new CYaHPConverter(false);
            Map<String, String> properties = new HashMap<String, String>();
            List headerFooterList = new ArrayList();
            properties.put(PDF_RENDERER_CLASS, FLYINGSAUCER_PDF_RENDERER);
            converter.convertToPdf(htmlContent.toString(), A4P, headerFooterList, "file://temp/", out, properties);
            out.flush();
            out.close();
            System.out.println("PDF file saved at : " + fout.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IHtmlToPdfTransformer.CConvertException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) { ex.printStackTrace();}

        }
    }

    public static void main(String[] args) {
        new YAHPConvertorMain().doProcess();
    }
}
