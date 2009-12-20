/*
 * (c) 2008-2009 RANDI2 Core Development Team
 *
 * This file is part of RANDI2.
 *
 * RANDI2 is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * RANDI2 is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * RANDI2. If not, see <http://www.gnu.org/licenses/>.
 */
package de.randi2.utility;

/**
 * An instance of this class, boxes a checked exception. This is manly used,
 * when an operation throws a checked exception which should not be thrown.
 *
 * @author Johannes Th&ouml;nes <jt@randi2.de>
 */
public class BoxedException extends RuntimeException {

    /**
     * Private constructor
     * @param e The exception to be boxed.
     */
    private BoxedException(Exception e) {
        super(e);
    }

    /**
     * Throws a boxed exception.
     * @param e The exception to be boxed.
     */
    public static void throwBoxed(Exception e) {
        throw new BoxedException(e);
    }
}
