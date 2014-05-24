package webfx.browser;

import org.joda.time.DateTime;
import java.net.URL;

/**
 * @author robosova
 * PageCacher interface
 */
public interface PageCacher {
    void put(final URL url);
    void update(final URL url);
    void delete(final URL url);
    BrowserTab get(final URL url);
    DateTime pageLastUpdate(final URL url);
}
