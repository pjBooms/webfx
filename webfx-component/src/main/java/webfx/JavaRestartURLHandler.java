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
public class JavaRestartURLHandler {

    public static final String JAVA_PROTOCOL = "java://";

    public static String convertToHttp(String location, String protocol) {
        if (location.startsWith(protocol)) {
            return "http://" + location.substring(protocol.length());
        }
        return location;
    }

    public static String convertToHttp(String location) {
        return convertToHttp(location, JAVA_PROTOCOL);
    }

    public static void launch(String location, String basePath) {
        String path;
        if (location.startsWith(JAVA_PROTOCOL)) {
            path = convertToHttp(location);
        } else {
            path = basePath + "/" + location;
        }
        JavaRestartLauncher.fork(path);
    }

    public static void launch(URL url) {
        JavaRestartLauncher.fork(url.toExternalForm());
    }
}
