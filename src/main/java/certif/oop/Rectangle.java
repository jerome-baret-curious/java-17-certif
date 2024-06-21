package certif.oop;

public sealed class Rectangle extends Shape permits FilledRectangle { // sealed class must have subclasses
    public double length, width;
}