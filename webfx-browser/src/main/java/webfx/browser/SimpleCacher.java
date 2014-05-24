package webfx.browser;

import org.joda.time.DateTime;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Date: 5/24/14
 * Time: 2:38 PM
 */
public class SimpleCacher implements PageCacher {
    private static final Logger LOGGER = Logger.getLogger(SimpleCacher.class.getName());
    private final Map<String, BrowserTab> cache = new ConcurrentHashMap<>();
    private final Map<String, DateTime> timestamps = new ConcurrentHashMap<>();
    private static final int WAS_MODIFIED_HTTP_RESPONSE = 304;

    @Override
    public void put(final URL url, BrowserTab tabToCache) {
        cache.put(url.toString(), tabToCache);
        timestamps.put(url.toString(), new DateTime());
    }

    @Override
    public void delete(final URL url) {
       delete(url.toString());

    }

    @Override
    public void delete(final String url) {
        if(cache.containsKey(url)) {
            cache.remove(url);
        }
        if(timestamps.containsKey(url)) {
            timestamps.remove(url);
        }
    }

    @Override
    public BrowserTab get(final URL url) {
        return cache.get(url.toString());
    }

    @Override
    public DateTime pageLastUpdate(final URL url) {
        return timestamps.get(url.toString());
    }

    @Override
    public boolean wasCachedAndNotNeedToBeUpdate(final URL url) {
        return cache.containsKey(url.toString()) && !urlWasChanged(url);
    }

    private boolean urlWasChanged(final URL url) {
        boolean needToBeUpdated = false;
        if(null != timestamps.get(url.toString())){
            try {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("If-Modified-Since:", timestamps.get(url.toString()).toString());
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.flush();
                wr.close();
                int responseCode = con.getResponseCode();
                if(responseCode == WAS_MODIFIED_HTTP_RESPONSE) {
                    LOGGER.info("Page need to be updated");
                    needToBeUpdated = true;
                }
            } catch (IOException e) {
                LOGGER.info("Get an exception " + e.getMessage());
            }
        }
        return needToBeUpdated;
    }
}
