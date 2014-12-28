package webfx.browser.urlhandlers;

import webfx.browser.BrowserTab;
import webfx.browser.FXTab;

import java.net.URL;
import java.util.Locale;

/**
 * The parent of WebFX URL handlers that render FXML pages.
 * An implementation may define a classloader for rendering a specific URL.
 *
 * See https://github.com/pjBooms/Java-ReStart/javarestart-webfx for
 * {@code java://} and {wfx://} protocols implementations, for example.
 *
 * @author Nikita Lipsky
 */
public abstract class WebFXURLHandler implements URLHandler {

    /**
     * Specifies which classloader should be used to render this particular URL.
     * The classloader will be used to resolve classes that is referenced by rendered FXML page.
     */
    public abstract ClassLoader getClassLoader(URL url);

    @Override
    public BrowserTab handle(URL url, Locale locale) {
        BrowserTab browserTab = new FXTab(locale);
        browserTab.getNavigationContext().goTo(url);
        return browserTab;
    }
}
