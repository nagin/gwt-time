/*
 * Copyright (c) 2007, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.time.calendar.field;

import java.io.Serializable;

import javax.time.calendar.Calendrical;
import javax.time.calendar.CalendricalState;
import javax.time.calendar.TimeFieldRule;

/**
 * A time field representing a minute of day.
 * <p>
 * MinuteOfDay is an immutable time field that can only store a minute of day.
 * It is a type-safe way of representing a minute of day in an application.
 * <p>
 * Static factory methods allow you to construct instances.
 * The minute of day may be queried using getMinuteOfDay().
 * <p>
 * MinuteOfDay is thread-safe and immutable.
 *
 * @author Stephen Colebourne
 */
public final class MinuteOfDay implements Calendrical, Comparable<MinuteOfDay>, Serializable {

    /**
     * The rule implementation that defines how the minute of day field operates.
     */
    public static final TimeFieldRule RULE = new Rule();
    /**
     * A serialization identifier for this class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The minute of day being represented.
     */
    private final int minuteOfDay;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of <code>MinuteOfDay</code>.
     *
     * @param minuteOfDay  the minute of day to represent
     * @return the created MinuteOfDay
     */
    public static MinuteOfDay minuteOfDay(int minuteOfDay) {
        return new MinuteOfDay(minuteOfDay);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructs an instance with the specified minute of day.
     *
     * @param minuteOfDay  the minute of day to represent
     */
    private MinuteOfDay(int minuteOfDay) {
        this.minuteOfDay = minuteOfDay;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the minute of day value.
     *
     * @return the minute of day
     */
    public int getMinuteOfDay() {
        return minuteOfDay;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the calendrical state which provides internal access to this
     * MinuteOfDay instance.
     *
     * @return the calendar state for this instance, never null
     */
    @Override
    public CalendricalState getCalendricalState() {
        return null;  // TODO
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this minute of day instance to another.
     *
     * @param otherMinuteOfDay  the other minute of day instance, not null
     * @return the comparator value, negative if less, postive if greater
     * @throws NullPointerException if otherMinuteOfDay is null
     */
    public int compareTo(MinuteOfDay otherMinuteOfDay) {
        int thisValue = this.minuteOfDay;
        int otherValue = otherMinuteOfDay.minuteOfDay;
        return (thisValue < otherValue ? -1 : (thisValue == otherValue ? 0 : 1));
    }

    /**
     * Is this minute of day instance greater than the specified minute of day.
     *
     * @param otherMinuteOfDay  the other minute of day instance, not null
     * @return true if this minute of day is greater
     * @throws NullPointerException if otherMinuteOfDay is null
     */
    public boolean isGreaterThan(MinuteOfDay otherMinuteOfDay) {
        return compareTo(otherMinuteOfDay) > 0;
    }

    /**
     * Is this minute of day instance less than the specified minute of day.
     *
     * @param otherMinuteOfDay  the other minute of day instance, not null
     * @return true if this minute of day is less
     * @throws NullPointerException if otherMinuteOfDay is null
     */
    public boolean isLessThan(MinuteOfDay otherMinuteOfDay) {
        return compareTo(otherMinuteOfDay) < 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Is this instance equal to that specified, evaluating the minute of day.
     *
     * @param otherMinuteOfDay  the other minute of day instance, null returns false
     * @return true if the minute of day is the same
     */
    @Override
    public boolean equals(Object otherMinuteOfDay) {
        if (this == otherMinuteOfDay) {
            return true;
        }
        if (otherMinuteOfDay instanceof MinuteOfDay) {
            return minuteOfDay == ((MinuteOfDay) otherMinuteOfDay).minuteOfDay;
        }
        return false;
    }

    /**
     * A hashcode for the minute of day object.
     *
     * @return a suitable hashcode
     */
    @Override
    public int hashCode() {
        return minuteOfDay;
    }

    //-----------------------------------------------------------------------
    /**
     * Implementation of the rules for the minute of day field.
     */
    private static class Rule extends TimeFieldRule {

        /** Constructor. */
        protected Rule() {
            super("MinuteOfDay", null, null, 0, 1439);
        }
    }

}
