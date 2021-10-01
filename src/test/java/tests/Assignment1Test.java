package tests;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Assignment1Test {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.lcwaikiki.com/tr-TR/TR");
        driver.manage().window().maximize();
    }

    @Test
    public void login() {
        WebElement account = driver.findElement(By.cssSelector(".header-profile-icon"));
        account.click();
        WebElement email = driver.findElement(By.id("LoginEmail"));
        email.click();
        email.sendKeys("kardelenshbz@gmail.com");
        WebElement password = driver.findElement(By.id("Password"));
        password.click();
        password.sendKeys("kardelen123");
        WebElement submit = driver.findElement(By.id("loginLink"));
        submit.click();
        WebElement myAccount = driver.findElement(By.cssSelector(".btn-group.header-user.login"));
        boolean myAccountExist = true;
        if (driver.findElements(By.cssSelector(".btn-group.header-user.login")).size() == 0) {
            myAccountExist = false;
        }

        Assert.assertTrue(myAccountExist);
    }

    @Test
    public void search() throws InterruptedException {
        WebElement product = driver.findElement(By.xpath("//div[@class='search-area']//input"));
        product.click();
        TimeUnit.SECONDS.sleep(3);
        WebElement searchProduct = driver.findElement(By.xpath("//div[@class='search-area']//input"));
        searchProduct.sendKeys("pantolon");
        WebElement searchButton = driver.findElement(By.cssSelector(".search-button"));
        searchButton.click();
        //scroll
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("lazy-load-button")));
        WebElement element = driver.findElement(By.className("lazy-load-button"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView();", element);
        element.click();
        List<WebElement> products = driver.findElement(By.cssSelector(".row.c-items")).findElements(By.className("c-item"));
        Random rand = new Random();
        int randomInt = rand.nextInt(products.size());
        products.get(randomInt).click();
        WebElement firstSize = driver.findElements(By.xpath("//div[@id='option-size']//a")).get(0);
        firstSize.click();
        WebElement basket = driver.findElement(By.id("pd_add_to_cart"));
        basket.click();
        WebElement basketDetails = driver.findElement(By.className("header-cart"));
        basketDetails.click();
        String productPrice = driver.findElement(By.className("rd-cart-item-price")).getText();
        String priceInBasket = driver.findElement(By.xpath("//div[@class='price-info-area']//span[@class='pull-right']")).getText();

        Assert.assertEquals(productPrice, priceInBasket);

        WebElement plus = driver.findElement(By.xpath("//div[@class='products-area']//div[@class='row mb-5']//div[@class='col-md-8 col-xs-9 pr-0 text-left']//div[@class='rd-cart-item-quantity-area']//a[@class='oq-up plus']"));
        plus.click();
        TimeUnit.SECONDS.sleep(3);
        String number = driver.findElement(By.xpath("//div[@class='rd-cart-item-quantity-area']//div//input")).getAttribute("value");
        String basketSize = driver.findElement(By.xpath("//div[@class='col-sm-4 col-xs-4 header-cart-section']//span[@id='spanCart']")).getText();
        Assert.assertEquals(number, basketSize);
        WebElement trash = driver.findElement(By.xpath("//div[@class='mobile-square-buttons-container']//i[@class='fa fa-trash-o']"));
        trash.click();
        WebElement delete = driver.findElement(By.xpath("//div[@class='col-xs-12']//a[@class='inverted-modal-button sc-delete ins-init-condition-tracking']"));
        TimeUnit.SECONDS.sleep(3);
        delete.click();
        TimeUnit.SECONDS.sleep(3);
        WebElement emptyBasket = driver.findElement(By.xpath("//div[@class='col-sm-12']//p[@class='cart-empty-title']"));
        boolean emptyBasketExist = true;
        if (driver.findElements(By.xpath("//div[@class='col-sm-12']//p[@class='cart-empty-title']")).size() == 0) {
            emptyBasketExist = false;
        }
        Assert.assertTrue(emptyBasketExist);
    }
    @After
        public void close() {
            driver.close();



    }


}




