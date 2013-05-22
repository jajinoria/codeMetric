package org.codemetrics.cubes.definition;

import org.sumus.dwh.time.TimeScale;

public abstract class CubeDefinition {

    private final String name;
    private final TimeScale timeScale;
    private DimensionDefinition[] dimensionDefinitions;
    private MetricDefinition[] metricDefinitions;

    public CubeDefinition(String name, TimeScale timeScale) {
        this.name = name;
        this.timeScale = timeScale;
    }

    public String getName() {
        return name;
    }

    public TimeScale getTimeScale() {
        return timeScale;
    }

    public DimensionDefinition[] getDimensionDefinitions() {
        return dimensionDefinitions;
    }

    public void setDimensionDefinitions(DimensionDefinition[] dimensionDefinitions) {
        this.dimensionDefinitions = dimensionDefinitions;
    }

    public MetricDefinition[] getMetricDefinitions() {
        return metricDefinitions;
    }

    public void setMetricDefinitions(MetricDefinition[] metricDefinitions) {
        this.metricDefinitions = metricDefinitions;
    }

    public abstract void addDimensions();

    public abstract void addMetrics();
}