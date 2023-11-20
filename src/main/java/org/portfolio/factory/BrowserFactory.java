package org.portfolio.factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Playwright;

import java.util.Arrays;

public enum BrowserFactory {

    CHROMIUM {
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.chromium().launch(options());
        }
    },
    FIREFOX {
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.firefox().launch(options());
        }
    },
    WEBKIT {
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.webkit().launch(options());
        }
    };

    public LaunchOptions options() {
        return new LaunchOptions()
                .setArgs(Arrays.asList("--start-fullscreen"))
                .setHeadless(false)
                .setSlowMo(50);
    }

    public abstract Browser createInstance(final Playwright playwright);
}
