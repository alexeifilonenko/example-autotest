package com.omertex.service;

import com.omertex.model.Region;
import com.omertex.model.Year;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * by afilonenko on 3/15/2017.
 */
public class XMLRegionService implements RegionService{

    private static final String XML_FILE = "regions.xml";

    @Override
    public List<Region> getRegions() throws Exception {
        Document document;
        File file;
        List<Region> regions = new ArrayList<>();
        Region region;
        List<Year> years;
        Year year;

        file = new File(XML_FILE);
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        document = dBuilder.parse(file);
        // recommended action
        document.getDocumentElement().normalize();

        NodeList regionsNodes = document.getElementsByTagName("region");

        for (int i = 0; i < regionsNodes.getLength(); i++) {
            region = new Region();
            years = new ArrayList<>();
            Element regionElement = (Element) regionsNodes.item(i);
            region.setId(regionElement.getAttribute("id"));
            NodeList yearsNodes = regionElement.getElementsByTagName("year");

            for (int j = 0; j < yearsNodes.getLength(); j++) {
                year = new Year();
                Element yearElement = (Element) yearsNodes.item(j);
                year.setId(yearElement.getAttribute("id"));
                year.setProperty1(yearElement.getElementsByTagName("property1").item(0).getTextContent());
                year.setProperty2(yearElement.getElementsByTagName("property2").item(0).getTextContent());
                years.add(year);
            }
            region.setYears(years);
            regions.add(region);
        }

        return regions;
    }
}
