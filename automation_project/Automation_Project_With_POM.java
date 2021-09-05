package automation_project;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import automation_project.pages.SitePage;
import automation_project.pages.SignInPage;
import automation_project.pages.CartPage;
import automation_project.pages.CategoryPage;
import automation_project.pages.OrderConfirmationPage;
import automation_project.pages.OrderSummaryPage;
import automation_project.pages.PaymentDetailsPage;
import automation_project.pages.ProductPage;

public class Automation_Project_With_POM {

	public static void main(String[] args) throws InterruptedException {
		final String siteUrl = "https://petstore.octoperf.com/actions/Catalog.action";

		// Create the driver object as null - Initialisation will happen later
		WebDriver driver = null;
		driver = initWebDriver(driver, siteUrl);

		Thread.sleep(1000);

		// Navigate to the "Sign In" page
		SitePage sitePage = new SitePage(driver);
		sitePage.clickSignInElement();

		String userName = "AlonPZ", password = "a3l5o7n9";

		performSignIn(driver, userName, password);

		Thread.sleep(1000);

		// CartPage inherits SitePage
		CartPage cartPage = new CartPage(driver);

		if(!verifySignIn(driver, cartPage))
		{
			System.out.println("User must be signed in to continue testing. Terminating...");
			driver.close();
			return;
		}

		int rndCategory = 0;

		for (int i = 0; i < 2; i ++)
		{
			cartPage = new CartPage(driver);
			addRndItemToCart(driver, cartPage, rndCategory, i);
		}

		cartPage = new CartPage(driver);
		String subTotalPriceStr = cartPage.getSubTotalPriceStr();
		cartPage.clickProceedToCheckoutButton();

		Thread.sleep(1000);

		PaymentDetailsPage paymentDetailsPage = new PaymentDetailsPage(driver);
		paymentDetailsPage.clickContinueButton();

		Thread.sleep(1000);

		OrderConfirmationPage orderConfirmationPage = new OrderConfirmationPage(driver);
		orderConfirmationPage.clickConfirmButton();

		Thread.sleep(1000);

		OrderSummaryPage orderSummaryPage = new OrderSummaryPage(driver);
		String totalPriceStr = orderSummaryPage.getTotalPriceStr();

		verifySubTotalEqualsTotal(subTotalPriceStr, totalPriceStr);
		
		orderSummaryPage.clickSignOutElement();

		sitePage = new SitePage(driver);
		
		// Verify that the text "Sign In" has reappeared
		verifySignOut(driver, sitePage);

		Thread.sleep(2000);

		driver.close();
	}

	static WebDriver initWebDriver(WebDriver driver, String url)
	{
		// Set the path to ChromeDriver
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver_win32\\chromedriver.exe");

		// Set the path to GeckoDriver (FireFox)
		System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver-v0.29.1-win64\\geckodriver.exe");

		int chosenExplorer = 1;

		Random rnd = new Random();

		// Randomly generate a value that is 1 or 2 to decide which explorer to use: 1 - Chrome, 2 - Firefox
		chosenExplorer = rnd.nextInt(2) + 1;

		if (chosenExplorer == 1)
		{
			System.out.println("Using Chrome");
			driver = new ChromeDriver();
		}
		else  //(chosenExplorer == 2)
		{
			System.out.println("Using Firefox");
			driver = new FirefoxDriver();
		}

		//	Open the explorer to the target WebSite
		driver.navigate().to(url);

		driver.manage().window().maximize();

		// Set the acceptable wait time period for the program to get a response 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		return driver;
	}

	static void performSignIn(WebDriver driver, String userName, String password) throws InterruptedException
	{
		SignInPage signInPage = new SignInPage(driver, userName, password);

		Thread.sleep(1000);

		signInPage.setUserName(userName);

		Thread.sleep(1000);

		signInPage.setPassword(password);

		Thread.sleep(1000);

		//	Click the "Login" button to perform the sign in operation
		signInPage.clickLoginButton();

	}

	static Boolean verifySignIn(WebDriver driver, SitePage sitePage)
	{
		// Verify that after signing in, a "Sign Out" button appears
		if (sitePage.getSignOutElement() == null)
		{
			System.out.println("Test failed! Sign out button did not appear after signing in");
			return false;
		}

		System.out.println("Sign in test passed");
		return true;
	}

	static void addRndItemToCart(WebDriver driver, CartPage cartPage, int rndCategory, int iteration) throws InterruptedException
	{
		Random rnd = new Random();
		//		Randomly choose a category for the current animal to be purchased
		int tempRndCategory = rndCategory;

		rndCategory = rnd.nextInt(cartPage.getCategoryLinks().size());

		if (iteration > 0)
		{
			while (tempRndCategory == rndCategory)
			{
				rndCategory = rnd.nextInt(cartPage.getCategoryLinks().size());
			}
		}
		

		// Navigate to said category's catalogue page
		cartPage.getCategoryLinks();
		cartPage.clickCategoryLinkById(rndCategory);

		CategoryPage categoryPage = new CategoryPage(driver);
		categoryPage.getCategoryCatalog();

		Thread.sleep(1000);

		//	Randomly choose a product to be purchased from the current category
		int currentProduct = rnd.nextInt(categoryPage.getNumOfProductsInCategory());

		// Navigate to the chosen product's types page
		categoryPage.clickProductById(currentProduct);

		Thread.sleep(1000);

		ProductPage productPage = new ProductPage(driver);
		productPage.getProductTypes();

		// Randomly choose which type of the item to buy
		int currentItemType = rnd.nextInt(productPage.getNumOfTypes());

		// Navigate to the chose item type's page
		String currentItemId = productPage.getTypeIdByIndex(currentItemType);
		productPage.clickItemTypeById(currentItemType);

		Thread.sleep(1000);

		// Randomly generate the amount of the item to be bought
		int currentRndAmount = rnd.nextInt(10);

		cartPage = new CartPage(driver, currentItemId);
		cartPage.changeItemQuantity(currentRndAmount);

		Thread.sleep(1000);

		cartPage.clickUpdateCartButton();
		cartPage = new CartPage(driver);
	}

	static void verifySubTotalEqualsTotal(String subTotalPriceStr, String totalPriceStr)
	{
		if (!totalPriceStr.equals(subTotalPriceStr))
		{
			System.out.println("Test failed! Final total price is not equal to Sub total price!");
		}
		else
		{
			System.out.println("Checkout test passed");
		}
	}
	
	static void verifySignOut(WebDriver driver, SitePage sitePage)
	{
		if (sitePage.getSignInElement() == null)
		{
			System.out.println("Sign Out test failed! The Sign In button has not reappeared");
		}
		else
		{
			System.out.println("Sign Out test passed");
		}
	}
}
