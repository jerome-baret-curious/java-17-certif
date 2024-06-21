package certif.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Sand {
    public static void main(String[] args) {
        // concurrent reduction : stream parallel, the collector is concurrent and the stream or the collector is unordered
        List<Integer> listOfNumbers = Arrays.asList(1, 2, 3, 4);
        int sum = listOfNumbers.parallelStream().reduce(5, Integer::sum);
        int sumTrue = listOfNumbers.parallelStream().reduce(0, Integer::sum) + 5;
        System.out.println(sum + " but true sum:" + sumTrue);

        List<Bonhomme> bons = Arrays.asList(new Bonhomme(), new Bonhomme(), new Bonhomme(), new Bonhomme(), new Bonhomme(), new Bonhomme(), new Bonhomme());
        System.out.println(bons);
        ConcurrentMap<Integer, List<Bonhomme>> byAge = bons.parallelStream().collect(Collectors.groupingByConcurrent(Bonhomme::getAge));
        System.out.println(byAge); //groupingBy would be more expensive as order in the list is preserved

        bons = Arrays.asList(new Bonhomme(1), new Bonhomme(2), new Bonhomme(3));
        ConcurrentMap<Integer, Bonhomme> oneByAge = bons.parallelStream().collect(Collectors.toConcurrentMap(Bonhomme::getAge, Function.identity()));
        System.out.println(oneByAge); //toMap would be more expensive as results are inserted in encounter order

        Optional<Integer> optSum = Stream.of(51, 2, 3, 4, 5, 6).reduce(Integer::sum);
        Integer reduced = Stream.of(2, 3, 4, 5, 6).reduce(51, Integer::sum);
        System.out.println(optSum + " = " + reduced);
    }

    static class Bonhomme {
        int age = (int) (Math.random() * 5);
        int id = (int) (Math.random() * 100);

        public Bonhomme() {
        }

        public Bonhomme(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "{" +
                    "age=" + age +
                    ", id=" + id +
                    '}';
        }
    }
}
