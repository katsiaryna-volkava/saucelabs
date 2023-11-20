package org.portfolio.ui.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.Getter;
import org.portfolio.ui.components.PrimaryHeader;
import org.portfolio.ui.components.SecondaryHeader;

import java.util.List;

@Getter
public abstract class BasePage {
    protected Page page;

    protected PrimaryHeader primaryHeader;
    protected SecondaryHeader secondaryHeader;

    public void setAndConfigurePage(final Page page) {
        this.page = page;

        page.setDefaultTimeout(20000);
        page.setViewportSize(1920, 1080);
    }

    public void initComponents() {
        primaryHeader = new PrimaryHeader(page);
        secondaryHeader = new SecondaryHeader(page);
    }
}
