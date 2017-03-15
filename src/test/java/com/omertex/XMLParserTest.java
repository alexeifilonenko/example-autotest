package com.omertex;

import org.junit.Test;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * by afilonenko on 3/14/2017.
 */
public class XMLParserTest {

    @Test
    public void testParseXML() throws Exception {
        File file = new File("regions.xml");
        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = dBuilder.parse(file);
        // recommended
        document.getDocumentElement().normalize();

        assertEquals("country", document.getDocumentElement().getNodeName());

        NodeList nList = document.getElementsByTagName("region");

        System.out.println("----------------------------");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                System.out.println("Region id: " + element.getAttribute("id"));

                NodeList years = element.getElementsByTagName("year");

                for (int i = 0; i < years.getLength(); i++) {
                    Node node = years.item(i);
                    Element elem = (Element) node;
                    System.out.println("\nCurrent year: " + elem.getAttribute("id"));
                    System.out.println("Property1 : " + elem.getElementsByTagName("property1").item(0).getTextContent());
                    System.out.println("Property2 : " + elem.getElementsByTagName("property2").item(0).getTextContent());
                }
            }
        }
    }
}
