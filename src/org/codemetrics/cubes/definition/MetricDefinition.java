package org.codemetrics.cubes.definition;

public class MetricDefinition {

    private final String name;
    private MetricDefinition metricDefinition;

    public MetricDefinition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public MetricDefinition getMetricDefinition() {
        return metricDefinition;
    }

    public void setMetricDefinition(MetricDefinition metricDefinitions) {
        this.metricDefinition = metricDefinitions;
    }
}