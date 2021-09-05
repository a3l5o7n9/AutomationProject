package automation_project.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductPage extends SitePage {

	List<WebElement> itemTypesTable;
	String[] itemTypeIds;
	String[] itemTypeDescriptions;
	String[] itemTypeListPrices;
	
	public ProductPage(WebDriver driver) {
		super(driver);
		
		this.itemTypesTable = this.driver.findElements(By.cssSelector("tr"));
		this.itemTypeIds = new String[this.itemTypesTable.size() - 2];
		this.itemTypeDescriptions = new String[this.itemTypesTable.size() - 2];
		this.itemTypeListPrices = new String[this.itemTypesTable.size() - 2];
	}
	
	public void getProductTypes()
	{
		List<WebElement> cells;

		for (int i = 1; i < this.itemTypesTable.size() - 1; i++)
		{
			WebElement temp = this.itemTypesTable.get(i);
			cells = temp.findElements(By.tagName("td"));
			this.itemTypeIds[i - 1] = cells.get(0).getText();
			this.itemTypeDescriptions[i - 1] = cells.get(2).getText();
			this.itemTypeListPrices[i - 1] = cells.get(3).getText();
		}
	}
	
	public int getNumOfTypes()
	{
		return this.itemTypeIds.length;
	}
	
	public void clickItemTypeById(int itemIdIndex)
	{
		this.itemTypesTable.get(itemIdIndex + 1).findElement(By.linkText("Add to Cart")).click();
	}
	
	public String getTypeIdByIndex(int itemIdIndex)
	{
		return this.itemTypeIds[itemIdIndex];
	}

}
