package certif.module;

import java.util.List;

// Le service doit être accessible du module qui provides
public interface StringGestion { // Le service ne peut être une enum
    List<String> getChaines();
}