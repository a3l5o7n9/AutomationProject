package automation_project;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Automation_Project {

	public static void main(String[] args) throws InterruptedException {
		final String siteUrl = "https://petstore.octoperf.com/actions/Catalog.action";
		// Set the path to ChromeDriver
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver_win32\\chromedriver.exe");

		// Set the path to GeckoDriver (FireFox)
		System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver-v0.29.1-win64\\geckodriver.exe");

		int chosenExplorer = 1;

		Random rnd = new Random();

		// Randomly generate a value that is 1 or 2 to decide which explorer to use: 1 - Chrome, 2 - Firefox
		chosenExplorer = rnd.nextInt(2) + 1;

		// Create the driver object as null - Initialisation will happen later
		WebDriver driver = null;

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
		driver.navigate().to(siteUrl);

		driver.manage().window().maximize();

		// Set the acceptable wait time period for the program to get a response 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		Thread.sleep(1000);

		// Find the link to the "Sign In" page and click it to navigate to it
		WebElement signInLink = driver.findElement(By.linkText("Sign In"));
		signInLink.click();

		//	Find the "UserName" and "Password" fields, clear any values already in them and insert the specified values
		String userName = "AlonPZ";
		String password = "a3l5o7n";
		WebElement userNameInputElement = driver.findElement(By.name("username"));
		WebElement passwordInputElement = driver.findElement(By.name("password"));

		userNameInputElement.clear();
		passwordInputElement.clear();

		Thread.sleep(1000);

		userNameInputElement.sendKeys(userName);

		Thread.sleep(1000);

		passwordInputElement.sendKeys(password);

		Thread.sleep(1000);

		//	Find the "Login" button and click it to perform the sign in operation
		WebElement loginButtonElement = driver.findElement(By.name("signon"));
		loginButtonElement.click();

		Thread.sleep(1000);

		WebElement signOutButtonElement;
		
		// Verify that after signing in, a "Sign Out" button appears
		try {
			signOutButtonElement = driver.findElement(By.linkText("Sign Out"));
			System.out.println("Sign in test passed");
		}
		catch (Exception e) {
			System.out.println("Test failed! Sign out button did not appear after signing in");
			e.printStackTrace();
			driver.close();
			return;
		}

		//	Find the links of all the categories in the 'QuickLinks' bar
		List<WebElement> categoriesLinkElements = driver.findElements(By.cssSelector("#QuickLinks > a"));

		int rndCategory = 0;

		for (int i = 0; i < 2; i ++)
		{
			//	Randomly choose a category for the current animal to be purchased
			int tempRndCategory = rndCategory;

			rndCategory = rnd.nextInt(categoriesLinkElements.size());

			if (i > 0)
			{
				while (tempRndCategory == rndCategory)
				{
					rndCategory = rnd.nextInt(categoriesLinkElements.size());
				}
			}

			// Navigate to said category's catalogue page
			categoriesLinkElements.get(rndCategory).click();

			Thread.sleep(1000);

			// Load the table of the animals in the category into a list of rows
			// and divide them into arrays of Product IDs and Product Names
			List<WebElement> currentCategoryTable = driver.findElements(By.cssSelector("tr"));
			String[] currentCategoryProductIds = new String[currentCategoryTable.size() - 1];
			String[] currentCategoryProductNames = new String[currentCategoryTable.size() - 1];

			getCategoryCatalog(currentCategoryTable, currentCategoryProductIds, currentCategoryProductNames);

			//	Randomly choose a product to be purchased from the current category
			int currentProduct = rnd.nextInt(currentCategoryProductNames.length);

			// Navigate to the chosen product's types page
			// System.out.println("Chose to buy " + currentCategoryProductNames[currentProduct]);
			driver.findElement(By.linkText(currentCategoryProductIds[currentProduct])).click();

			Thread.sleep(1000);

			// Load the table of types for the current product into a list of rows
			// and divide them into arrays of Item IDs, Descriptions and Prices
			List<WebElement> currentItemTypesTable = driver.findElements(By.cssSelector("tr"));
			String[] currentItemTypeIds = new String[currentItemTypesTable.size() - 2];
			String[] currentItemTypeDescriptions = new String[currentItemTypesTable.size() - 2];
			String[] currentItemTypeListPrices = new String[currentItemTypesTable.size() - 2];

			getProductTypes(currentItemTypesTable, currentItemTypeIds, currentItemTypeDescriptions, currentItemTypeListPrices);

			// Randomly choose which type of the item to buy
			int currentItemType = rnd.nextInt(currentItemTypeIds.length);

			//Find the link to add the chosen type to the cart and click it
			currentItemTypesTable.get(currentItemType + 1).findElement(By.linkText("Add to Cart")).click();

			Thread.sleep(1000);

			// Randomly generate the amount of the item to be bought
			int currentRndAmount = rnd.nextInt(10);

			// Find the "Quantity" field for the current item in the cart and change it to the random amount generated earlier
			WebElement currentItemQuantityInputField = driver.findElement(By.name(currentItemTypeIds[currentItemType]));
			currentItemQuantityInputField.clear();
			currentItemQuantityInputField.sendKeys("" + currentRndAmount);

			Thread.sleep(1000);

			// Find the "Update Cart" button and click it to update the price in the cart
			driver.findElement(By.name("updateCartQuantities")).click();

			//	Refresh the object with the links of all the categories in the 'QuickLinks' bar
			categoriesLinkElements = driver.findElements(By.cssSelector("#QuickLinks > a"));

		}

		// Find the 'Sub Total' area and get the current total price from it
		WebElement subTotalElement = driver.findElement(By.cssSelector("td[colspan='7']"));
		String subTotalPriceStr = subTotalElement.getText().substring(subTotalElement.getText().indexOf("$") + 1);
		// double subTotalPrice = Double.parseDouble(subTotalPriceStr.substring(0, subTotalPriceStr.indexOf(",")) + subTotalPriceStr.substring(subTotalPriceStr.indexOf(",") + 1));
		// System.out.println(subTotalPriceStr);

		// Find the "Proceed to Checkout" button and click it
		driver.findElement(By.linkText("Proceed to Checkout")).click();

		Thread.sleep(1000);

		// Find the "Continue" button and click it
		driver.findElement(By.name("newOrder")).click();

		Thread.sleep(1000);

		// Find the "Confirm button and click it
		driver.findElement(By.className("Button")).click();

		Thread.sleep(1000);

		// Find the 'Total' area and get the final total price from it
		WebElement totalElement = driver.findElement(By.cssSelector("th[colspan='5']"));
		
		String totalPriceStr = totalElement.getText().substring(totalElement.getText().indexOf("$") + 1);
		// double totalPrice = Double.parseDouble(totalPriceStr.substring(0, totalPriceStr.indexOf(",")) + totalPriceStr.substring(totalPriceStr.indexOf(",") + 1));


		//		if (totalPrice != subTotalPrice)
		//		{
		//			System.out.println("Test failed! Final total price is not equal to Sub total price!");
		//		}
		if (!totalPriceStr.equals(subTotalPriceStr))
		{
			System.out.println("Test failed! Final total price is not equal to Sub total price!");
		}
		else
		{
			System.out.println("Checkout test passed");
		}

		// Refresh the 'Sign Out' element and click it
		signOutButtonElement = driver.findElement(By.linkText("Sign Out"));
		signOutButtonElement.click();

		// Verify that the text "Sign In" has reappeared
		signInLink = driver.findElement(By.linkText("Sign In"));

		if (signInLink == null)
		{
			System.out.println("Sign Out test failed! The Sign In button has not reappeared");
		}
		else
		{
			System.out.println("Sign Out test passed");
		}

		Thread.sleep(2000);

		driver.close();
	}


	static public void getCategoryCatalog(List<WebElement> categoryTable, String[] categoryProductIds, String[] categoryProductNames)
	{
		List<WebElement> cells;

		for (int i = 1; i < categoryTable.size(); i++ ) {
			WebElement temp = categoryTable.get(i);
			cells = temp.findElements(By.tagName("td"));
			categoryProductIds[i - 1] = cells.get(0).getText();
			categoryProductNames[i - 1] = cells.get(1).getText();
		}
	}

	static public void getProductTypes(List<WebElement > itemTypesTable, String[] itemTypeIds, String[] itemTypeDescriptions, String[] itemTypeListPrices)
	{
		List<WebElement> cells;

		for (int i = 1; i < itemTypesTable.size() - 1; i++)
		{
			WebElement temp = itemTypesTable.get(i);
			cells = temp.findElements(By.tagName("td"));
			itemTypeIds[i - 1] = cells.get(0).getText();
			itemTypeDescriptions[i - 1] = cells.get(2).getText();
			itemTypeListPrices[i - 1] = cells.get(3).getText();
		}
	}

}
