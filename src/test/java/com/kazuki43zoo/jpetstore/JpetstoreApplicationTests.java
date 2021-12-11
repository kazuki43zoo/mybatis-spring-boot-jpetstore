/*
 *    Copyright 2016-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.kazuki43zoo.jpetstore;

import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.fastSetValue;
import static com.codeborne.selenide.Configuration.headless;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Kazuki Shimizu
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JpetstoreApplicationTests {

  @LocalServerPort
  private int port;

  @RegisterExtension
  static ScreenShooterExtension screenshotEmAll = new ScreenShooterExtension(true).to("target/screenshots");

  @BeforeEach
  void setupSelenide() {
    browser = Browsers.CHROME;
    headless = true;
    timeout = TimeUnit.SECONDS.toMillis(10);
    baseUrl = String.format("http://localhost:%d", port);
    fastSetValue = true;
  }

  @AfterEach
  void logout() {
    SelenideElement element = $(By.linkText("Sign Out"));
    if (element.exists()) {
      element.click();
    }
  }

  @Test
  void testOrder() {

    // Open the home page
    open("/");
    assertThat(title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    $(By.linkText("Enter the Store")).click();
    $(By.id("WelcomeContent")).shouldHave(exactText(""));

    // Move to sign in page & sign
    $(By.linkText("Sign In")).click();
    $(By.name("username")).val("j2ee");
    $(By.name("password")).val("j2ee");
    $(By.id("login")).click();
    $(By.id("WelcomeContent")).$(By.tagName("span")).shouldHave(text("ABC"));

    // Search items
    $(By.name("keywords")).val("fish");
    $(By.id("searchProducts")).click();
    $$(By.cssSelector("#Catalog table tr")).shouldHave(size(3));

    // Select item
    $(By.linkText("Fresh Water fish from China")).click();
    $(By.cssSelector("#Catalog h2")).shouldHave(text("Goldfish"));

    // Add a item to the cart
    $(By.linkText("Add to Cart")).click();
    $(By.cssSelector("#Catalog h2")).shouldHave(text("Shopping Cart"));

    // Checkout cart items
    $(By.linkText("Proceed to Checkout")).click();
    assertThat(title()).isEqualTo("JPetStore Demo");

    // Input card information & Confirm order information
    $(By.name("creditCard")).val("9999999999");
    $(By.name("expiryDate")).val("04/2020");
    $(By.name("continue")).click();
    $(By.id("confirmMessage")).shouldHave(text("Please confirm the information below and then press submit..."));

    // Submit order
    $(By.id("order")).click();
    $(By.cssSelector(".messages li")).shouldHave(text("Thank you, your order has been submitted."));
    String orderId = $(By.id("orderId")).text();

    // Show profile page
    $(By.linkText("My Account")).click();
    $(By.cssSelector("#Catalog h3")).shouldHave(text("User Information"));

    // Show orders
    $(By.linkText("My Orders")).click();
    $(By.cssSelector("#Content h2")).shouldHave(text("My Orders"));

    // Show order detail
    $(By.linkText(orderId)).click();
    assertThat($(By.id("orderId")).text()).isEqualTo(orderId);

    // Sign out
    $(By.linkText("Sign Out")).click();
    $(By.id("WelcomeContent")).shouldHave(exactText(""));

  }

  @Test
  void testUpdateProfile() {
    // Open the home page
    open("/");
    assertThat(title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    $(By.linkText("Enter the Store")).click();
    $(By.id("WelcomeContent")).shouldHave(exactText(""));

    // Move to sign in page & sign
    $(By.linkText("Sign In")).click();
    $(By.name("username")).val("j2ee");
    $(By.name("password")).val("j2ee");
    $(By.id("login")).click();
    $(By.id("WelcomeContent")).$(By.tagName("span")).shouldHave(text("ABC"));

    // Show profile page
    $(By.linkText("My Account")).click();
    $(By.cssSelector("#Catalog h3")).shouldHave(text("User Information"));
    $$(By.cssSelector("#Catalog table td")).get(1).shouldHave(text("j2ee"));

    // Edit account
    $(By.id("save")).click();
    $(By.cssSelector("#Catalog h3")).shouldHave(text("User Information"));
    $$(By.cssSelector("#Catalog table td")).get(1).shouldHave(text("j2ee"));
  }

  @Test
  void testRegistrationUser() {
    // Open the home page
    open("/");
    assertThat(title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    $(By.linkText("Enter the Store")).click();
    $(By.id("WelcomeContent")).shouldHave(exactText(""));

    // Move to sign in page & sign
    $(By.linkText("Sign In")).click();
    $(By.cssSelector("#Catalog p")).shouldHave(text("Please enter your username and password."));

    // Move to use registration page
    $(By.linkText("Register Now!")).click();
    $(By.cssSelector("#Catalog h3")).shouldHave(text("User Information"));

    // Create a new user
    String userId = String.valueOf(System.currentTimeMillis());
    $(By.name("username")).val(userId);
    $(By.name("password")).val("password");
    $(By.name("repeatedPassword")).val("password");
    $(By.name("firstName")).val("Jon");
    $(By.name("lastName")).val("MyBatis");
    $(By.name("email")).val("jon.mybatis@test.com");
    $(By.name("phone")).val("09012345678");
    $(By.name("address1")).val("Address1");
    $(By.name("address2")).val("Address2");
    $(By.name("city")).val("Minato-Ku");
    $(By.name("state")).val("Tokyo");
    $(By.name("zip")).val("0001234");
    $(By.name("country")).val("Japan");
    $(By.name("languagePreference")).selectOption("Japanese");
    $(By.name("favouriteCategoryId")).selectOption("CATS");
    $(By.name("listOption")).setSelected(true);
    $(By.name("bannerOption")).setSelected(true);
    $(By.id("save")).click();
    $(By.cssSelector(".messages li")).shouldHave(text("Your account has been created. Please try login !!"));

    // Move to sign in page & sign
    $(By.name("username")).val(userId);
    $(By.name("password")).val("password");
    $(By.id("login")).click();
    $(By.id("WelcomeContent")).$(By.tagName("span")).shouldHave(text("Jon"));

  }

  @Test
  void testSelectItems() {
    // Open the home page
    open("/");
    assertThat(title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    $(By.linkText("Enter the Store")).click();
    $(By.id("WelcomeContent")).shouldHave(exactText(""));

    // Move to category
    $(By.cssSelector("#SidebarContent a")).click();
    $(By.cssSelector("#Catalog h2")).shouldHave(text("Fish"));

    // Move to items
    $(By.linkText("FI-SW-01")).click();
    $(By.cssSelector("#Catalog h2")).shouldHave(text("Angelfish"));

    // Move to item detail
    $(By.linkText("EST-1")).click();
    $$(By.cssSelector("#Catalog table tr td")).get(2).shouldHave(text("Large Angelfish"));

    // Back to items
    $(By.linkText("Return to FI-SW-01")).click();
    $(By.cssSelector("#Catalog h2")).shouldHave(text("Angelfish"));

    // Back to category
    $(By.linkText("Return to FISH")).click();
    $(By.cssSelector("#Catalog h2")).shouldHave(text("Fish"));

    // Back to the top page
    $(By.linkText("Return to Main Menu")).click();
    $(By.id("WelcomeContent")).shouldHave(exactText(""));

  }

  @Test
  void testViewCart() {

    // Open the home page
    open("/");
    assertThat(title()).isEqualTo("JPetStore Demo");

    // Move to the top page
    $(By.linkText("Enter the Store")).click();
    $(By.id("WelcomeContent")).shouldHave(exactText(""));

    // Move to cart
    $(By.name("img_cart")).click();
    $(By.cssSelector("#Catalog h2")).shouldHave(text("Shopping Cart"));

  }

  @Test
  void testViewHelp() {

    // Open the home page
    open("/");
    assertThat(title()).isEqualTo("JPetStore Demo");

    String mainWindow = getWebDriver().getWindowHandle();

    // Move to the top page
    $(By.linkText("Enter the Store")).click();
    $(By.id("WelcomeContent")).shouldHave(exactText(""));

    // Move to help
    $(By.linkText("?")).click();

    Set<String> windows = new HashSet<>(getWebDriver().getWindowHandles());
    windows.remove(mainWindow);
    switchTo().window(windows.iterator().next());

    $(By.cssSelector("#Content h1")).shouldHave(text("JPetStore Demo"));
  }
}
