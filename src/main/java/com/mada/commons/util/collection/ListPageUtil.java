package com.mada.commons.util.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 分页工具类
 * Created by madali on 2017/12/5.
 */
public class ListPageUtil<T> {

    //原集合
    private List<T> data;

    //上一页
    private int previousPage;

    //当前页
    private int nowPage;

    //下一页
    private int nextPage;

    //每页条数
    private int pageSize;

    //总页数
    private int totalPage;

    //总数据条数
    private int totalCount;

    public ListPageUtil(List<T> data, int nowPage, int pageSize) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("data must be not empty!");
        }

        this.data = data;
        this.pageSize = pageSize;
        this.nowPage = nowPage;
        this.totalCount = data.size();
        this.totalPage = (totalCount + pageSize - 1) / pageSize;
        this.previousPage = nowPage - 1 > 1 ? nowPage - 1 : 1;
        this.nextPage = nowPage >= totalPage ? totalPage : nowPage + 1;
    }

    /**
     * 得到分页后的数据
     *
     * @return
     */
    public List<T> getPagedList() {
        int fromIndex = (nowPage - 1) * pageSize;
        if (fromIndex >= data.size()) {
            return Collections.emptyList();
        }
        if (fromIndex < 0) {
            return Collections.emptyList();
        }
        int toIndex = nowPage * pageSize;
        if (toIndex >= data.size()) {
            toIndex = data.size();
        }
        return data.subList(fromIndex, toIndex);
    }

    public int getPageSize() {
        return pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public int getPreviousPage() {
        return previousPage;
    }

    public int getNowPage() {
        return nowPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }

        ListPageUtil<Integer> listPageUtil = new ListPageUtil<>(list, 2, 5);
        List<Integer> pagedList = listPageUtil.getPagedList();
        System.out.println(pagedList);
    }
}