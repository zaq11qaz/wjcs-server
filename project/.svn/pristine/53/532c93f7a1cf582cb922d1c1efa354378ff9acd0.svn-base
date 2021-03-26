package com.huihe.eg.grab.service.impl;

import com.cy.framework.service.impl.BaseFrameworkServiceImpl;
import com.cy.framework.service.impl.DataException;
import com.cy.framework.util.StringUtil;
import com.huihe.eg.grab.model.GrabFilterRuleEntity;
import com.huihe.eg.grab.model.GrabFilterRuleJoinEntity;
import com.huihe.eg.grab.model.GrabTaskEntity;
import com.huihe.eg.grab.mybatis.dao.GrabTaskMapper;
import com.huihe.eg.grab.service.dao.GrabFilterRuleJoinService;
import com.huihe.eg.grab.service.dao.GrabTaskService;
import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class GrabTaskServiceImpl extends BaseFrameworkServiceImpl<GrabTaskEntity, Long> implements GrabTaskService {

    @Resource
    private GrabTaskMapper mapper;

    @Resource
    private TaskExecutor taskExecutor;

    @Resource
    private GrabFilterRuleJoinService ruleJoinService;
    @Override
    public void init() {
        setMapper(mapper);
    }

    @Override
    public Map<String, Object> execute(String taskIds) {
        Map<String, Object> map = null;
        try {
            /*if (StringUtil.isEmpty(taskIds)) {
                throw new DataException("任务id未传");
            }*/
            for (final String taskId : taskIds.split(",")) {
                taskExecutor.execute(new Job() {
                    @Override
                    void init() {
                        this.taskEntitys = getGrabTask(Long.parseLong(taskId));
                        this.main = true;
                        this.call = new EndCall() {
                            @Override
                            public void end(List<Map<String, Object>> mapList, Map<String, Object> lastMap) {
//                                activeMQService.sendMessage(null, taskEntitys.getController_name(), taskEntitys.getMethod_name(), null, lastMap);
                            }
                        };
                    }
                });
            }
        } catch (DataException e) {
//            map = exceptionResult(e);
        }
        return map;
    }

    private GrabTaskEntity getGrabTask(Long taskId) {
        GrabTaskEntity taskEntity = mapper.selectByPrimaryKey(taskId);
        if (taskEntity == null) {
            logger.warn("findById task:" + taskId + " result is null");
            return null;
        }
        return taskEntity;
    }

    public abstract class Job extends GrabBaseServiceImpl implements Runnable {
        public GrabTaskEntity taskEntitys;
        public boolean main;
        public String executeUrl;
        public EndCall call;

        abstract void init();

        @Override
        public void run() {
            init();
            List<Map<String, Object>> listDataMap = new ArrayList<>();
            taskEntitys.setDocument(super.getDocument((executeUrl == null) ? taskEntitys.getLink() : executeUrl));
            if (taskEntitys.getDocument() == null) {
                return;
            }
            Map<String, Object> map = filterNode(taskEntitys, taskEntitys.getDocument(), listDataMap, null, new ElementCall() {
                @Override
                public void Element(GrabFilterRuleJoinEntity ruleJoin, Map<String, Object> map, GrabTaskEntity taskEntity) {
                    if ("link".equalsIgnoreCase(ruleJoin.getObject_name()) && taskEntitys.getLayer() >= taskEntitys.getCurrentLayer()) {
                        ResultThrad resultThrad = new ResultThrad() {
                            @Override
                            void init() {
                                this.dataMap = map;
                                this.executeUrl = map.get(ruleJoin.getObject_name()).toString();
                                this.taskEntity = taskEntitys;
                                this.endCall = call;
                                this.listData = listDataMap;
                            }
                        };
                        taskExecutor.execute(resultThrad);
                    }
                }
            });
            if (taskEntitys.getLayer().compareTo(1) == 0) {
                for (Map<String, Object> map1 : listDataMap) {
                    call.end(listDataMap, map1);
                }
            }
        }
    }

    public Map<String, Object> filterNode(GrabTaskEntity taskEntitys, Document document, List<Map<String, Object>> listDataMap, Map<String, Object> dataMaps, ElementCall call) {
        GrabFilterRuleJoinEntity ruleJoinEntity = new GrabFilterRuleJoinEntity();
        ruleJoinEntity.setType(1);
        ruleJoinEntity.setTask_id(taskEntitys.getId());
        ruleJoinEntity.setLayer(taskEntitys.getCurrentLayer());
        List<GrabFilterRuleJoinEntity> list =  ruleJoinService.query(ruleJoinEntity, null,null);
        ruleJoinEntity.setType(2);
        List<GrabFilterRuleJoinEntity> list2 =  ruleJoinService.query(ruleJoinEntity, null,null);
        for (GrabFilterRuleJoinEntity ruleJoin : list) {
            GrabFilterRuleEntity filterRuleEntity = ruleJoin.getFilterRuleEntity();
            Elements elements = document.select(filterRuleEntity.getQuerySelector());
            ruleJoin.setElements(elements);
            removeSelectory(ruleJoin.getFilterRuleEntity().getRemove_querySelector(), elements);
            for (Element element : elements) {
                String value = null;
                Map<String, Object> map = null;
                if (dataMaps == null) {
                    map = new Hashtable<>();
                } else {
                    map = dataMaps;
                }
                for (GrabFilterRuleJoinEntity ruleJoin2 : list2) {
                    GrabFilterRuleEntity filterRuleEntity2 = ruleJoin2.getFilterRuleEntity();
                    if ("text".equalsIgnoreCase(filterRuleEntity2.getValue_rule())) {
                        value = element.select(filterRuleEntity2.getQuerySelector()).text();
                    } else if ("outerHtml".equalsIgnoreCase(filterRuleEntity2.getValue_rule())) {
                        value = element.select(filterRuleEntity2.getQuerySelector()).outerHtml();
                    } else if ("html".equalsIgnoreCase(filterRuleEntity2.getValue_rule())) {
                        value = element.select(filterRuleEntity2.getQuerySelector()).html();
                    } else {
                        value = element.select(filterRuleEntity2.getQuerySelector()).attr(filterRuleEntity2.getValue_rule());
                    }
                    map.put(ruleJoin2.getObject_name(), value);
                    if (call != null) {
                        call.Element(ruleJoin2, map, taskEntitys);
                    }
                }
                if (listDataMap != null) {
                    listDataMap.add(map);
                }
            }
        }
        return null;
    }

    private Elements removeSelectory(String querySelectory, Elements elements) {
        if (StringUtil.isNotEmpty(querySelectory)) {
            for (String qs : querySelectory.split(",")) {
                elements.removeClass(qs);
            }
        }
        return elements;
    }

    public abstract class ResultThrad extends GrabBaseServiceImpl implements Runnable {
        public Map<String, Object> dataMap;
        public String executeUrl;
        public GrabTaskEntity taskEntity;
        public EndCall endCall;
        public List<Map<String, Object>> listData;

        abstract void init();

        @Override
        public void run() {
            init();
            taskEntity.setCurrentLayer(2);
            filterNode(taskEntity, super.getDocument(executeUrl), null, dataMap, null);
            endCall.end(listData, dataMap);
        }
    }

    public abstract class ElementCall {
        public abstract void Element(GrabFilterRuleJoinEntity ruleJoin, Map<String, Object> map, GrabTaskEntity taskEntity);
    }

    public abstract class EndCall {
        public abstract void end(List<Map<String, Object>> mapList, Map<String, Object> lastMap);
    }
}