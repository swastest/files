package ru.packege;

import com.codeborne.pdftest.PDF;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Zippo {
@Test
    void zipPdf () throws Exception {
        ClassLoader classLoader = Zippo.class.getClassLoader();
        ZipFile zf = new ZipFile(new File("src/test/resources/pdf.zip"));
        try (ZipInputStream is = new ZipInputStream(classLoader.getResourceAsStream("pdf.zip"))){
            ZipEntry entry;
            while ((entry = is.getNextEntry()) !=null){
                org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo("junit-user-guide-5.8.2.pdf");
                PDF pdf = new PDF(is);
                Assertions.assertThat(pdf.text).contains("JUnit");
            }
        }
    }
}
