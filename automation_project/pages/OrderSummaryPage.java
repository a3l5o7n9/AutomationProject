package automation_project.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderSummaryPage extends SitePage {

	@FindBy (css = "th[colspan='5']")
	WebElement totalElement;
	
	public OrderSummaryPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public String getTotalPriceStr()
	{
		return this.totalElement.getText().substring(this.totalElement.getText().indexOf("$") + 1);
	}

}
