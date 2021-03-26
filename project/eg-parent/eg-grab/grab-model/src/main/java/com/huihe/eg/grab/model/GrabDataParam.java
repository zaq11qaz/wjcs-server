package com.huihe.eg.grab.model;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GrabDataParam {

    private Document document;
    private Elements elements;
    private String value;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
