package com.omertex.service;

import com.omertex.model.Country;
import com.omertex.model.Region;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

/**
 * by afilonenko on 3/15/2017.
 */
public class XMLRegionService implements RegionService {

    private static final String XML_FILE = "regions.xml";

    @Override
    public List<Region> getRegions() throws Exception {
        File file = new File(XML_FILE);
        JAXBContext jaxbContext = JAXBContext.newInstance(Country.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Country country = (Country) jaxbUnmarshaller.unmarshal(file);

        return country.getRegions();
    }

}