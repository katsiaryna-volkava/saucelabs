package org.portfolio.factory;

import com.microsoft.playwright.Page;
import org.portfolio.ui.pages.BasePage;

/**
 * @author tahanima
 */
public final class BasePageFactory {

    private BasePageFactory() {
    }

    public static <T extends BasePage> T createInstance(final Page page, final Class<T> clazz) {
        try {
            BasePage instance = clazz.getDeclaredConstructor().newInstance();

            instance.setAndConfigurePage(page);
            instance.initComponents();

            return clazz.cast(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new NullPointerException("Page class instantiation failed.");
    }
}
