package ru.packege;

import com.codeborne.pdftest.PDF;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class Zippo {
@Test
    void zipPdf () throws Exception {
        ClassLoader classLoader = Zippo.class.getClassLoader();
        ZipFile zf = new ZipFile(new File("src/test/resources/pdf.zip"));
        try (ZipInputStream zis = new ZipInputStream(classLoader.getResourceAsStream("pdf.zip"))){
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) !=null){
                org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo("junit-user-guide-5.8.2.pdf");
                PDF pdf = new PDF(zis);
                Assertions.assertThat(pdf.text).contains("JUnit");
                assertThat(pdf.author).contains("Marc");
                assertThat(pdf.numberOfPages).isEqualTo(166);
            }
        }
    }

    @Test
    void zipCsv () throws Exception {
        ClassLoader classLoader = Zippo.class.getClassLoader();
    ZipFile zf = new ZipFile( new File("src/test/resources/csv.zip"));
    try (ZipInputStream zis = new ZipInputStream(classLoader.getResourceAsStream("csv.zip"))){
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) !=null){
            org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo("username.csv");


        }

    }

    }
}
