package com.huihe.eg.comm.util;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.cy.framework.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * @ Author     ：zwy
 * @ Date       ：2021/4/12 17:03
 * @ Description：
 * @ since: JDk1.8
 */
public class StringXmlUtil {

    /**
     * XML 字符串转 Map
     *
     * @param xmlStr
     * @return
     */
    public static Map<String, String> xml2ToMap(String xmlStr) {

        Map<String, String> map = new HashMap<String, String>();
        if (StringUtil.isEmpty(xmlStr)) {
            throw new IllegalArgumentException("xml is empty");
        } else {
            Document document = null;
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
                document = documentBuilder.parse(is);

            } catch (Exception e) {
                e.printStackTrace();
            }


            Element element = document.getDocumentElement();
            if (element != null) {
                NodeList nodeList = element.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    String nodeName = node.getNodeName();
                    String nodeText = node.getTextContent();
                    if ("#text".equals(nodeName)) {
                        continue;
                    }
                    map.put(nodeName, nodeText);
                }
            }
        }
        return map;
    }

}
