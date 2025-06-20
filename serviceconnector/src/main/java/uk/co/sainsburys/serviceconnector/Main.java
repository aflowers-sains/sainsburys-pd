package uk.co.sainsburys.serviceconnector;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.boot.SpringApplication;

public class Main {

  public static void main(String[] args) {
//
//    LocalDateTime nineAMGMT = LocalDateTime.of(2025, 3, 29, 9, 0);
//    LocalDateTime nineAMBST = LocalDateTime.of(2025, 3, 30, 9, 0);
//
//    System.out.println("Current time in GMT: " + nineAMGMT);
//    System.out.println("Current time in BST: " + nineAMBST);
//
//    ZonedDateTime nineAMGMTZoned = ZonedDateTime.of(2025, 3, 29, 9, 0, 0, 0, java.time.ZoneId.of("Europe/London"));
//    ZonedDateTime nineAMBSTZoned = ZonedDateTime.of(2025, 3, 30, 9, 0, 0, 0, java.time.ZoneId.of("Europe/London"));
//
//    System.out.println("Current GMT Zoned time in UTC: " + nineAMGMTZoned);
//    System.out.println("Current BST Zoned time in UTC: " + nineAMBSTZoned);
//
    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    DateTimeFormatter formatterUTC = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(java.time.ZoneId.of("UTC"));
    DateTimeFormatter formatterUK = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(java.time.ZoneId.of("Europe/London"));
//
//    System.out.println("" + formatter.format(nineAMGMTZoned));
//    System.out.println("" + formatter.format(nineAMBSTZoned));
//    System.out.println("UTC" + formatterUTC.format(nineAMGMTZoned));
//    System.out.println("UTC" + formatterUTC.format(nineAMBSTZoned));
//
//    LocalDateTime nineAMGMTLocal = LocalDateTime.parse("2025-03-29T09:00:00Z", formatterUTC);
//    LocalDateTime nineAMBSTLocal = LocalDateTime.parse("2025-03-30T08:00:00Z", formatterUTC);
//
//    System.out.println("Parsed Saturday via UTC : " + nineAMGMTLocal);
//    System.out.println("Parsed Sunday via UTC : " + nineAMBSTLocal);
//
//    nineAMGMTLocal = LocalDateTime.parse("2025-03-29T09:00:00Z", formatter);
//    nineAMBSTLocal = LocalDateTime.parse("2025-03-30T09:00:00+01:00", formatter);
//
//    System.out.println("Parsed Saturday : " + nineAMGMTLocal);
//    System.out.println("Parsed Sunday : " + nineAMBSTLocal);

    var nineAMUTCGMT = ZonedDateTime.parse("2025-03-29T09:00:00Z", formatterUK);
    var nineAMUTCBST = ZonedDateTime.parse("2025-03-29T09:00:00+00:00", formatterUK);

    System.out.println("UTC Parsed Saturday : " + nineAMUTCGMT.getHour());
    System.out.println("UTC Parsed Sat -1 : " + nineAMUTCBST.getHour());

    //

    nineAMUTCGMT = ZonedDateTime.parse("2025-03-30T09:00:00Z", formatterUK);
    nineAMUTCBST = ZonedDateTime.parse("2025-03-30T09:00:00-01:00", formatterUK);

    System.out.println("Parsed Saturday : " + nineAMUTCGMT.getHour());
    System.out.println("Parsed Sat + 1 : " + nineAMUTCBST.getHour());

    LocalDateTime x = LocalDateTime.parse("2025-03-30T09:00:00+01:00", formatter);
    System.out.println("Parsed BST : " + x.getHour());
//
//    ZonedDateTime.of(localDate, localTime, ZoneId.of("Europe/London"));
//    System.out.println("a. " + formatterUK.format(nineAMUTCGMT));
//    System.out.println("b. " + formatterUK.format(nineAMUTCBST));
  }
}
