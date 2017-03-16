package com.omertex.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * by afilonenko on 3/15/2017.
 */
public class Year {

    private String id;
    private String property1;
    private String property2;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }
}
