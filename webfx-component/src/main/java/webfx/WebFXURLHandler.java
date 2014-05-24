/*
 * Copyright (c) 2012-2013, Nikita Lipsky, Excelsior LLC.
 *
 *  The Java ReStart is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  The Java ReStart is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Java ReStart.  If not, see <http://www.gnu.org/licenses/>.
 *
*/
package webfx;

import javarestart.JavaRestartLauncher;

import java.net.URL;

/**
 * @author Nikita Lipsky
 */
public class WebFXURLHandler {

    public static final String WEB_FX_PROTOCOL = "wfx://";

    public static String convertToHttp(final String location) {
        if (location.startsWith(WEB_FX_PROTOCOL)) {
            final String url = "http://" + location.substring(WEB_FX_PROTOCOL.length());
            return url;
        }
        return location;
    }

    public static void launch(String location, String basePath) {
        final String path;
        if (location.startsWith(WEB_FX_PROTOCOL)) {
            path = convertToHttp(location);
        } else {
            path = basePath + '/' + location;
        }
        JavaRestartLauncher.fork(path);
    }

    public static void launch(final URL url) {
        JavaRestartLauncher.fork(url.toExternalForm());
    }
}
