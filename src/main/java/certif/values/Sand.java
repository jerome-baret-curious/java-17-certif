package certif.values;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class Sand {
    public static void main(String[] args) {
        // Primitives assigned to their default values if they were fields:
        boolean bb = false;
        Boolean bbw = bb;

        byte ff = 0; // promotable to short
        Byte ffw = ff;
        short hh = 0; // promotable to int
        Short hhw = hh;
        char mm = '\u0000'; // promotable to int
        Character mmw = mm;
        int cc = 0; // promotable to long
        Integer ccw = cc;
        long aa = 0L; // promotable to float
        Long aaw = aa;

        float uu = 0.0f; // promotable to double
        Float uuw = uu;
        double tt = 0.0d;
        Double ttw = tt;
        datetimes();

        int a = 1;
        int b = ~a; // -2
        System.out.println(b);
        int c = a ^ b; // < 0
        System.out.println(c);

        var tientien = 0b01111111_11111111_11111111_11111111; //Integer.MAX_VALUE
        System.out.println(tientien);

        var anonObj = new Object() {
            String name = "Pomme";
            int total = 60;
        };
        System.out.println("name = " + anonObj.name + ", total = " + anonObj.total);
    }

    public static void mathAPI() {
        System.out.println(Math.E);
        System.out.println(Math.PI);
        // *Exact methods throw ArithmeticException
    }

    public static void strings() {
        String param = "value";
        // text block opens with """ followed by 0-n spaces, tabs, form feeds then line terminator
        // min indent is chosen as base
        String json = """
                {
                   "field": "%s"
                }""".formatted(param);
        System.out.println(json); // json is { then newline then 4 spaces and "field"... then newline and }
    }

    public static void datetimes() {
        LocalDate firstJanuary = LocalDate.of(2024, 1, 1);
        LocalDate firstJanuaryToo = LocalDate.parse("2024-01-01");
        LocalDateTime beforeChange = LocalDateTime.of(2015, Month.NOVEMBER, 1, 1, 0);
        LocalDate firstFebruary = firstJanuary.plus(Period.ofMonths(1));

        LocalDate endOfFebruary = firstFebruary.with(TemporalAdjusters.lastDayOfMonth());

        LocalTime nineThirty = LocalTime.parse("10:30").minusHours(1);

        ZoneId zoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime firstJanuaryMorning = ZonedDateTime.of(firstJanuaryToo.atTime(nineThirty), zoneId);

        // The same zone id may reference many zone offsets
        ZoneId zoneId_LosAngeles = ZoneId.of("America/Los_Angeles");
        ZonedDateTime before = beforeChange.atZone(zoneId_LosAngeles);
        ZonedDateTime after = before.plusHours(1);
        System.out.println(before + " and " + after); // offset has changed, not zone id

        ZoneOffset offset = ZoneOffset.of("+02:00");

        // 2024-01-01T14:00+02:00
        OffsetDateTime offSetByTwo = OffsetDateTime.of(firstJanuaryToo.atTime(16, 0), offset);

        OffsetDateTime beforeOff = beforeChange.atOffset(ZoneOffset.of("-07:00"));
        OffsetDateTime afterOff = beforeOff.plusHours(1);
        System.out.println(beforeOff + " and " + afterOff); // 2015-11-01T01:00-07:00 and 2015-11-01T02:00-07:00

        long twentyEight = ChronoUnit.DAYS.between(firstFebruary, endOfFebruary);
        System.out.println(twentyEight);

        LocalDate now = LocalDate.now();
        System.out.println(now.isAfter(now)); // false
    }
}
