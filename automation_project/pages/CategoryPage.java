package automation_project.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CategoryPage extends SitePage {

	List<WebElement> categoryTable;
	String[] categoryProductIds;
	String[] categoryProductNames;

	public CategoryPage(WebDriver driver) {
		super(driver);
		
		this.categoryTable = this.driver.findElements(By.cssSelector("tr"));
		this.categoryProductIds = new String[categoryTable.size() - 1];
		this.categoryProductNames = new String[categoryTable.size() - 1];
	}
	
	public void getCategoryCatalog()
	{
		List<WebElement> cells;

		for (int i = 1; i < this.categoryTable.size(); i++ ) {
			WebElement temp = this.categoryTable.get(i);
			cells = temp.findElements(By.tagName("td"));
			this.categoryProductIds[i - 1] = cells.get(0).getText();
			this.categoryProductNames[i - 1] = cells.get(1).getText();
		}
	}
	
	public void clickProductById(int productIdIndex)
	{
		this.driver.findElement(By.linkText(this.categoryProductIds[productIdIndex])).click();
	}
	
	public int getNumOfProductsInCategory()
	{
		return this.categoryProductNames.length;
	}

}
