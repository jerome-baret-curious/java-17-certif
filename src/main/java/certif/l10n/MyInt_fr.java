package certif.l10n;

import java.util.ListResourceBundle;

public class MyInt_fr extends ListResourceBundle {
   public Object[][] getContents() {
      return contents;
   }
   static final Object[][] contents = {
           //any object as value
      {"ok", "ok"},
      {"cancel", "annuler"},
      {"weekend", "weekend"},
   };
}