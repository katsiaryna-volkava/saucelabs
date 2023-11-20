package org.portfolio.util;

import com.microsoft.playwright.Page;

public class FieldsActionsUtils {

    private FieldsActionsUtils() {
    }

    public static void fillInDropdownByPlaceholder(Page page, String placeholderText, String selectOption) {
        String locator = String.format(
                "//div[contains(@class, 'dropdown__control') and " +
                        ".//text()[contains(., '%s')]]//input",
                placeholderText
        );
        page.locator(locator).click();
        page.getByText(selectOption, new Page.GetByTextOptions().setExact(true)).click();
    }

    public static void fillInDropdownByPlaceholder(
            Page page,
            String sectionName,
            String placeholderText,
            String selectOption
    ) {
        var sectionLocator = String.format("//h4[text() = '%s']/ancestor::fieldset", sectionName);
        var dropdownLocator = String.format(
                "//div[contains(@class, 'dropdown__control') and " +
                        ".//text()[contains(., '%s')]]//input",
                placeholderText
        );

        String fullLocator = sectionLocator + dropdownLocator;

        page.locator(fullLocator).fill(selectOption);
        page.locator(String.format(
                "//*[contains(@id, 'react-select') and .//*[text() = '%s']]",
                selectOption
        )).click();
    }
}
