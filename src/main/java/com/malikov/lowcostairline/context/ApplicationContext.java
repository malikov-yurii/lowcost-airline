package com.malikov.lowcostairline.context;

import com.malikov.lowcostairline.context.exceptions.ApplicationContextInitializationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yurii Malikov
 */
public class ApplicationContext {

    private Map<String, Object> contextMap = new HashMap<>();

    private Document[] xmlDocuments;

    private String[] xmlDocumentsPaths;

    public ApplicationContext(String... xmlDocumentsPaths) {
        this.xmlDocumentsPaths = xmlDocumentsPaths;
    }

    public void initializeXmlDocuments(){
        try{
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL[] xmlDocumentsUrls = new URL[xmlDocumentsPaths.length];
            for (int i = 0; i < xmlDocuments.length; i++) {
                xmlDocumentsUrls[0] = getClass().getClassLoader().getResource(xmlDocumentsPaths[i]);
                if (xmlDocumentsUrls[0] == null) {
                    throw new FileNotFoundException("XML document has not been found.");
                }
                xmlDocuments[i] = documentBuilder.parse(xmlDocumentsUrls[i].getPath());


            }

        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new ApplicationContextInitializationException(e);
        }
    }





}
