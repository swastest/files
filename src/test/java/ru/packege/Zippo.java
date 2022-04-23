package ru.packege;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class Zippo {
    @Test
    void zipPdf() throws Exception {
        ClassLoader classLoader = Zippo.class.getClassLoader();
        ZipFile zf = new ZipFile(new File("src/test/resources/pdf.zip"));
        try (ZipInputStream zis = new ZipInputStream(classLoader.getResourceAsStream("pdf.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo("junit-user-guide-5.8.2.pdf");
                PDF pdf = new PDF(zis);
                Assertions.assertThat(pdf.text).contains("JUnit");
                assertThat(pdf.author).contains("Marc");
                assertThat(pdf.numberOfPages).isEqualTo(166);
            }
        }
    }

    @Test
    void zipCsv() throws Exception {
        ClassLoader classLoader = Zippo.class.getClassLoader();
        ZipFile zf = new ZipFile(new File("src/test/resources/csv.zip"));
        try (ZipInputStream zis = new ZipInputStream(classLoader.getResourceAsStream("csv.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo("username.csv");
                try (InputStream is = zf.getInputStream(entry)) {
                    try (CSVReader reader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                        List<String[]> content = reader.readAll();
                        Assertions.assertThat(content.get(0)).contains(
                                "Username; Identifier;First name;Last name"
                        );
                        Assertions.assertThat(content.get(1)).contains(
                                "booker12;9012;Rachel;Booker"
                        );
                    }
                }
            }
        }
    }

    @Test
    void zipXls() throws Exception {
        ClassLoader classLoader = Zippo.class.getClassLoader();
        ZipFile zf = new ZipFile(new File("src/test/resources/xls.zip"));
        try (ZipInputStream zis = new ZipInputStream(classLoader.getResourceAsStream("xls.zip"))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                org.assertj.core.api.Assertions.assertThat(entry.getName()).isEqualTo("Pairwise.xlsx");
                try (InputStream is = zf.getInputStream(entry)) {
                    XLS xls = new XLS(is);
                    xls.excel.getSheetAt(0)
                            .getRow(3)
                            .getCell(3)
                            .getStringCellValue().contains("ресницы");
                }
            }
        }
    }
}
