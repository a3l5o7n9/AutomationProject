package automation_project.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderConfirmationPage extends SitePage {
	
	@FindBy (className = "Button")
	WebElement confirmButton;

	public OrderConfirmationPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void clickConfirmButton()
	{
		this.confirmButton.click();
	}

}
