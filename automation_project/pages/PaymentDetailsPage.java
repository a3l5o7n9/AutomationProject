package automation_project.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaymentDetailsPage extends SitePage {
	
	@FindBy (name = "newOrder")
	WebElement continueButton;

	public PaymentDetailsPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void clickContinueButton()
	{
		this.continueButton.click();
	}

}
