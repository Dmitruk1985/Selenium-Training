import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UnitTest {
    public static void main(String[] arg) {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("ddMMyyyy");

        System.out.println("Текущая дата " + formatForDateNow.format(dateNow));
    }
}
