package com.omertex.model;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

/**
 * by afilonenko on 3/15/2017.
 */
public class Region {

    private String id;
    private List<Year> years;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Year> getYears() {
        return years;
    }

    public void setYears(List<Year> years) {
        this.years = years;
    }
}
