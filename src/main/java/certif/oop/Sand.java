package certif.oop;

import java.util.HashMap;
import java.util.Map;

public class Sand {
    private int i = 0;
    private Inner inn;
    private StaticNest nest;

    interface Hello {
        void coucou(String qqun);
    }

    public static void main(String[] args) {
        Sand sand = new Sand();
        sand.doit();
        sand.french.coucou("moi");
    }

    Hello french = new Hello() { // could have been a class to extend
        static Map<String, Long> hh= new HashMap<>();
        static {
            hh.put("1", 1L);
        }
        String name = "tout le monde"; // so IDE doesn't propose a lambda
        public void coucou(String qqun) {
            hh = new HashMap<>();
            name = qqun;
            System.out.println("Salut " + name+hh.get("1"));
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
                    System.out.println("Showinner " + indeed +" "+i+" "+ Sand.this.i + another);
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
}
