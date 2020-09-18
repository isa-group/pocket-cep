package com.siddhiApi.util;

public class PropertyFirstAppearance implements Comparable<PropertyFirstAppearance>{
    private String property;
    private int firstAppearance;

    public PropertyFirstAppearance(String property, int firstAppearance) {
        this.property = property;
        this.firstAppearance = firstAppearance;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public int getFirstAppearance() {
        return firstAppearance;
    }

    public void setFirstAppearance(int firstAppearance) {
        this.firstAppearance = firstAppearance;
    }

    @Override
    public int compareTo(PropertyFirstAppearance propertyFirstAppearance) {
        return firstAppearance - propertyFirstAppearance.getFirstAppearance();
    }
}
