package commons;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageUIs.AbstractPageUI;

public class AbstractPage {
  public void openPageUrl(WebDriver driver, String URL) {
    driver.get(URL);
  }

  public String getCurrenPageURL(WebDriver driver) {
    return driver.getCurrentUrl();
  }

  public String getCurrenPageTitle(WebDriver driver) {
    sleepInSecond(1);
    return driver.getTitle();
  }

  public String getCurrenPageSoure(WebDriver driver) {
    return driver.getPageSource();
  }

  public void backToPage(WebDriver driver) {
    driver.navigate().back();
  }

  public void forwardToPage(WebDriver driver) {
    driver.navigate().forward();
  }

  public void refreshToPage(WebDriver driver) {
    driver.navigate().refresh();
  }

  public WebElement getElement(WebDriver driver, String locator) {
    return driver.findElement(By.xpath(locator));
  }

  public WebElement getElement(WebDriver driver, String locator, String... value) {
    return driver.findElement(By.xpath(getDynamicLocator(locator, value)));
  }

  public List<WebElement> getElements(WebDriver driver, String locator) {
    return driver.findElements(By.xpath(locator));
  }

  public By getByXpath(String locator) {
    return By.xpath(locator);
  }

  public void clickToElement(WebDriver driver, String locator) {
    element = getElement(driver, locator);
    // highlightElement(driver, locator);
    element.click();
    sleepInMiniSecond(200);
  }

  public void clickToElement(WebDriver driver, String locator, String... values) {
    element = getElement(driver, getDynamicLocator(locator, values));
    // highlightElement(driver, locator);
    element.click();
    sleepInMiniSecond(200);
  }

  public void clearTextToElement(WebDriver driver, String locator) {
    element = getElement(driver, locator);
    element.sendKeys(Keys.CONTROL + "a");
    element.sendKeys(Keys.DELETE);
  }

  public void sendkeyToElement(WebDriver driver, String locator, String value) {
    element = getElement(driver, locator);
    element.clear();
    element.sendKeys(value);
  }

  public void sendkeyToElement(WebDriver driver, String locator, String... values) {
    element = getElement(driver, getDynamicLocator(locator, values));
    element.clear();
    element.sendKeys(values);
  }

  public void selectItemInDropDown(WebDriver driver, String locator, String value) {
    element = getElement(driver, locator);
    select = new Select(element);
    select.selectByVisibleText(value);
  }

  public String getFirstSelectTextInDropDown(WebDriver driver, String locator) {
    element = getElement(driver, locator);
    select = new Select(element);
    return select.getFirstSelectedOption().getText();
  }

  public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator,
      String expectedItem) {
    getElement(driver, parentLocator).click();
    sleepInSecond(1);
    explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    List<WebElement> allItems = explicitWait
        .until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childItemLocator)));

    for (WebElement item : allItems) {
      if (item.getText().trim().equals(expectedItem)) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
        sleepInSecond(1);

        item.click();
        sleepInSecond(1);
        break;
      }
    }
  }

  public void sleepInSecond(long timeInSecond) {
    try {
      Thread.sleep(timeInSecond * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void sleepInMiniSecond(long timeInSecond) {
    try {
      Thread.sleep(timeInSecond);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public String getElementText(WebDriver driver, String locator) {
    element = getElement(driver, locator);
    return element.getText();
  }

  public void checkToCheckBox(WebDriver driver, String locator) {
    element = getElement(driver, locator);
    if (!element.isSelected()) {
      element.click();
    }
  }

  public void uncheckToCheckBox(WebDriver driver, String locator) {
    element = getElement(driver, locator);
    if (element.isSelected()) {
      element.click();
    }
  }

  public boolean isElementDisplayed(WebDriver driver, String locator) {
    return getElement(driver, locator).isDisplayed();
  }

  public boolean isElementDisplayed(WebDriver driver, String locator, String... values) {
    return getElement(driver, getDynamicLocator(locator, values)).isDisplayed();
  }

  public boolean isElementEnable(WebDriver driver, String locator) {
    return getElement(driver, locator).isEnabled();
  }

  public boolean isElementSelected(WebDriver driver, String locator) {
    return getElement(driver, locator).isSelected();
  }

  public void doubleClickToElement(WebDriver driver, String locator) {
    action = new Actions(driver);
    action.doubleClick(getElement(driver, locator)).perform();
  }

  public void rightClickToElemennt(WebDriver driver, String locator) {
    action = new Actions(driver);
    action.contextClick(getElement(driver, locator)).perform();
  }

  public void hoverMouseToElement(WebDriver driver, String locator) {
    action = new Actions(driver);
    action.moveToElement(getElement(driver, locator)).perform();
  }

  public void hoverMouseToElement(WebDriver driver, String locator, String... value) {
    action = new Actions(driver);
    action.moveToElement(getElement(driver, getDynamicLocator(locator, value))).perform();
  }

  public void clickAndHoldToElement(WebDriver driver, String locator) {
    action = new Actions(driver);
    action.clickAndHold(getElement(driver, locator)).perform();
  }

  public void dragAndDropElement(WebDriver driver, String sourceLocator, String tagetLocator) {
    action = new Actions(driver);
    action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, tagetLocator)).perform();
  }

  public void waitToElementVisible(WebDriver driver, String locator) {
    explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
  }

  public void waitToElementVisible(WebDriver driver, String locator, String... values) {
    explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(locator, values))));
  }

  public void waitToElementInvisible(WebDriver driver, String locator) {
    explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
  }

  public void waitToElementInvisible(WebDriver driver, String locator, String... values) {
    explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(locator, values))));
  }

  public void waitToElementClickable(WebDriver driver, String locator, String... value) {
    explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, value))));
  }

  public void waitToElementClickable(WebDriver driver, String locator, int... value) {
    explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, value))));
  }

  public String getDynamicLocator(String locator, String... values) {
    locator = String.format(locator, (Object[]) values);
    return locator;
  }

  public String getDynamicLocator(String locator, int... values) {
    locator = String.format(locator, (int[]) values);
    return locator;
  }

  public void openLinkWithPageName(WebDriver driver, String pageName) {
    waitToElementClickable(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
    clickToElement(driver, AbstractPageUI.DYNAMIC_LINK, pageName);
  }

  // *add more here

  private WebDriverWait explicitWait;
  private WebElement element;
  private Select select;
  private Actions action;
}
