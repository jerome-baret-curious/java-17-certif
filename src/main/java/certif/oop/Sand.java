package certif.oop;

import java.util.HashMap;
import java.util.Map;

public class Sand {
    private int i = 0;
    private Inner inn;
    private StaticNest nest;

    interface Hello {
        void coucou(String qqun);

        static int voyons() {
            return 1;
        }
    }

    public static void main(String[] args) {
        System.out.println(LESNOMBRES.ZERO.ordinal()); // 0
        System.out.println(LESNOMBRES.valueOf("UNO")); // UNO
        Sand sand = new Sand();
        sand.doit();
        //sand.clone(); //throws checked CloneNotSupportedException because not Cloneable
        sand.french.coucou("moi");
        System.out.println("Only from Interface type " + Hello.voyons());

        TheRec theRec = new TheRec("zaerghfdhgf");
        System.out.println(theRec.name());

        System.out.println(Rates.NORMAL);
        System.out.println(Rates.HIFI);
    }

    Hello french = new Hello() { // could have been a class to extend
        static Map<String, Long> hh = new HashMap<>();

        static {
            hh.put("1", 1L);
        }

        String name = "tout le monde"; // so IDE doesn't propose a lambda

        public void coucou(String qqun) {
            hh = new HashMap<>();
            name = qqun;
            System.out.println("Salut " + name + hh.get("1"));
        }
    }; // ; because the class expression is part of the instantiation statement

    Sand() {
        inn = new Inner();
        nest = new StaticNest();
    }

    void doit() {
        inn.print("one");
        nest.print("two");
    }

    protected class Inner {
        static void tt() {
            System.out.println("Bye bye");
        }

        void print(String indeed) { // indeed is a local effectively final
            String another = "must be";
            class InsideInner {
                static boolean possible = true;

                void show(int i) {
                    System.out.println("Showinner " + indeed + " " + i + " " + Sand.this.i + another);
                }

                static void sayGoodbye() {
                    System.out.println("Bye bye");
                }
            }
            System.out.println("Hello " + i);
            InsideInner.sayGoodbye();
            new InsideInner().show(88);
        }
    }

    static class StaticNest {
        boolean b = true;

        static void tt() {
            System.out.println("Bye bye");
        }

        void print(String indeed) { // indeed is a local effectively final
            String another = "must be";
            class InsideStatic {
                void show() {
                    System.out.println("Showstatic " + indeed + b + another);
                }

                static void sayGoodbye() {
                    System.out.println("Bye bye");
                }
            }
            System.out.println("Bye");
            InsideStatic.sayGoodbye();
            new InsideStatic().show();
        }
    }

    record TheRec(String name) {
        public TheRec { // compact constructor to avoid explicit assignment
            if (name.length() < 10) {
                throw new IllegalArgumentException("too short");
            }
        }

        public int length() {
            return name.length();
        }

        public static String sub(String one) {
            return one.substring(0, Math.min(0, one.length() - 5));
        }

        record NestedRec(String age) { //static
            public String ofP() {
                return age;
            }
        }
    }

    public enum Rates {

        NORMAL(0.5) {
            @Override
            public String toString() {
                return "It's 5%";
            }
        },
        HIFI(0.7) {
            @Override
            public String toString() {
                return "There is 7%";
            }
        };

        private final double rate;

        Rates(double rate) {
            this.rate = rate;
        }
    }

    static <T extends Chanteur & Danseur> void showInter(T humain) {
        humain.chante();
        humain.danse();
    }
}

interface Chanteur {
    void chante();
}

interface Danseur {
    void danse();
}