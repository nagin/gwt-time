/*
 * Copyright (c) 2007-present, Stephen Colebourne & Michael Nascimento Santos
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
package org.jresearch.threetenbp.gwt.client.format;

import static java.time.temporal.ChronoField.EPOCH_DAY;
import static java.time.temporal.ChronoField.INSTANT_SECONDS;
import static java.time.temporal.ChronoField.MICRO_OF_SECOND;
import static java.time.temporal.ChronoField.MILLI_OF_SECOND;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.OFFSET_SECONDS;
import static java.time.temporal.ChronoField.SECOND_OF_DAY;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import org.jresearch.threetenbp.gwt.client.AbstractTest;
import org.junit.Test;

/**
 * Test parsing of edge cases.
 */
//@Test
public class TestDateTimeParsing extends AbstractTest {

	private static ZoneId PARIS;
	private static ZoneOffset OFFSET_0230;
	private static DateTimeFormatter LOCALFIELDS;
	private static DateTimeFormatter LOCALFIELDS_ZONEID;
	private static DateTimeFormatter LOCALFIELDS_OFFSETID;
	private static DateTimeFormatter LOCALFIELDS_WITH_PARIS;
	private static DateTimeFormatter LOCALFIELDS_WITH_0230;
	private static DateTimeFormatter INSTANT;
	private static DateTimeFormatter INSTANT_WITH_PARIS;
	private static DateTimeFormatter INSTANT_WITH_0230;
	private static DateTimeFormatter INSTANT_OFFSETID;
	private static DateTimeFormatter INSTANT_OFFSETSECONDS;
	private static DateTimeFormatter INSTANTSECONDS;
	private static DateTimeFormatter INSTANTSECONDS_WITH_PARIS;
	private static DateTimeFormatter INSTANTSECONDS_NOS;
	private static DateTimeFormatter INSTANTSECONDS_NOS_WITH_PARIS;
	private static DateTimeFormatter INSTANTSECONDS_OFFSETSECONDS;
	private static DateTimeFormatter INSTANT_OFFSETSECONDS_ZONE;

	@Override
	public void gwtSetUp() throws Exception {
		super.gwtSetUp();
		PARIS = ZoneId.of("Europe/Paris");
		OFFSET_0230 = ZoneOffset.ofHoursMinutes(2, 30);
		LOCALFIELDS = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();
		LOCALFIELDS_ZONEID = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss ").appendZoneId()
				.toFormatter();
		LOCALFIELDS_OFFSETID = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss ").appendOffsetId()
				.toFormatter();
		LOCALFIELDS_WITH_PARIS = LOCALFIELDS.withZone(PARIS);
		LOCALFIELDS_WITH_0230 = LOCALFIELDS.withZone(OFFSET_0230);
		INSTANT = new DateTimeFormatterBuilder().appendInstant().toFormatter();
		INSTANT_WITH_PARIS = INSTANT.withZone(PARIS);
		INSTANT_WITH_0230 = INSTANT.withZone(OFFSET_0230);
		INSTANT_OFFSETID = new DateTimeFormatterBuilder().appendInstant().appendLiteral(' ').appendOffsetId()
				.toFormatter();
		INSTANT_OFFSETSECONDS = new DateTimeFormatterBuilder().appendInstant().appendLiteral(' ')
				.appendValue(OFFSET_SECONDS).toFormatter();
		INSTANTSECONDS = new DateTimeFormatterBuilder().appendValue(INSTANT_SECONDS).toFormatter();
		INSTANTSECONDS_WITH_PARIS = INSTANTSECONDS.withZone(PARIS);
		INSTANTSECONDS_NOS = new DateTimeFormatterBuilder().appendValue(INSTANT_SECONDS).appendLiteral('.')
				.appendValue(NANO_OF_SECOND).toFormatter();
		INSTANTSECONDS_NOS_WITH_PARIS = INSTANTSECONDS_NOS.withZone(PARIS);
		INSTANTSECONDS_OFFSETSECONDS = new DateTimeFormatterBuilder().appendValue(INSTANT_SECONDS).appendLiteral(' ')
				.appendValue(OFFSET_SECONDS).toFormatter();
		INSTANT_OFFSETSECONDS_ZONE = new DateTimeFormatterBuilder().appendInstant().appendLiteral(' ')
				.appendValue(OFFSET_SECONDS).appendLiteral(' ').appendZoneId().toFormatter();
	}

	// @DataProvider(name = "instantZones")
	Object[][] data_instantZones() {
		return new Object[][] {
				{ LOCALFIELDS_ZONEID, "2014-06-30 01:02:03 Europe/Paris",
						ZonedDateTime.of(2014, 6, 30, 1, 2, 3, 0, PARIS) },
				{ LOCALFIELDS_ZONEID, "2014-06-30 01:02:03 +02:30",
						ZonedDateTime.of(2014, 6, 30, 1, 2, 3, 0, OFFSET_0230) },
				{ LOCALFIELDS_OFFSETID, "2014-06-30 01:02:03 +02:30",
						ZonedDateTime.of(2014, 6, 30, 1, 2, 3, 0, OFFSET_0230) },
				{ LOCALFIELDS_WITH_PARIS, "2014-06-30 01:02:03", ZonedDateTime.of(2014, 6, 30, 1, 2, 3, 0, PARIS) },
				{ LOCALFIELDS_WITH_0230, "2014-06-30 01:02:03",
						ZonedDateTime.of(2014, 6, 30, 1, 2, 3, 0, OFFSET_0230) },
				{ INSTANT_WITH_PARIS, "2014-06-30T01:02:03Z",
						ZonedDateTime.of(2014, 6, 30, 1, 2, 3, 0, ZoneOffset.UTC).withZoneSameInstant(PARIS) },
				{ INSTANT_WITH_0230, "2014-06-30T01:02:03Z",
						ZonedDateTime.of(2014, 6, 30, 1, 2, 3, 0, ZoneOffset.UTC).withZoneSameInstant(OFFSET_0230) },
				{ INSTANT_OFFSETID, "2014-06-30T01:02:03Z +02:30",
						ZonedDateTime.of(2014, 6, 30, 1, 2, 3, 0, ZoneOffset.UTC).withZoneSameInstant(OFFSET_0230) },
				{ INSTANT_OFFSETSECONDS, "2014-06-30T01:02:03Z 9000",
						ZonedDateTime.of(2014, 6, 30, 1, 2, 3, 0, ZoneOffset.UTC).withZoneSameInstant(OFFSET_0230) },
				{ INSTANTSECONDS_WITH_PARIS, "86402", Instant.ofEpochSecond(86402).atZone(PARIS) },
				{ INSTANTSECONDS_NOS_WITH_PARIS, "86402.123456789",
						Instant.ofEpochSecond(86402, 123456789).atZone(PARIS) },
				{ INSTANTSECONDS_OFFSETSECONDS, "86402 9000", Instant.ofEpochSecond(86402).atZone(OFFSET_0230) },
				{ INSTANT_OFFSETSECONDS_ZONE, "2016-10-30T00:30:00Z 7200 Europe/Paris",
						ZonedDateTime.ofStrict(LocalDateTime.of(2016, 10, 30, 2, 30), ZoneOffset.ofHours(2), PARIS) },
				{ INSTANT_OFFSETSECONDS_ZONE, "2016-10-30T01:30:00Z 3600 Europe/Paris", ZonedDateTime
						.ofStrict(LocalDateTime.of(2016, 10, 30, 2, 30), ZoneOffset.ofHours(1), PARIS) }, };
	}

	@Test(/* dataProvider = "instantZones" */)
	public void test_parse_instantZones_ZDT() throws Exception {
		Object[][] data = data_instantZones();
		for (int i = 0; i < data.length; i++) {
			Object[] objects = data[i];
			gwtSetUp();
			test_parse_instantZones_ZDT((DateTimeFormatter) objects[0], (String) objects[1],
					(ZonedDateTime) objects[2]);
		}
	}

	public void test_parse_instantZones_ZDT(DateTimeFormatter formatter, String text, ZonedDateTime expected) {
		TemporalAccessor actual = formatter.parse(text);
		assertEquals(ZonedDateTime.from(actual), expected);
	}

	@Test(/* dataProvider = "instantZones" */)
	public void test_parse_instantZones_LDT() throws Exception {
		Object[][] data = data_instantZones();
		for (int i = 0; i < data.length; i++) {
			Object[] objects = data[i];
			gwtSetUp();
			test_parse_instantZones_LDT((DateTimeFormatter) objects[0], (String) objects[1],
					(ZonedDateTime) objects[2]);
		}
	}

	public void test_parse_instantZones_LDT(DateTimeFormatter formatter, String text, ZonedDateTime expected) {
		TemporalAccessor actual = formatter.parse(text);
		assertEquals(LocalDateTime.from(actual), expected.toLocalDateTime());
	}

	@Test(/* dataProvider = "instantZones" */)
	public void test_parse_instantZones_Instant() throws Exception {
		Object[][] data = data_instantZones();
		for (int i = 0; i < data.length; i++) {
			Object[] objects = data[i];
			gwtSetUp();
			test_parse_instantZones_Instant((DateTimeFormatter) objects[0], (String) objects[1],
					(ZonedDateTime) objects[2]);
		}
	}

	public void test_parse_instantZones_Instant(DateTimeFormatter formatter, String text, ZonedDateTime expected) {
		TemporalAccessor actual = formatter.parse(text);
		assertEquals(Instant.from(actual), expected.toInstant());
	}

	@Test(/* dataProvider = "instantZones" */)
	public void test_parse_instantZones_supported() throws Exception {
		Object[][] data = data_instantZones();
		for (int i = 0; i < data.length; i++) {
			Object[] objects = data[i];
			gwtSetUp();
			test_parse_instantZones_supported((DateTimeFormatter) objects[0], (String) objects[1],
					(ZonedDateTime) objects[2]);
		}
	}

	public void test_parse_instantZones_supported(DateTimeFormatter formatter, String text, ZonedDateTime expected) {
		TemporalAccessor actual = formatter.parse(text);
		assertEquals(actual.isSupported(INSTANT_SECONDS), true);
		assertEquals(actual.isSupported(EPOCH_DAY), true);
		assertEquals(actual.isSupported(SECOND_OF_DAY), true);
		assertEquals(actual.isSupported(NANO_OF_SECOND), true);
		assertEquals(actual.isSupported(MICRO_OF_SECOND), true);
		assertEquals(actual.isSupported(MILLI_OF_SECOND), true);
	}

	// -----------------------------------------------------------------------
	// @DataProvider(name = "instantNoZone")
	Object[][] data_instantNoZone() {
		return new Object[][] {
				{ INSTANT, "2014-06-30T01:02:03Z",
						ZonedDateTime.of(2014, 6, 30, 1, 2, 3, 0, ZoneOffset.UTC).toInstant() },
				{ INSTANTSECONDS, "86402", Instant.ofEpochSecond(86402) },
				{ INSTANTSECONDS_NOS, "86402.123456789", Instant.ofEpochSecond(86402, 123456789) }, };
	}

	@Test(/* dataProvider = "instantNoZone", */ expected = DateTimeException.class)
	public void test_parse_instantNoZone_ZDT() throws Exception {
		Object[][] data = data_instantNoZone();
		for (int i = 0; i < data.length; i++) {
			Object[] objects = data[i];
			gwtSetUp();
			test_parse_instantNoZone_ZDT((DateTimeFormatter) objects[0], (String) objects[1], (Instant) objects[2]);
		}
	}

	public void test_parse_instantNoZone_ZDT(DateTimeFormatter formatter, String text, Instant expected) {
		try {
			TemporalAccessor actual = formatter.parse(text);
			ZonedDateTime.from(actual);
			fail("Missing exception");
		} catch (DateTimeException e) {
			// expected
		}
	}

	@Test(/* dataProvider = "instantNoZone", */ expected = DateTimeException.class)
	public void test_parse_instantNoZone_LDT() throws Exception {
		Object[][] data = data_instantNoZone();
		for (int i = 0; i < data.length; i++) {
			Object[] objects = data[i];
			gwtSetUp();
			test_parse_instantNoZone_LDT((DateTimeFormatter) objects[0], (String) objects[1], (Instant) objects[2]);
		}
	}

	public void test_parse_instantNoZone_LDT(DateTimeFormatter formatter, String text, Instant expected) {
		try {
			TemporalAccessor actual = formatter.parse(text);
			LocalDateTime.from(actual);
			fail("Missing exception");
		} catch (DateTimeException e) {
			// expected
		}
	}

	@Test(/* dataProvider = "instantNoZone" */)
	public void test_parse_instantNoZone_Instant() throws Exception {
		Object[][] data = data_instantNoZone();
		for (int i = 0; i < data.length; i++) {
			Object[] objects = data[i];
			gwtSetUp();
			test_parse_instantNoZone_Instant((DateTimeFormatter) objects[0], (String) objects[1], (Instant) objects[2]);
		}
	}

	public void test_parse_instantNoZone_Instant(DateTimeFormatter formatter, String text, Instant expected) {
		TemporalAccessor actual = formatter.parse(text);
		assertEquals(Instant.from(actual), expected);
	}

	@Test(/* dataProvider = "instantNoZone" */)
	public void test_parse_instantNoZone_supported() throws Exception {
		Object[][] data = data_instantNoZone();
		for (int i = 0; i < data.length; i++) {
			Object[] objects = data[i];
			gwtSetUp();
			test_parse_instantNoZone_supported((DateTimeFormatter) objects[0], (String) objects[1],
					(Instant) objects[2]);
		}
	}

	public void test_parse_instantNoZone_supported(DateTimeFormatter formatter, String text, Instant expected) {
		TemporalAccessor actual = formatter.parse(text);
		assertEquals(actual.isSupported(INSTANT_SECONDS), true);
		assertEquals(actual.isSupported(EPOCH_DAY), false);
		assertEquals(actual.isSupported(SECOND_OF_DAY), false);
		assertEquals(actual.isSupported(NANO_OF_SECOND), true);
		assertEquals(actual.isSupported(MICRO_OF_SECOND), true);
		assertEquals(actual.isSupported(MILLI_OF_SECOND), true);
	}

	// -----------------------------------------------------------------------
	@Test
	public void test_parse_fromField_InstantSeconds() {
		DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendValue(INSTANT_SECONDS).toFormatter();
		TemporalAccessor acc = fmt.parse("86402");
		Instant expected = Instant.ofEpochSecond(86402);
		assertEquals(acc.isSupported(INSTANT_SECONDS), true);
		assertEquals(acc.isSupported(NANO_OF_SECOND), true);
		assertEquals(acc.isSupported(MICRO_OF_SECOND), true);
		assertEquals(acc.isSupported(MILLI_OF_SECOND), true);
		assertEquals(acc.getLong(INSTANT_SECONDS), 86402L);
		assertEquals(acc.getLong(NANO_OF_SECOND), 0L);
		assertEquals(acc.getLong(MICRO_OF_SECOND), 0L);
		assertEquals(acc.getLong(MILLI_OF_SECOND), 0L);
		assertEquals(Instant.from(acc), expected);
	}

	@Test
	public void test_parse_fromField_InstantSeconds_NanoOfSecond() {
		DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendValue(INSTANT_SECONDS).appendLiteral('.')
				.appendValue(NANO_OF_SECOND).toFormatter();
		TemporalAccessor acc = fmt.parse("86402.123456789");
		Instant expected = Instant.ofEpochSecond(86402, 123456789);
		assertEquals(acc.isSupported(INSTANT_SECONDS), true);
		assertEquals(acc.isSupported(NANO_OF_SECOND), true);
		assertEquals(acc.isSupported(MICRO_OF_SECOND), true);
		assertEquals(acc.isSupported(MILLI_OF_SECOND), true);
		assertEquals(acc.getLong(INSTANT_SECONDS), 86402L);
		assertEquals(acc.getLong(NANO_OF_SECOND), 123456789L);
		assertEquals(acc.getLong(MICRO_OF_SECOND), 123456L);
		assertEquals(acc.getLong(MILLI_OF_SECOND), 123L);
		assertEquals(Instant.from(acc), expected);
	}

	@Test
	public void test_parse_fromField_SecondOfDay() {
		DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendValue(SECOND_OF_DAY).toFormatter();
		TemporalAccessor acc = fmt.parse("864");
		assertEquals(acc.isSupported(SECOND_OF_DAY), true);
		assertEquals(acc.isSupported(NANO_OF_SECOND), true);
		assertEquals(acc.isSupported(MICRO_OF_SECOND), true);
		assertEquals(acc.isSupported(MILLI_OF_SECOND), true);
		assertEquals(acc.getLong(SECOND_OF_DAY), 864L);
		assertEquals(acc.getLong(NANO_OF_SECOND), 0L);
		assertEquals(acc.getLong(MICRO_OF_SECOND), 0L);
		assertEquals(acc.getLong(MILLI_OF_SECOND), 0L);
	}

	@Test
	public void test_parse_fromField_SecondOfDay_NanoOfSecond() {
		DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendValue(SECOND_OF_DAY).appendLiteral('.')
				.appendValue(NANO_OF_SECOND).toFormatter();
		TemporalAccessor acc = fmt.parse("864.123456789");
		assertEquals(acc.isSupported(SECOND_OF_DAY), true);
		assertEquals(acc.isSupported(NANO_OF_SECOND), true);
		assertEquals(acc.isSupported(MICRO_OF_SECOND), true);
		assertEquals(acc.isSupported(MILLI_OF_SECOND), true);
		assertEquals(acc.getLong(SECOND_OF_DAY), 864L);
		assertEquals(acc.getLong(NANO_OF_SECOND), 123456789L);
		assertEquals(acc.getLong(MICRO_OF_SECOND), 123456L);
		assertEquals(acc.getLong(MILLI_OF_SECOND), 123L);
	}

	@Test
	public void test_parse_fromField_SecondOfMinute() {
		DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendValue(SECOND_OF_MINUTE).toFormatter();
		TemporalAccessor acc = fmt.parse("32");
		assertEquals(acc.isSupported(SECOND_OF_MINUTE), true);
		assertEquals(acc.isSupported(NANO_OF_SECOND), true);
		assertEquals(acc.isSupported(MICRO_OF_SECOND), true);
		assertEquals(acc.isSupported(MILLI_OF_SECOND), true);
		assertEquals(acc.getLong(SECOND_OF_MINUTE), 32L);
		assertEquals(acc.getLong(NANO_OF_SECOND), 0L);
		assertEquals(acc.getLong(MICRO_OF_SECOND), 0L);
		assertEquals(acc.getLong(MILLI_OF_SECOND), 0L);
	}

	@Test
	public void test_parse_fromField_SecondOfMinute_NanoOfSecond() {
		DateTimeFormatter fmt = new DateTimeFormatterBuilder().appendValue(SECOND_OF_MINUTE).appendLiteral('.')
				.appendValue(NANO_OF_SECOND).toFormatter();
		TemporalAccessor acc = fmt.parse("32.123456789");
		assertEquals(acc.isSupported(SECOND_OF_MINUTE), true);
		assertEquals(acc.isSupported(NANO_OF_SECOND), true);
		assertEquals(acc.isSupported(MICRO_OF_SECOND), true);
		assertEquals(acc.isSupported(MILLI_OF_SECOND), true);
		assertEquals(acc.getLong(SECOND_OF_MINUTE), 32L);
		assertEquals(acc.getLong(NANO_OF_SECOND), 123456789L);
		assertEquals(acc.getLong(MICRO_OF_SECOND), 123456L);
		assertEquals(acc.getLong(MILLI_OF_SECOND), 123L);
	}

	@Test
	// GWT - TODO no locale aware works in GWT tests check in the real browser
	public void disable_test_parse_tzdbGmtZone() {
		String dateString = "2015,7,21,0,0,0,GMT+02:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,M,d,H,m,s,z", Locale.US);
		ZonedDateTime parsed = ZonedDateTime.parse(dateString, formatter);
		assertEquals(parsed, ZonedDateTime.of(2015, 7, 21, 0, 0, 0, 0, ZoneId.of("Etc/GMT-2")));
	}

}
