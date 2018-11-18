package ohtu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Random;

public class Tester {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        driver.get("http://localhost:4567");
        
        sleep(2);
        
        WebElement element = driver.findElement(By.linkText("login"));
        element.click();

        sleep(2);

        element = driver.findElement(By.name("username"));
        element.sendKeys("pekka");
        element = driver.findElement(By.name("password"));
        element.sendKeys("wrongPassword");
        element = driver.findElement(By.name("login"));
        
        sleep(2);
        element.submit();
        
        //Olematon käyttäjä
        element = driver.findElement(By.name("username"));
        element.sendKeys("Unknown");
        element = driver.findElement(By.name("password"));
        element.sendKeys("akkep1");
        element = driver.findElement(By.name("login"));

        sleep(2);
        element.submit();
                
        element = driver.findElement(By.linkText("back to home"));
        element.click();

        sleep(2);
        
        element = driver.findElement(By.linkText("register new user"));
        element.click();

        sleep(2);
        
        System.out.println(driver.getPageSource());
        Random r = new Random();
        
        element = driver.findElement(By.name("username"));
        element.sendKeys("test"+r.nextInt(100000));
        element = driver.findElement(By.name("password"));
        element.sendKeys("test1234");
        element = driver.findElement(By.name("passwordConfirmation"));
        element.sendKeys("test1234");

        element = driver.findElement(By.name("signup"));

        sleep(2);
        element.submit();
        
        element = driver.findElement(By.linkText("continue to application mainpage"));
        element.click();

        element = driver.findElement(By.linkText("logout"));
        element.click();

        System.out.println(driver.getPageSource());
        
        driver.quit();
    }
    
    private static void sleep(int n){
        try{
            Thread.sleep(n*1000);
        } catch(Exception e){}
    }
}
