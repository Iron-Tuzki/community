package com.lanshu.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDto {
    //按钮标识
    private boolean hasFirstPage;
    private boolean hasPrePage;
    private boolean hasNextPage;
    private boolean hasEndPage;

    private Integer curPage;    //当前页
    private Integer endPage;    //最后一页
    private List<Integer> pages = new ArrayList<>();    //页码组合
    private List<QuestionDto> questions;    //问题列表封装到paginationDto中

    public void setPageConfiguration(Integer curPage) {

        this.curPage = curPage;

        //计算页码组合，前三页后三页，共七页
        pages.add(curPage);
        for (int i = 1;i<=3;i++){
            //以当前页为基准，向前三页头插法插入pages中
            if (curPage - i > 0) pages.add(0,curPage-i);
            //向后三页，List 的 add 方法默认尾插法
            if (curPage + i <= endPage) pages.add(curPage+i);
        }

        hasFirstPage = pages.contains(1) ? false : true;
        hasPrePage   = curPage == 1 ? false : true;
        hasNextPage  = curPage == endPage ? false : true;
        hasEndPage   = pages.contains(endPage) ? false : true;
    }

    /**
     * 根据问题总数和每页问题数得出总页数
     * @param totalQuestions
     * @param size
     * @return
     */
    public Integer getTotalPages(Integer totalQuestions, Integer size){
        //根据问题总数，得出总页数
        endPage = totalQuestions % size == 0 ? totalQuestions/size : totalQuestions/size+1;
        return endPage;
    }
}
