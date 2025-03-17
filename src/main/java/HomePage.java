import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HomePage {

    private WebDriver driver;

    @FindBy(xpath = "//input[@placeholder='Where are you going?']")
    private WebElement locationField;
    @FindBy(id = "date-display-field-start")
    private WebElement checkInField;
    @FindBy(id = "date-display-field-end")
    private WebElement checkOutField;

    @FindBy(xpath = "//button[contains(@class, 'a83ed08757') and contains(., 'Search')]")
    private WebElement searchButton;

   @FindBy(xpath = "//button[@type='button' and @data-testid='date-display-field-start' and contains(@class,'d47738b911') and contains(@class,'e246f833f7') and contains(@class,'fe211c0731')]" )
    private WebElement checkInValue;
   @FindBy(xpath = "//button[@data-testid='date-display-field-start' and @class='d47738b911 e246f833f7 fe211c0731']")
   private WebElement checkInDateElement;

    @FindBy(xpath = "//span[@tabindex='-1' and @class='b21c1c6c83' and @data-date='2023-09-24' and @aria-checked='false' and @role='checkbox' and @aria-label='24 September 2023']")
    private WebElement chekoutValue;

    @FindBy(linkText = "See availability")
    private WebElement seeAvailabilityButton;

    @FindBy(xpath = "//button[@aria-label='Dismiss sign-in info.' and @class='fc63351294 a822bdf511 e3c025e003 fa565176a8 f7db01295e c334e6f658 ae1678b153']" )
    private WebElement closeButton;

    @FindBy(xpath = "//button[@class='a83ed08757 c21c56c305 f38b6daa18 d691166b09 f671049264 deab83296e f4552b6561 dc72a8413c f073249358']" )
    private WebElement svgButton;

    @FindBy(xpath = "//span[@tabindex='-1' and @class='cf06f772fa' and @data-date='2023-10-01' and @aria-checked='false' and @role='checkbox' and @aria-label='1 October 2023']")
    private WebElement firstDuration;

    @FindBy(xpath ="//span[@tabindex='-1' and @class='cf06f772fa' and @data-date='2023-10-14' and @aria-checked='false' and @role='checkbox' and @aria-label='14 October 2023']")
    private WebElement lastDuration;

    @FindBy(xpath = "//input[@type='radio' and @value='2' and @name='bedPreference_17536301']")
    private WebElement twinBedButton;

    @FindBy(xpath = "//select[@class='hprt-nos-select js-hprt-nos-select' and @name='nr_rooms_17536301_371001036_0_85_0']")
    private WebElement amountButton;

    @FindBy(xpath = "//button[contains(@data-title,'Pharaoh Azur Resort') and contains(.,'I\\'ll reserve')]")
    private WebElement reserveBtn;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void open() {
        System.setProperty("webdriver.chrome.driver", Configuration.getChromeDriverPath());
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.booking.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void dismissNotification(){
        //dismiss the pop up that appears after login
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(50, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-label='Dismiss sign-in info.' and @type='button']"))).click();
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
    }

    public void searchForLocation(String location) throws IOException {

        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(100, TimeUnit.SECONDS);
        String excelFilePath = Configuration.getExcel();
        System.out.println("======"+ excelFilePath);
        String sheetName = "Sheet1";

        FileInputStream fis = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        for(int i=1;i<=rowCount;i++) {
            location = sheet.getRow(i).getCell(0).getStringCellValue();
            System.out.println("====="+sheet.getRow(i).getCell(0).getStringCellValue());
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Where are you going?']"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Where are you going?']"))).sendKeys(location + Keys.ENTER);

            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        }
    }

    public void selectDates(String checkInDate, String checkOutDate) {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(50, TimeUnit.SECONDS);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='a83ed08757 c21c56c305 f38b6daa18 d691166b09 f671049264 deab83296e f4552b6561 dc72a8413c f073249358']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//span[@tabindex='-1' and @class='cf06f772fa' and @data-date='2023-10-01' and @aria-checked='false' and @role='checkbox' and @aria-label='1 October 2023']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@tabindex='-1' and @class='cf06f772fa' and @data-date='2023-10-14' and @aria-checked='false' and @role='checkbox' and @aria-label='14 October 2023']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//button[contains(@class, 'a83ed08757') and contains(., 'Search')]"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText( "See availability"))).click();


        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

        driver.get("https://www.booking.com/hotel/eg/pharaoh-azur-resort.html?aid=304142&label=gen173nr-1DCAEoggI46AdIM1gEaEOIAQGYATG4ARfIAQzYAQPoAQH4AQKIAgGoAgO4Aqrw2KYGwAIB0gIkNjM5ZWI0MTAtNWQ5OC00NWQwLWFlMGItNTVmNmE5NzQxNTVi2AIE4AIB&sid=f9a413967d8002d9911af79c328147b8&all_sr_blocks=17536301_371001036_0_85_0;checkin=2023-10-01;checkout=2023-10-14;dest_id=-290029;dest_type=city;dist=0;group_adults=2;group_children=0;hapos=1;highlighted_blocks=17536301_371001036_0_85_0;hpos=1;matching_block_id=17536301_371001036_0_85_0;no_rooms=1;req_adults=2;req_children=0;room1=A%2CA;sb_price_type=total;sr_order=popularity;sr_pri_blocks=17536301_371001036_0_85_0__93353;srepoch=1691760701;srpvid=8a295f1ca6810119;type=total;ucfs=1&#hotelTmpl");
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

        // this is the radio button value = 2 which is queen bed value
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"hprt-table\"]/tbody/tr[1]/td[1]/div/div[3]/div/label[3]/div/input"))).click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        WebElement dropdown = driver.findElement(By.cssSelector("select[name='nr_rooms_17536301_371001036_0_85_0']"));

        // Create a Select object
        Select select = new Select(dropdown);

        // Select the option with value "1"
        select.selectByValue("1");
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

        //click I'll reserve button
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.txp-bui-main-pp.bui-button.bui-button--primary.hp_rt_input.px--fw-cta.js-reservation-button"))).click();

        WebElement dateLabel = driver.findElement(By.xpath("//time[contains(@aria-describedby, 'bp-checkin-date__label')]"));
        dateLabel.getTagName();
        String dateTextCheckIn = dateLabel.getText();
        System.out.println("======"+dateTextCheckIn);

        Assert.assertEquals(dateTextCheckIn, checkInDate);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement dateElement = driver.findElement(By.xpath("//time[contains(@aria-describedby, 'bp-checkout-date__label')]"));

        // Get the text of the element
        String dateTextCheckout = dateElement.getText();
        System.out.println("======"+dateTextCheckout);

        // Assert that the text matches the expected value
        Assert.assertEquals(dateTextCheckout, checkOutDate);

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        // Locate the h1 element with the class "e1eebb6a1e"
        WebElement header = driver.findElement(By.xpath("//h1[@class='e1eebb6a1e']"));

// Retrieve the text of the element
        String text = header.getText();

// Assert that the text is equal to "Pharaoh Azur Resort"
        Assert.assertEquals(text, "Pharaoh Azur Resort");
    }
    
    public void quitDriver() {
         driver.quit();
    }
}
