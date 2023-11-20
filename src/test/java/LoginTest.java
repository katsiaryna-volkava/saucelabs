import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.portfolio.enums.UserData;
import org.portfolio.enums.UserRole;
import org.portfolio.ui.pages.LoginPage;

import java.util.stream.Stream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.portfolio.enums.UserRole.*;

public class LoginTest extends BaseTest {

    @BeforeEach
    public void configureBrowserContext() {
        browserContext = browser.newContext();
        page = browserContext.newPage();
        loginPage = createInstance(LoginPage.class);
    }

    @AfterEach
    public void closeBrowserContextSession() {
        browserContext.close();
    }

    @ParameterizedTest
    @EnumSource(value = UserRole.class, names = {"STANDARD_USER", "PROBLEM_USER", "ERROR_USER", "VISUAL_USER"})
    public void shouldLoginSuccessfully_whenProvidedCorrectCredentials(UserRole userRole) {
        var products = loginPage.open().loginAs(userRole);

        assertThat(products.getPrimaryHeader().getAppLogo()).hasText("Swag Labs");
        assertThat(products.getSecondaryHeader().getPageTitle()).hasText("Products");
    }

    @ParameterizedTest
    @MethodSource("provideDataForInvalidUsers")
    public void shouldNotLogin_whenIncorrectCredentials(UserData userData, String expectedErrorMessage) {
        var products = loginPage.open().loginAs(userData.getLogin(), userData.getPassword());

        assertThat(products.getError()).hasText(expectedErrorMessage);
        assertThat(products.getLoginBox()).isVisible();
    }

    private static Stream<Arguments> provideDataForInvalidUsers() {
        return Stream.of(
                Arguments.of(
                        prepareLoginDataForUser(NON_EXISTING_USER),
                        "Epic sadface: Username and password do not match any user in this service"
                ),
                Arguments.of(
                        prepareLoginDataForUserWithInvalidPassword(STANDARD_USER),
                        "Epic sadface: Username and password do not match any user in this service"
                )
        );
    }

    @ParameterizedTest
    @EnumSource(value = UserRole.class, names = {"LOCKED_OUT_USER"})
    public void shouldNotLogin_whenLockedOut(UserRole userRole) {
        var products = loginPage.open().loginAs(userRole);

        assertThat(products.getError()).hasText("Epic sadface: Sorry, this user has been locked out.");
        assertThat(products.getLoginBox()).isVisible();
    }

    @SneakyThrows
    private static UserData prepareLoginDataForUser(UserRole userRole) {
        return UserData.builder()
                .login(userCredentialsFetchService.fetchLoginData(userRole).getLogin())
                .password(userCredentialsFetchService.fetchLoginData(userRole).getPassword())
                .build();
    }

    @SneakyThrows
    private static UserData prepareLoginDataForUserWithInvalidPassword(UserRole userRole) {
        return UserData.builder()
                .login(userCredentialsFetchService.fetchLoginData(userRole).getLogin())
                .password("invalid_password")
                .build();
    }
}
