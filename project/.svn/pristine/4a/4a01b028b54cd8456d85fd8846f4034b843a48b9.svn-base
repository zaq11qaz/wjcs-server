package com.huihe.eg.grab.service.impl;

import com.cy.framework.util.StringUtil;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.huihe.eg.grab.service.dao.GrabBaseService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GrabBaseServiceImpl implements GrabBaseService {
    private WebClient wc = new WebClient();

    public GrabBaseServiceImpl() {
        initGrab();
    }

    /**
     * 初始化
     */
    @Override
    public WebClient initGrab(int timeOut) {
        WebClientOptions webClientOptions = wc.getOptions();
        webClientOptions.setJavaScriptEnabled(true); //启用JS解释器，默认为true
        webClientOptions.setCssEnabled(false); //禁用css支持
        webClientOptions.setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常
        webClientOptions.setTimeout(timeOut); //设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        return wc;
    }

    @Override
    public WebClient initGrab() {
        return initGrab(10000);
    }

    /**
     * 根据url获取html字符串
     *
     * @param url
     * @return
     */
    @Override
    public String getHtmlStr(String url) {
        String html = null;
        try {
            logger.warn("request url:" + url);
            HtmlPage page = wc.getPage(url.replaceAll(" ", ""));
            html = page.asXml();
        } catch (IOException io) {
            logger.warn(io.getMessage(), io);
        }
        return html;
    }

    /**
     * 根据url获取 dom对象
     *
     * @param url
     * @return
     */
    @Override
    public Document getDocument(String url) {
        String html = getHtmlStr(url);
        return StringUtil.isEmpty(html) ? null : Jsoup.parse(html);
    }

    @Override
    public Elements getElements(String url, String querySelector) {
        Elements elements = null;
        logger.warn("getElemetns querySelector:" + querySelector);
        try {
            Document document = getDocument(url);
            if (document != null) {
                elements = document.body().select(querySelector);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
        logger.warn("getElemetns querySelector result size:" + (elements == null ? 0 : elements.size()));
        return elements;
    }

    @Override
    public Elements getElements(Document document, String querySelector) {
        return document != null ? getElements(document.body(), querySelector) : null;
    }

    @Override
    public Elements getElements(Element element, String querySelector) {
        return element != null ? element.select(querySelector) : null;
    }

    @Override
    public Elements getElements(Elements elements, String querySelector) {
        return (elements != null && elements.size() > 0) ? elements.select(querySelector) : null;
    }

    @Override
    public String getAttribute(Document document, String key) {
        return (document != null) ? document.attr(key) : null;
    }

    @Override
    public String getAttribute(Elements document, String key) {
        return (document != null && document.size() > 0) ? document.attr(key) : null;
    }

    @Override
    public String getAttribute(Element document, String key) {
        return document != null ? document.attr(key) : null;
    }


    public static void main(String [] args){
        String encode = "";
        try {
            encode = URLEncoder.encode("趣事", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        new GrabBaseServiceImpl().getDocument("https://www.zhihu.com/signup?next=%2F");

    }
}
