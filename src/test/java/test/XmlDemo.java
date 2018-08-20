package test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

/**
 * Created by madali on 2018/1/4.
 */
public class XmlDemo {

    public static void main(String[] args) throws DocumentException {
        String definition = "";
        System.out.println(isLegal(definition));
    }

    private static Boolean isLegal(String definition) throws DocumentException {

        Boolean flag = false;
        if (definition == null) {
            return flag;
        }

        try {
            DocumentHelper.parseText(definition);
            flag = true;
        } catch (DocumentException e) {
            flag = false;
        }

        return flag;
    }

    private static Document getDocument(String definition) throws DocumentException {

        Document document;

        try {
            document = DocumentHelper.parseText(definition);
        } catch (DocumentException e) {
            throw new DocumentException(e);
        }

        return document;
    }

}
