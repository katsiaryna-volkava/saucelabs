package org.portfolio.ui.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SecondaryHeader extends BaseComponent {
    public SecondaryHeader(final Page page) {
        super(page);
    }

    public Locator getPageTitle() {
        return page.locator("//span[@class='title']");
    }
}
