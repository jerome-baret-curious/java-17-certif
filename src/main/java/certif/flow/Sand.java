package certif.flow;

public class Sand {
    public enum Day {
        SUNDAY, MONDAY, TUESDAY,
        WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;
    }

    public static void main(String[] args) {
        // switch expressions must be exhaustive
        // and yield or throw
        Day day = Day.WEDNESDAY;
        System.out.println(expressionSwitchLabel(day));
        System.out.println(expressionSwitchStatement(day));
        statementSwitchNonExhaustive(day);

        Mere m = new PetiteFille();
        if (m instanceof PetiteFille pf) {
            System.out.println(pf);
            if (m instanceof Fille f) { // f instanceof Fille pf not allowed
                System.out.println(f);
            }
        }
    }

    public static int expressionSwitchLabel(Day day) {
        return switch (day) {
            // switch labeled rule {
            case MONDAY, FRIDAY, SUNDAY -> {
                System.out.println(6);
                yield 6;
            }
            // }
            case WEDNESDAY -> {
                System.out.println(7);
                yield 7;
            }
            case TUESDAY -> 3;
            default -> throw new IllegalStateException("Invalid day: " + day);
        };
    }

    public static int expressionSwitchStatement(Day day) {
        // has fallthrough
        return switch (day) {
            // switch labeled statement group {
            case MONDAY:
            case FRIDAY:
            case SUNDAY: {
                System.out.println(8);
                yield 8;
            }
            // }
            case WEDNESDAY: {
                System.out.println(9);
            }
            case TUESDAY:
                yield 10;
            default:
                throw new IllegalStateException("Invalid day: " + day);
        };
    }

    public static void statementSwitchNonExhaustive(Day day) {
        switch (day) {
            case WEDNESDAY:
                System.out.println(2);
                System.out.println(3);
            case MONDAY:
            case SUNDAY: {
                System.out.println(1);
            }
        }
    }
}

class Mere {
}

class Fille extends Mere {

}

class PetiteFille extends Fille {

}