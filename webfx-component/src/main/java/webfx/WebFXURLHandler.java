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

/**
 * @author Nikita Lipsky
 */
public class WebFXURLHandler {

    public static final String WEB_FX_PROTOCOL = "wfx://";

    public static String convertToHttp(final String location) {
        return JavaRestartURLHandler.convertToHttp(location, WEB_FX_PROTOCOL);
    }
}
