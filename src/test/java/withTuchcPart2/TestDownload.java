package withTuchcPart2;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.Selenide;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TestDownload {

//    static {         ЕСЛИ ВДРУГ ФАЙЛ СКАЧИВАЕТСЯ БЕЗ ХРЕФА
//        Configuration.fileDownload = FileDownloadMode.PROXY;
//    }

    @DisplayName("Скачивание файла")
    @Test
    void downloadTestJUnit5Git() throws Exception {
        Selenide.open("https://github.com/junit-team/junit5/blob/main/README.md");
        File downloadFile = $("#raw-url").download();
        assertThat(FileUtils.readFileToString(downloadFile).contains("This repository"));
        try (InputStream is = new FileInputStream(downloadFile);) {
            byte[] fileContent = is.readAllBytes();
            String asString = new String(fileContent, StandardCharsets.UTF_8);
            assertThat(asString.contains("This repository"));
        }
            }
    @Test
    @DisplayName("Загрузка файла")
    void uploadFile() {
        open("https://the-internet.herokuapp.com/upload");
        $("input[type=file]").uploadFromClasspath("username.csv");
        $("#file-submit").click();
        $("#uploaded-files").shouldHave(Condition.text("username.csv"));


    }
}
