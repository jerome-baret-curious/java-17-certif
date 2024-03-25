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
        LocalDate firstFebruary = firstJanuary.plus(Period.ofMonths(1));

        LocalDate endOfFebruary = firstFebruary.with(TemporalAdjusters.lastDayOfMonth());

        LocalTime nineThirty = LocalTime.parse("10:30").minusHours(1);

        ZoneId zoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime firstJanuaryMorning = ZonedDateTime.of(firstJanuaryToo.atTime(nineThirty), zoneId);

        ZoneOffset offset = ZoneOffset.of("+02:00");

        // 2024-01-01T14:00+02:00
        OffsetDateTime offSetByTwo = OffsetDateTime.of(firstJanuaryToo.atTime(16, 0), offset);

        long twentyEight = ChronoUnit.DAYS.between(firstFebruary, endOfFebruary);
        System.out.println(twentyEight);
    }
}
