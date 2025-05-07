package pageObjects;

import org.openqa.selenium.WebDriver;

import commons.AbstractPage;

//*domain can change
//*screen can change
public class domain_screenPageObject extends AbstractPage {

  WebDriver driver;

  public domain_screenPageObject(WebDriver driver) {
    this.driver = driver;
  }

}
