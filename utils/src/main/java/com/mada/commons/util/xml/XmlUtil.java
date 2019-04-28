package com.mada.commons.util.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by madali on 2018/11/22 16:01
 * <p>
 * 生成xml工具类，基本功能可以实现，后面需优化。
 */
public class XmlUtil {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void toElement(Object object, Element root) {
        if (object != null) {
            if ((object instanceof Number) || (object instanceof Boolean) || (object instanceof String) || (object instanceof Double) || (object instanceof Float)) {
                root.setText(object.toString());
            } else if (object instanceof Map) {
                mapToElement((Map) object, root);
            } else if (object instanceof Collection) {
                collToElement((Collection) object, root);
            } else {
                pojoToElement(object, root);
            }
        } else {
            root.setText("");
        }
    }

    /**
     * list中存放的数据类型有基本类型、用户自定对象 map、list
     *
     * @param coll
     * @param root
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void collToElement(Collection<?> coll, Element root) {
        for (Iterator<?> it = coll.iterator(); it.hasNext(); ) {
            Object value = it.next();
            if (coll == value) {
                continue;
            }
            if ((value instanceof Number) || (value instanceof Boolean) || (value instanceof String) || (value instanceof Double) || (value instanceof Float)) {
                Class<?> classes = value.getClass();
                String objName = classes.getName();
                String elementName = objName.substring(objName.lastIndexOf(".") + 1, objName.length());
                Element elementOfObject = root.addElement(elementName);
                elementOfObject.setText(value.toString());
            } else if (value instanceof Map) {
                Class<?> classes = value.getClass();
                String objName = classes.getName();
                String elementName = objName.substring(objName.lastIndexOf(".") + 1, objName.length());
                Element elementOfObject = root.addElement(elementName);
                mapToElement((Map) value, elementOfObject);
            } else if (value instanceof Collection) {
                Class<?> classes = value.getClass();
                String objName = classes.getName();
                String elementName = objName.substring(objName.lastIndexOf(".") + 1, objName.length());
                Element elementOfObject = root.addElement(elementName);
                collToElement((Collection) value, elementOfObject);
            } else {
                toElement(value, root);
            }

        }
    }

    /**
     * map中存放的数据类型有基本类型、用户自定对象 map、list
     *
     * @param map
     * @param root
     */
    @SuppressWarnings("rawtypes")
    public static void mapToElement(Map<String, Object> map, Element root) {
        for (Iterator<?> it = map.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            String name = (String) entry.getKey();
            if (null == name)
                continue;
            Object value = entry.getValue();
            if (value == map) {

            }
            Element elementValue = root.addElement(name);
            toElement(value, elementValue);
        }
    }

    public static void pojoToElement(Object obj, Element root) {
        Class<?> classes = obj.getClass();
        String objName = classes.getName();
        String elementName = objName.substring(objName.lastIndexOf(".") + 1, objName.length());

        Element elementOfObject;
        if (!root.getName().equals(elementName)) {
            elementOfObject = root.addElement(elementName);
        } else {
            elementOfObject = root;
        }
        Field[] fields = classes.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers()))
                continue;
            String name = f.getName();
            f.setAccessible(true);
            Object value;
            try {
                value = f.get(obj);
            } catch (Exception e) {
                value = null;
            }
            Element elementValue = elementOfObject.addElement(name);
            toElement(value, elementValue);
        }
    }

    public static void createXmlDocument(Object obj, String filePath) {
        Class<?> classes = obj.getClass();
        String objName = classes.getName();
        String elementName = objName.substring(objName.lastIndexOf(".") + 1, objName.length());
        createXmlDocument(obj, filePath, elementName);
    }

    public static void createXmlDocument(Object obj, String filePath, String xmlRootName) {
        Document xmlDoc = DocumentHelper.createDocument();
        Element root = xmlDoc.addElement(xmlRootName);
        toElement(obj, root);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        try {
            XMLWriter xmlw = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), "UTF-8"), format);
            xmlw.write(xmlDoc);
            xmlw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
