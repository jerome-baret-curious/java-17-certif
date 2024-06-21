package certif.oop;

public sealed class Shape permits Circle, Square, Rectangle { // permits can be omitted if in same file
    // FilledRectangle can't be in permits because it's not a direct subclass
    // permits must be in same module or same package if unnamed module
}