package com.core.helper;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.Set;

/**
 * @description: xml数据解析
 * 
 * @author Yangcl
 * @date 2016年10月26日 下午6:08:52 
 * @version 1.0.0
 */
public class XmlHelper {

    /**
     * 构造Document的简便方法
     * @param text
     * @return
     */
    public static Document buildDocument(String text){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // never forget this!
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(text.getBytes("UTF-8")));
            return doc;
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 构造XPaath的表达式对象
     * @param text
     * @return
     */
    public static XPathExpression buildXPathExpression(String text){
        try{
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile(text);
            return expr;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据xpath查询定义的值
     * @param doc
     * @param xpath
     * @return
     */
    public static String getValueFormXPath(Document doc, String xpath){
        XPathExpression expr = buildXPathExpression(xpath);
        try{
            Node node = (Node)expr.evaluate(doc, XPathConstants.NODE);
            if(node == null) return null;
            return node.getNodeValue();
        }catch (Exception e){
            return null;
        }
    }

    public static String mapToXml(Map<String,String> map){
        StringBuilder builder = new StringBuilder();
        builder.append("<xml>");

        Set<Map.Entry<String,String>> entrySet =  map.entrySet();
        for(Map.Entry<String,String> entry : entrySet){
            builder.append("<").append(entry.getKey()).append(">");
            builder.append(entry.getValue());
            builder.append("</").append(entry.getKey()).append(">");
        }
        builder.append("</xml>");
        return builder.toString();
    }
}
