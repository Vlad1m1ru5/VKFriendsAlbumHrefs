import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class PhotoFinder {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\ProgrammFiles\\Selenium-java-3.141.59\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();

        String usrLogin;
        String usrPass;

        // Консольный интерфейс
        System.out.println("Поиск фото в ВК");
        System.out.println("Укажите логин и пароль для авторизации");
        System.out.print("Логин: "); usrLogin = scan.nextLine();
        System.out.print("Пароль: "); usrPass = scan.nextLine();

        // Авторизация Вк
        driver.get("https://vk.com");
        driver.findElement(By.id("index_email")).sendKeys(usrLogin);
        driver.findElement(By.id("index_pass")).sendKeys(usrPass);
        driver.findElement(By.id("index_login_button")).click();

        // Проверка ошибки авторизации
        while (driver.getCurrentUrl().equals("https://vk.com/")) {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }

        // Ошибка авторизации
        while (driver.getCurrentUrl().contains("https://vk.com/login?m=1&email=%")) {
            // Изменить пароль
            System.out.println("Неверный пароль пользователя " + usrLogin);
            System.out.print("Другой пароль: "); usrPass = scan.nextLine();

            // Повторная авторизация
            driver.findElement(By.id("pass")).sendKeys(usrPass);
            driver.findElement(By.id("login_button")).click();
        }

        // Перейти на страницу со списком друзей
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get("https://vk.com/friends");

        // Получение данных друзей
        WebElement friends = driver.findElement(By.xpath("//a[@href=\"/friends?section=all\"]"));
        WebElement friendsCntr = friends.findElement(By.className("ui_tab_count"));
        int cntr = Integer.parseInt(friendsCntr.getText());

        // Проверка наличия друзей
        if (cntr == 0) {
            System.out.println("Не возможно получить ссылки на альбомы: у пользователя нет друзей!");
        } else {
            if (cntr > 5) {
                cntr = 5;
            }

            System.out.println("Ссылки на альбомы " + cntr + " первых друзей:");

            //while (cntr-- != 0) {
                System.out.println(driver.findElement(By.className("ui_zoom_outer ui_zoom_added")).getAttribute("href"));
            //}
        }

    } // конец main()
}