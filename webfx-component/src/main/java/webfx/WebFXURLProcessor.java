package webfx;

import java.net.URL;

/**
 * SAM interface that tells to WebFXRegion what to do with a specified URL,
 * where URL is specified via {@link NavigationContext#goTo} methods.
 *
 * @author Nikita Lipsky
 */
public interface WebFXURLProcessor {

    public class Result {
        /**
         * if set to {@code true} that WebFXRegionshould load the content of specified URL
         * as FXML page.
         */
        public final boolean loadContent;

        /**
         * Class loader that should be used to load classes referenced by FXML specified by the URL.
         */
        public final ClassLoader classLoader;

        public Result(boolean loadContent, ClassLoader classLoader) {
            this.loadContent = loadContent;
            this.classLoader = classLoader;
        }
    }

    Result process(URL url);

}
