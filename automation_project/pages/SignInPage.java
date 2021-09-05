package automation_project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignInPage extends SitePage {

	WebElement userNameInputElement;
	WebElement passwordInputElement;
	WebElement loginButtonElement;
	
	String userNameInput;
	String passwordInput;
			
	public SignInPage(WebDriver driver, String userName, String password) {
		super(driver);		
		this.userNameInputElement = this.driver.findElement(By.name("username"));
		this.passwordInputElement = this.driver.findElement(By.name("password"));
		this.loginButtonElement = this.driver.findElement(By.name("signon"));
		
		this.userNameInputElement.clear();
		this.passwordInputElement.clear();
	}
	
	public void setUserName(String userName)
	{
		this.userNameInput = userName;
		this.userNameInputElement.sendKeys(this.userNameInput);
	}
	
	public void setPassword(String password)
	{
		this.passwordInput = password;
		this.passwordInputElement.sendKeys(this.passwordInput);
	}
	
	public String getUserNameInput()
	{
		return this.userNameInput;
	}
	
	public String getPasswordInput()
	{
		return this.passwordInput;
	}
	
	public WebElement getLoginButton()
	{
		return this.loginButtonElement;
	}
	
	public void clickLoginButton()
	{
		this.loginButtonElement.click();
	}

}
