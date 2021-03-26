package com.huihe.eg.grab.service.dao;

import com.gargoylesoftware.htmlunit.WebClient;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface GrabBaseService {
    Logger logger = LoggerFactory.getLogger(GrabBaseService.class);

    WebClient initGrab(int timeOut);

    WebClient initGrab();

    String getHtmlStr(String url);

    Document getDocument(String url);

    Elements getElements(String url, String querySelector);

    Elements getElements(Document document, String querySelector);

    Elements getElements(Element element, String querySelector);

    Elements getElements(Elements elements, String querySelector);

    String getAttribute(Document document, String key);

    String getAttribute(Elements document, String key);

    String getAttribute(Element document, String key);
}
