package org.portfolio.ui.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.portfolio.ui.pages.LoginPage;

public class PrimaryHeader extends BaseComponent {

    public PrimaryHeader(final Page page) {
        super(page);
    }

    public Locator getShoppingCart() {
        return page.locator("//div[@id='shopping_cart_container']");
    }

    public Locator getAppLogo() {
        return page.locator("//div[@class='app_logo']");
    }

    public PrimaryHeader openMenu() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open Menu")).click();

        return this;
    }

}
