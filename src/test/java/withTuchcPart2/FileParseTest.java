package withTuchcPart2;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class FileParseTest {
    @Test
    void pdfTest() throws IOException {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        // пишу название файла Path from source Root
        File downloadPdfFile = $("a[href*='junit-user-guide-5.9.1.pdf']").download();
        PDF pdf = new PDF(downloadPdfFile);
        assertThat(pdf.author).isEqualTo("Stefan Bechtold, Sam Brannen, Johannes Link, Matthias Merdes, Marc Philipp, Juliette de Rancourt, Christian Stein");
    }

    @Test
    void xlsTest() throws FileNotFoundException {
            Selenide.open("http://romashka2008.ru/price");
            File downloadXlsFile = $(".site-main__inner a[href*='prajs']").download();
            /// ВАЖНО "звездочка" * указывает на содержание в себе части контента $(".site-main__inner a[href*='prajs']")")
            XLS xls = new XLS(downloadXlsFile);
        assertThat(xls.excel.getSheetAt(0)  // номер страницы(табы)
                .getRow(34)  // номер поля-строчки
                .getCell(2)   // номер столбца
                .getStringCellValue()).isEqualTo("        Бумага для широкоформатных принтеров и чертежных работ");
//
//getSheet("Бумага для цветной печати");  - ищет по контенту в общем


        }

    @Test
    void csvTest() {

    }

    @Test
    void zipTest() {

    }

    @Test
    void jsonTest() {

    }
}
