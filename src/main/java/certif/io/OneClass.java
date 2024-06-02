package certif.io;

import java.io.Serializable;

public class OneClass implements Serializable {

    //private static final long serialVersionUID = 1L; //automatic anyway

    private String name;
    private transient Long id;

    public OneClass(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
