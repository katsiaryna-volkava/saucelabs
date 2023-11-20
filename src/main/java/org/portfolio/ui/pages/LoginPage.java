package org.portfolio.ui.pages;

import com.microsoft.playwright.Locator;
import lombok.SneakyThrows;
import org.portfolio.enums.UserRole;
import org.portfolio.services.UserCredentialsFetchService;

public class LoginPage extends BasePage {

    protected UserCredentialsFetchService userCredentialsFetchService = new UserCredentialsFetchService();

    private static final String BASE_URL = "http://saucedemo.com";

    public LoginPage open() {
        page.navigate(BASE_URL);

        return this;
    }

    @SneakyThrows
    public LoginPage loginAs(final UserRole userRole) {
        var userCredentials = userCredentialsFetchService.fetchLoginData(userRole);
        page.locator("[data-test=\"username\"]").fill(userCredentials.getLogin());
        page.locator("[data-test=\"password\"]").fill(userCredentials.getPassword());
        page.locator("[data-test=\"login-button\"]").click();

        return this;
    }

    public LoginPage loginAs(String login, String password) {
        page.locator("[data-test=\"username\"]").fill(login);
        page.locator("[data-test=\"password\"]").fill(password);
        page.locator("[data-test=\"login-button\"]").click();

        return this;
    }

    public Locator getLoginBox() {
        return page.locator("//div[@class='login-box']");
    }

    public Locator getError() {
        return page.locator("[data-test=\"error\"]");
    }

}

