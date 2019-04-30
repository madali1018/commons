package com.mada.commons.utils.collection;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List的分页工具类
 * <p>
 * Created by madali on 2017/12/5.
 */
@Data
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

    // 分页构造器
    public ListPageUtil(List<T> data, int nowPage, int pageSize) {
        if (CollectionUtil.isEmpty(data)) {
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

    // 得到分页后的数据
    public List<T> getPagedList() {
        int fromIndex = (nowPage - 1) * pageSize;
        if (!(fromIndex >= 0 && fromIndex < data.size())) {
            return Collections.emptyList();
        }

        int toIndex = nowPage * pageSize;
        toIndex = toIndex >= data.size() ? data.size() : toIndex;

        return data.subList(fromIndex, toIndex);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }

        ListPageUtil<Integer> listPageUtil = new ListPageUtil<>(list, 1, 5);
        List<Integer> pagedList = listPageUtil.getPagedList();
        System.out.println(pagedList);
    }
}