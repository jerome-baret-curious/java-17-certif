package certif.module;

import java.util.List;

public class UnAutreFournisseur {

    // provider method
    public static StringGestion provider() {
        return new StringGestion() {
            @Override
            public List<String> getChaines() {
                return List.of();
            }
        };
    }
}