package com.malikov.lowcostairline.context;

import com.malikov.lowcostairline.context.exception.ContextException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Yurii Malikov
 */
public class XMLApplicationContext implements IApplicationContext{

    private static final String BEANS = "beans";
    private static final String BEAN = "bean";
    private static final String PROPERTY = "property";
    private static final String ID = "id";
    private static final String CLASS = "class";
    private static final String NAME = "name";
    private static final String REF = "ref";
    private static final String VALUE = "value";


    private Map<String, Object> contextBeanMap = new HashMap<>();

    private Document[] xmlDocuments;

    private String[] xmlDocumentsPaths;

    public XMLApplicationContext(String... xmlDocumentsPaths) {
        this.xmlDocumentsPaths = xmlDocumentsPaths;
    }

    @Override
    public Object getBean(String beanName) {
        return contextBeanMap.get(beanName);
    }

    @Override
    public void initialize() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL[] xmlDocumentsUrls = new URL[xmlDocumentsPaths.length];
            xmlDocuments = new Document[xmlDocumentsPaths.length];
            for (int i = 0; i < xmlDocuments.length; i++) {
                xmlDocumentsUrls[i] = getClass().getClassLoader().getResource(xmlDocumentsPaths[i]);
                if (xmlDocumentsUrls[i] == null) {
                    throw new FileNotFoundException("XML document has not been found.");
                }
                xmlDocuments[i] = documentBuilder.parse(xmlDocumentsUrls[i].getPath());
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new ContextException("Failed to initialize context", e);
        }
        parseXMLDocuments();
    }

    private void parseXMLDocuments() {
        for (Document document : xmlDocuments) {
            NodeList beanDeclarationNodes = document.getElementsByTagName(BEANS);
            Node xmlBeansTagNode = beanDeclarationNodes.item(0);
            if (xmlBeansTagNode == null) {
                throw new ContextException("There is no <beans> tag in " + document.getDocumentURI() + " file to parse");
            }
            Queue<XMLBeanProperty> commonXMLBeanPropertiesQueue = new LinkedList<>();
            NodeList xmlBeans = xmlBeansTagNode.getChildNodes();
            for (int i = 0; i < xmlBeans.getLength(); i++) {
                Node xmlBean = xmlBeans.item(i);
                if (BEAN.equals(xmlBean.getNodeName())) {
                    String beanName = createBean(xmlBean);

                    NodeList xmlProperties = xmlBean.getChildNodes();

                    for (int j = 0; j < xmlProperties.getLength(); j++) {
                        if (PROPERTY.equals(xmlProperties.item(j).getNodeName())) {
                            commonXMLBeanPropertiesQueue.offer(new XMLBeanProperty(beanName, xmlProperties.item(j)));
                        }
                    }
                }
            }
            while (!commonXMLBeanPropertiesQueue.isEmpty()) {
                instantiateXmlBeanProperties(commonXMLBeanPropertiesQueue.poll());
            }
        }

    }

    private void instantiateXmlBeanProperties(XMLBeanProperty xmlBeanProperty) {

        Node xmlBeanPropertyNode = xmlBeanProperty.getBeanProperty();

        NamedNodeMap xmlBeanPropertyAttributes = xmlBeanPropertyNode.getAttributes();
        Node propertyNameNode = xmlBeanPropertyAttributes.getNamedItem(NAME);
        Node propertyRefNode = xmlBeanPropertyAttributes.getNamedItem(REF);
        Node propertyValueNode = xmlBeanPropertyAttributes.getNamedItem(VALUE);

        if (propertyNameNode == null || (propertyRefNode == null) == (propertyValueNode == null)) {
            throw new ContextException(String.format("Bean property initialization failure. Bad syntax. "
                    + "Tag \"%s\" and one of \"%s\" or \"%s\" should be declared.", NAME, REF, VALUE));
        }

        Object bean = contextBeanMap.get(xmlBeanProperty.getBeanName());
        String propertyNameValue = propertyNameNode.getNodeValue();
        Class<?> beanClass = bean.getClass();

        Field beanField = findClassField(beanClass, propertyNameValue);

        if (beanField == null) {
            throw new ContextException(String.format("Bean property instantiation failure." +
                            " Property %s has not been found in class %s", propertyNameValue, beanClass));
        }

        beanField.setAccessible(true);

        Object beanFieldValue;
        if (propertyRefNode != null) {
            beanFieldValue = contextBeanMap.get(propertyRefNode.getNodeValue());
        } else {
            if (propertyValueNode.getNodeValue() == null) {
                throw new NullPointerException(String.format("Property value for bean \"%s\" not provided",  bean));
            }
            beanFieldValue = getFieldValue(beanField, propertyValueNode.getNodeValue());
        }
        try {
            beanField.set(bean, beanFieldValue);
        } catch (IllegalAccessException e) {
            throw new ContextException(e);
        }
    }

    private Object getFieldValue(Field beanField, String stringValue) {
        if (beanField.getType() == String.class) {
            return stringValue;
        }
        try {
            return beanField.getType().getConstructor(String.class).newInstance(stringValue);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new ContextException(String.format("Field %s instantiatiation by value %s failed", beanField, stringValue));
        }
    }

    /**
     * Recursive search for field in class and its superclasses
     */
    private static Field findClassField(Class clazz, String fieldName) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        Class superClass = clazz.getSuperclass();
        if (superClass != null) {
            return findClassField(superClass, fieldName);
        }
        return null;
    }

    private String createBean(Node bean) {
        NamedNodeMap xmlBeanAttributes = bean.getAttributes();
        String xmlBeanId = xmlBeanAttributes.getNamedItem(ID).getNodeValue();
        String xmlBeanClass = xmlBeanAttributes.getNamedItem(CLASS).getNodeValue();
        contextBeanMap.put(xmlBeanId, instantiateXMLBean(xmlBeanClass));
        return xmlBeanId;
    }


    private Object instantiateXMLBean(String xmlBeanClass) {
        try {
            Class<?> cls = Class.forName(xmlBeanClass);
            return cls.newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
            throw new ContextException("Bean instantiation failure.", e);
        }
    }

    private class XMLBeanProperty {

        String beanName;

        Node beanProperty;

        public XMLBeanProperty(String beanName, Node beanProperty) {
            this.beanName = beanName;
            this.beanProperty = beanProperty;
        }

        public String getBeanName() {
            return beanName;
        }
        public Node getBeanProperty() {
            return beanProperty;
        }

    }

}
