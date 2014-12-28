package webfx.browser.urlhandlers;

import webfx.browser.BrowserTab;

import java.net.URL;
import java.util.Locale;

/**
 * WebFX URL handlers abstract interface.
 * It is assumed that the browser can handle several protocols
 * and there should be a mechanism to extend supported protocols.
 *
 * @see webfx.browser.urlhandlers.URLHandlersRegistry
 *
 * @author Nikita Lipsky
 */
public interface URLHandler {

    /**
     * @return the list of supported protocols by this handler.
     */
    String[] getProtocols();

    /**
     * Handles specified URL.
     *
     * @param url specified URL to handle
     * @param locale preferred locale that should be used to render the content.
     * @return browser tab of the rendered content of the specified url or
     *         {@code null}, if no browser tab is required to show the content.
     */
    BrowserTab handle(URL url, Locale locale);

}
