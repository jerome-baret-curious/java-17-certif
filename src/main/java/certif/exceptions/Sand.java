package certif.exceptions;

import java.io.*;
import java.util.Arrays;

public class Sand {
    public static void main(String[] args) {

        try {
            fir();
        } catch (Exception e) {
            System.out.println("fir exp : " + e.getClass()); // MyFirstException
            System.out.println("suppr : " + Arrays.toString(e.getSuppressed()));//exception from close is suppressed
        } finally {
            System.out.println("catch or finally is required for 'normal' try");
        }

        try {
            willNPE();
        } catch (Exception e) {
            System.out.println("is " + e.getClass());
            System.out.println("npe suppr : " + Arrays.toString(e.getSuppressed()));//empty
        }
    }

    private static void fir() throws MySecondException {
        StringReader sr = new StringReader("c:/dev");
        try (sr; // is effectively final
             BufferedReader br = new BufferedReader(sr);
             TestingClose tc = new TestingClose()) {
            br.readLine();
            tc.hello();
        } catch (IOException e) {
            System.out.println("exp " + e.getClass());
        } finally {
            System.out.println("finally");
        }
        // close tc then br then sr
    }

    private static void willNPE() throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("/dev");
        } finally {
            fis.close();
        }
    }
}

class TestingClose implements AutoCloseable {

    public void hello() throws MyFirstException {
        System.out.println("hello");
        throw new MyFirstException();
    }

    @Override
    public void close() throws MySecondException {
        System.out.println("Closed");
        throw new MySecondException();
    }
}

class TestingClose2 implements Closeable {
    private boolean alreadyClosed = false;
    @Override
    public void close() {
        if (!alreadyClosed) {
            System.out.println("Closing");
            alreadyClosed = true;// because of contract
        }
    }
}

class MyFirstException extends RuntimeException {
    MyFirstException() {
        super("", null, true, true);
    }
}

class MySecondException extends Exception {
}