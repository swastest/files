package xls.file;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Stream;

///НИЧЕГО НЕ ПОЛУЧИЛОСЬ

public class XlsxTest {


    @DataProvider
    public Object[][] usersFromSheet1() throws Exception {
        String path = "src/test/resources/Auto.xlsx";
        ExelReader excelReader = new ExelReader(path);
        return excelReader.getSheetDataForTDD();
    }

   @Test( dataProvider = "usersFromSheet1")
    void test001(String param1, String param2){
        System.out.println( param1 + "=="+ param2);

    }

    static Stream<Arguments> TestMethodSource() {
        return Stream.of(
                Arguments.of("Vladislav", "Privet", List.of(12)),
                Arguments.of("Privet", "Poka", List.of(23))
        );
    }

    @ParameterizedTest(name = "TestMethodSource")
    @MethodSource("TestMethodSource")
    void test01(String value1, String value2, List<Long> value3){
        System.out.println(value1 + value2 + value3);
    }

    @CsvSource(value = {
            "Vlad, Kornev , ghy@jjh.ua",
            "Valentin , Bugaga,  hgf@dff.ru",
            "Dodo, Pizza, vkusno@dodo.com"
    })
    @ParameterizedTest(name = "Csv Source")
    void TestWithCsvSours(String testDataName, String testDataLastN, String testDataMail){
        System.out.println( testDataName + " " + testDataLastN+ " "+testDataMail);
    }


}
