package automation_project.pages;

import java.util.List;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SitePage {
	WebDriver driver;
	
	@FindBy(id="Header")
	WebElement header;
	
	@FindBy(id="Content")
	WebElement content;
	
	List<WebElement> sideBarLinks;
	
	@FindBy(linkText="Sign In")
	WebElement signInElement = null;
	
	@FindBy(linkText="Sign Out")
	WebElement signOutElement = null;
	
	public SitePage(WebDriver driver)
	{
		this.driver = driver;
		
		PageFactory.initElements(driver, this);
		
		this.sideBarLinks = this.driver.findElements(By.cssSelector("#QuickLinks > a"));
	}
	
	public List<WebElement> getCategoryLinks()
	{
		return this.sideBarLinks;
	}
	
	public WebElement getCategoryLinkById(int categoryId)
	{
		return this.sideBarLinks.get(categoryId);
	}
	
	public void clickCategoryLinkById(int categoryId)
	{
		this.sideBarLinks.get(categoryId).click();
	}
	
	public WebElement getSignInElement()
	{
		return this.signInElement;
	}
	
	public void clickSignInElement()
	{
		this.signInElement.click();
	}
	
	public WebElement getSignOutElement()
	{
		return this.signOutElement;
	}
	
	public void clickSignOutElement()
	{
		this.signOutElement.click();
	}

}
