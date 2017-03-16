package com.omertex.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 *  by afilonenko on 3/16/2017.
 */
@XmlRootElement(name = "country")
public class Country {

    private List<Region> regions;

    @XmlElement
    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}
