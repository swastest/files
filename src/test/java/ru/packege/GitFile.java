package ru.packege;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;
import org.openqa.selenium.By;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.codeborne.pdftest.assertj.Assertions.assertThat;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class GitFile {
    @DisplayName("Файл, который нужно парсить")
    @Test
    void DownloadTestJUnit5Git() throws Exception {
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
    void DownloadTestPdf() throws Exception {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        //  $(By.linkText("PDF download")).uploadFromClasspath("1.png"); - этоп росто пример загрузки файла
        // пишу название файла Path from source Root
        File downloadPdfFile = $(By.linkText("PDF download")).download();
        PDF pdf = new PDF(downloadPdfFile);
        assertThat(pdf.text).contains("JUnit");
        assertThat(pdf.author).contains("Marc");
        assertThat(pdf.numberOfPages).isEqualTo(166);
    }
    @DisplayName("Файл обычный Эксель")
    @Test
    void DownloadTestXls() throws Exception {
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
    void privetCsv () throws Exception {
    Selenide.open("https://support.staffbase.com/hc/en-us/articles/360007108391-CSV-File-Examples");
    File downloadCsv = $(byText("Download: CSV File with the Minimum Data Set for Username Onboarding")).download();
   CSVReader reader = new CSVReader(downloadCsv);

        }

    }

