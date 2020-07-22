package com.dh.common.util;

import java.util.List;

public class PageUtil {

    /**
     * 返回分页后的list
     *
     * @param list
     * @param pageSize
     * @param currentPage
     * @return
     */
    public static <T> List<T> pageBySubList(List<T> list, int pageSize, int currentPage) {
        int totalCount = list.size();
        int pageCount = 0;
        List<T> subList;
        int m = totalCount % pageSize;
        if (m > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }
        if (m == 0) {
            subList = list.subList((currentPage - 1) * pageSize, pageSize * (currentPage));
        } else {
            if (currentPage == pageCount) {
                subList = list.subList((currentPage - 1) * pageSize, totalCount);
            } else {
                subList = list.subList((currentPage - 1) * pageSize, pageSize * (currentPage));
            }
        }
        return subList;
    }

}
