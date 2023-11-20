package org.portfolio.util;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Playwright;
import org.portfolio.factory.BrowserFactory;


public final class BrowserManager {

    private BrowserManager() {}

    public static Browser getBrowser(final Playwright playwright) {
        return BrowserFactory.valueOf("chromium".toUpperCase())
                .createInstance(playwright);
    }
}
