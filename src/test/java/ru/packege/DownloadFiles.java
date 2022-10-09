package ru.packege;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.$;


public class DownloadFiles {

    @BeforeAll
    static void config(){
        Configuration.browserSize = "1920x1080";
    }

    @DisplayName("Файл, который нужно парсить")
    @Test
    void downloadTestJUnit5Git() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloadFile = $("#raw-url").download();
        try (InputStream is = new FileInputStream(downloadFile)) {
            assertThat(new String(is.readAllBytes(), StandardCharsets.UTF_8)).contains("This repository");
        }
    }

    /*String readString = Files.readString(downloadFile.toPath(), StandardCharsets.UTF_8);
    Это более простая альтернатива, которая делает то же самое, что и код выше, но правильнее все-такие делать через try
     */
    @DisplayName("Файл обычный ПДФ")
    @Test
    void downloadTestPdf() throws IOException {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        //  $(By.linkText("PDF download")).uploadFromClasspath("1.png"); - этоп росто пример загрузки файла
        // пишу название файла Path from source Root
        File downloadPdfFile = $(By.linkText("PDF download")).download();
        PDF pdf = new PDF(downloadPdfFile);
        assertThat(pdf.text).contains("JUnit");
        assertThat(pdf.author).contains("Marc");
        assertThat(pdf.numberOfPages).isEqualTo(179);
        assertThat(pdf.title).isEqualTo("JUnit 5 User Guide");
       int a = 0;
    }

    @DisplayName("Файл обычный Эксель")
    @Test
    void downloadTestXls() throws Exception {
        Selenide.open("http://romashka2008.ru/price");
        File downloadXlsFile = $(".site-main__inner a[href*='prajs']").download();
        /// ВАЖНО "звездочка" * указывает на содержание в себе части контента $(".site-main__inner a[href*='prajs']")")
        XLS xls = new XLS(downloadXlsFile);
        xls.excel.getSheetAt(0)  // номер страницы(табы)
                .getRow(34)  // номер поля
                .getCell(2)   // номер столбца
                .getStringCellValue().contains("3 313,04 руб."); // содержит в себе
//
//getSheet("Бумага для цветной печати");  - ищет по контенту в общем


    }

    @DisplayName("Большая удача найти Csv  в интернетах")
    @Test
    void privetCsv() throws Exception {
        ClassLoader classLoader = DownloadFiles.class.getClassLoader(); // пишу название класса
        try (InputStream is = classLoader.getResourceAsStream("username.csv");
             CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            List<String[]> content = reader.readAll();
            assertThat(content.get(4)).contains("jenkins46;9346;Mary;Jenkins");
        }
    }

    @DisplayName("Прекрасный зип-файл нашла")
    @Test
    void zipZip() throws Exception {
        ClassLoader classLoader = DownloadFiles.class.getClassLoader();
        // можно вынести в поле класса, если нужно ис-ть в нескольких тестах
        try (InputStream is = classLoader.getResourceAsStream("sample-zip-file.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry; //объявила переменную
            //ниже пишу цикл while
            while ((entry = zis.getNextEntry()) != null) {
                assertThat(entry.getName()).isEqualTo("sample.txt");
            }
        }
    }


}

