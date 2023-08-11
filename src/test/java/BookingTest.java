
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class BookingTest {

    private WebDriver driver;
    @BeforeClass
    public void setUp() {

    }

    @DataProvider(name = "bookingData")
    public Object[][] getBookingData() throws IOException {

        return new Object[][] {
                 {"Hurghada", "Sun, Oct 1, 2023\n" +
                         "From 2:00 PM", "Sat, Oct 14, 2023\n" +
                         "Until 12:00 PM"} // Example data
        };
    }

    @Test(dataProvider = "bookingData")
    public void testBookingFlow(String location, String checkInDate, String checkOutDate) throws IOException {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.dismissNotification();
        homePage.searchForLocation(location);
        homePage.selectDates(checkInDate, checkOutDate);
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }    }
}