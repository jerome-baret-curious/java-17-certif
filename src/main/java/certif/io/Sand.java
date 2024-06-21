package certif.io;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Stream;

public class Sand {
    public static void main(String[] args) {

        System.out.format("%f, %1$+020.10f %n", Math.PI); //3.141593, +00000003.1415926536

        scan();

        rel();

        cc();

        ser();

        marche();
    }

    private static void ser() {
        OneClass obj = new OneClass("yy");
        byte[] ba = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(obj);
            oos.writeObject(obj);
            ba = baos.toByteArray();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        OneClass oc1 = null;
        OneClass oc2 = null;
        InputStream is = new ByteArrayInputStream(ba);
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            ObjectInputFilter filter = ObjectInputFilter.Config.createFilter("maxdepth=2");
            ois.setObjectInputFilter(filter);
            oc1 = (OneClass) ois.readObject();
            oc2 = (OneClass) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(oc1.getName());
        System.out.println(oc1);
        System.out.println(oc2);//same as oc1

        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos2)) {
            oos.writeObject(new Oui());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos2.toByteArray());
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            Oui oui = (Oui)ois.readObject();
            System.out.println(oui); // Oui{j=10.0, i=5}
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void cc() {
        Console c = System.console(); // KNOW IT!
        if (c == null) {
            System.err.println("No console.");
            return;
        }

        String login = c.readLine("Enter your login: ");
        char[] password = c.readPassword("Enter your old password: ");
        System.out.println(login + " " + Arrays.toString(password));
    }

    public static void scan() {
        // useDelimiter("\\p{javaWhitespace}+")
        try (Scanner s = new Scanner(new BufferedReader(new FileReader(Paths.get("src/main/java/certif/io/thefile.txt").toFile())))) {
            s.useLocale(Locale.FRENCH);

            while (s.hasNext()) {
                System.out.println(s.next());
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void rel() {
        Path p1 = Paths.get("bro");
        Path p2 = Paths.get("sis");

        Path p1_rel_p2 = p1.relativize(p2);
        Path p2_rel_p1 = p2.relativize(p1);

        System.out.println(p1_rel_p2); // ../sis
        System.out.println(p2_rel_p1); // ../bro
    }

    public static void marche() {
        List<Path> result = new ArrayList<>();
        List<Path> result2 = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get("/home"), 3, FileVisitOption.FOLLOW_LINKS);
             Stream<Path> list = Files.list(Paths.get("/home"))) {
            result = walk.filter(Files::isRegularFile).toList();
            result2 = list.filter(Files::isRegularFile).toList();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(result);
        System.out.println(result2);

        try {
            Path startingFile = Files.walkFileTree(Paths.get("/home"), Collections.emptySet(), 3, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path file, BasicFileAttributes attrs) {
                    System.out.println("preVisitDirectory");
                    System.out.println(file);
                    System.out.println(attrs.size());
                    System.out.println("CONTINUE");
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    System.out.println("visitFile");
                    System.out.println(file);
                    System.out.println(attrs.creationTime());
                    System.out.println("SKIP_SIBLINGS");
                    return FileVisitResult.SKIP_SIBLINGS;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    System.out.println("visitFileFailed");
                    System.out.println(file);
                    System.out.println(exc);
                    System.out.println("SKIP_SUBTREE");
                    return FileVisitResult.SKIP_SUBTREE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path file, IOException exc) {
                    System.out.println("postVisitDirectory");
                    System.out.println(file);
                    System.out.println(exc);
                    System.out.println("TERMINATE");
                    return FileVisitResult.TERMINATE;
                }
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        try {
            Files.list(Paths.get("/home")).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Files.lines(Paths.get("src/main/resources/rb/MyInt.properties")).forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            List<String> strings = Files.readAllLines(Paths.get("src/main/resources/rb/MyInt.properties"));
            System.out.println(strings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Reponse {
    int i;
    Reponse() {
        i = 5;
    }
}
class Oui extends Reponse implements Serializable {
    double j;
    Oui() {
        j = 10.0;
        i = 88;
    }

    @Override
    public String toString() {
        return "Oui{" +
                "j=" + j +
                ", i=" + i +
                '}';
    }
}