package org.codemetrics.cubes.definition;

public abstract class DimensionDefinition {

    private String name;
    private FeatureDefinition[] featuresDefinition;

    public DimensionDefinition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public FeatureDefinition[] getFeaturesDefinition() {
        return featuresDefinition;
    }

    public void setFeaturesDefinition(FeatureDefinition[] featuresDefinition) {
        this.featuresDefinition = featuresDefinition;
    }

    public abstract void addFeatures();
}