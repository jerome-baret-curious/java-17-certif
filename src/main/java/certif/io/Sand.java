package certif.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Sand {
    public static void main(String[] args) {

        System.out.format("%f, %1$+020.10f %n", Math.PI); //3.141593, +00000003.1415926536

        scan();

        rel();

        cc();

        ser();
    }

    private static void ser() {
        OneClass obj = new OneClass("yy");
        byte[] ba = null;
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(obj);
            ois.writeObject(obj);
            ba = boas.toByteArray();
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
    }

    private static void cc() {
        Console c = System.console();
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
}
