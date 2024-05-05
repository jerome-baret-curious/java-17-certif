package certif.l10n;

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Sand {
    public static void main(String[] args) throws ParseException {
        Locale locale_CA_Unix = new Locale("fr", "CA", "UNIX");
        ResourceBundle bundle = ResourceBundle.getBundle("certif.l10n.MyInt", locale_CA_Unix);
        String weekend = bundle.getString("weekend");
        System.out.println(weekend);

        bundle = ResourceBundle.getBundle("rb.MyInt", locale_CA_Unix);
        weekend = bundle.getString("weekend");
        System.out.println(weekend);

        MessageFormat mf = new MessageFormat("{0,date,short} {0,time} {1,number,percent} {1,number,currency}");
        String result = mf.format(new Object[]{new Date(), 0.4});
        System.out.println(result);

        String pattern = """
                Test with choice for '{0}'\s
                You have {0, choice, 0#no notifications|1#a notification|2#two notifications|2<{0, number, integer} notifications}.""";
        MessageFormat formatter = new MessageFormat(pattern, Locale.UK);
        System.out.println(formatter.format(new Object[]{0}));
        System.out.println(formatter.format(new Object[]{1}));
        System.out.println(formatter.format(new Object[]{2}));
        System.out.println(formatter.format(new Object[]{3}));

        Object[] parse = mf.parse("5/5/24 5:17:24 PM 40% $0.40");
        for (Object o : parse) System.out.println(o + " " + o.getClass());
    }
}
