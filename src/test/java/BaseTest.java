import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.io.TempDir;
import org.portfolio.factory.BasePageFactory;
import org.portfolio.services.UserCredentialsFetchService;
import org.portfolio.ui.pages.BasePage;
import org.portfolio.ui.pages.LoginPage;
import org.portfolio.util.BrowserManager;

import java.nio.file.Path;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseTest {

    @TempDir
    protected static Path sharedTempDir;

    protected Playwright playwright;
    protected APIRequestContext request;
    protected Browser browser;
    protected BrowserContext browserContext;
    protected Page page;
    protected LoginPage loginPage;

    protected static UserCredentialsFetchService userCredentialsFetchService;


    protected <T extends BasePage> T createInstance(Class<T> basePage) {
        return BasePageFactory.createInstance(page, basePage);
    }

    @BeforeAll
    public void createPlaywrightAndTestInstances() {
        createPlaywright();
        browser = BrowserManager.getBrowser(playwright);
        userCredentialsFetchService = new UserCredentialsFetchService();
    }

    @AfterAll
    public void closeBrowserAndPlaywrightSessions() {
        browser.close();
        closePlaywright();
    }

    private void createPlaywright() {
        playwright = Playwright.create();
    }

    protected void createAPIRequestContext(String token) {
        // TODO: no need now
    }

    private void closePlaywright() {
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    protected BrowserContext configureBrowserContextForLoggedInUser(Path credentialsFilePath) {
        return browser.newContext(new Browser.NewContextOptions().setStorageStatePath(credentialsFilePath));
    }

    protected BrowserContext.StorageStateOptions saveStorageState(Path credentialsFilePath) {
        return new BrowserContext.StorageStateOptions().setPath(credentialsFilePath);
    }

}
