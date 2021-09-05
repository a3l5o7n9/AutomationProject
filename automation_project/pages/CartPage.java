package automation_project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends SitePage {
	
	WebElement itemQuantityInputField = null;
	
	@FindBy (name = "updateCartQuantities")
	WebElement updateCartElement;
	
	@FindBy (css = "td[colspan='7']")
	WebElement subTotalElement;
	
	@FindBy (linkText = "Proceed to Checkout")
	WebElement proceedToCheckoutButton;
	
	String currentItemId;

	public CartPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public CartPage(WebDriver driver, String currentItemId) {
		super(driver);
		PageFactory.initElements(driver, this);
		this.currentItemId = currentItemId;
		this.itemQuantityInputField = this.driver.findElement(By.name(this.currentItemId));
	}
	
	public void changeItemQuantity(int quantity)
	{
		this.itemQuantityInputField.clear();
		this.itemQuantityInputField.sendKeys("" + quantity);
	}
	
	public void clickUpdateCartButton()
	{
		this.updateCartElement.click();
	}
	
	public String getSubTotalPriceStr()
	{
		return this.subTotalElement.getText().substring(subTotalElement.getText().indexOf("$") + 1);
	}
	
	public void clickProceedToCheckoutButton()
	{
		this.proceedToCheckoutButton.click();
	}

}
