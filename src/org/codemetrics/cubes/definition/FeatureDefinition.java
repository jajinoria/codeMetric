package org.codemetrics.cubes.definition;

public abstract class FeatureDefinition {

    private final String name;
    private final int type;

    public FeatureDefinition(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
