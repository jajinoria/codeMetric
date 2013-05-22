package org.codemetrics.cubes.definition;

public abstract class ScaleDefinition {

    private final String name;
    private final double to;
    private final double factor;

    public ScaleDefinition(String name, double to, double factor) {
        this.name = name;
        this.to = to;
        this.factor = factor;
    }

    public String getName() {
        return name;
    }

    public double getTo() {
        return to;
    }

    public double getFactor() {
        return factor;
    }
}