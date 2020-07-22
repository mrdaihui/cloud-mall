package com.dh.common.models;


import com.dh.common.context.RequestContext;
import com.dh.common.exception.RException;
import com.dh.common.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回数据
 */
public class R extends HashMap<String, Object> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final long serialVersionUID = 1L;

    public R() {
        put("code", CodeDefined.SUCCESS.getValue());
        put("msg", CodeDefined.SUCCESS.getDesc());
        put("requestId", RequestContext.getContext().getRequestId());
    }


    public static R error(String code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }


    public static R error() {
        return error(CodeDefined.ERROR.getValue(), CodeDefined.ERROR.getDesc());
    }

    public static R error(String msg) {
        return error(CodeDefined.ERROR.getValue(), msg);
    }

    public static R error(CodeDefined codeDefined) {
        return error(codeDefined.getValue(), codeDefined.getDesc());
    }

    public static R error(RException ex) {
        return error(ex.getCode(), ex.getMsg());
    }


    public static R ok() {
        return new R();
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public R data(Object value) {
        super.put("data", value);
        return this;
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * list转为page，返回分页数据
     *
     * @param list
     * @param pageSize
     * @param currentPage
     * @return
     */
    public R list2page(List<?> list, Integer pageSize, Integer currentPage) {
        // page和limit为空时，给定默认值
        if (pageSize == null) {
            pageSize = 10;
        }
        if (currentPage == null) {
            currentPage = 1;
        }

        if (list.size() <= 0) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("current", currentPage);
            resultMap.put("size", pageSize);
            resultMap.put("pages", 1);
            resultMap.put("total", 0);
            resultMap.put("records", new ArrayList<>());
            super.put("data", resultMap);
        } else {
            //分页后的list
            List<?> pageList = PageUtil.pageBySubList(list, pageSize, currentPage);
            //总条数
            int totalCount = list.size();
            //总页数
            int pageCount = 0;

            int m = totalCount % pageSize;
            if (m > 0) {
                pageCount = totalCount / pageSize + 1;
            } else {
                pageCount = totalCount / pageSize;
            }

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("current", currentPage);
            resultMap.put("size", pageSize);
            resultMap.put("pages", pageCount);
            resultMap.put("total", totalCount);
            resultMap.put("records", pageList);
            super.put("data", resultMap);
        }
        return this;
    }




}
