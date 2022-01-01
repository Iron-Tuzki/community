package com.lanshu.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDto {
    //按钮标识
    private boolean hasFirstPage;
    private boolean hasEndPage;
    private boolean hasPrePage;
    private boolean hasNextPage;

    private Integer curPage;    //当前页
    private Integer endPage;    //最后一页
    private List<Integer> pages = new ArrayList<>();    //页码组合
    //问题列表封装到paginationDto中
    private List<QuestionDto> questions;

    public void setPageConfiguration(Integer curPage) {

        this.curPage = curPage;

        //计算页码组合，前三页后三页，共七页
        pages.add(curPage);
        for (int i = 1;i<=3;i++){
            //以当前页为基准，向前三页头插法插入pages中
            if (curPage - i > 0)
                pages.add(0,curPage-i);
            //向后三页，List 的 add 方法默认尾插法
            if (curPage + i <= endPage)
                pages.add(curPage+i);
        }

        if (curPage == 1) { hasPrePage = false;} else {hasPrePage = true;}
        if (curPage == endPage) { hasNextPage = false ;} else { hasNextPage = true;}
        if (pages.contains(1)) { hasFirstPage = false;} else { hasFirstPage = true;}
        if (pages.contains(endPage)) { hasEndPage = false;} else { hasEndPage = true;}
    }

    /**
     * 根据问题总数和每页问题数得出总页数
     * @param totalQuestions
     * @param size
     * @return
     */
    public Integer getTotalPages(Integer totalQuestions, Integer size){
        //根据问题总数，得出总页数
        if (totalQuestions % size == 0){
            endPage = totalQuestions/size;
        } else {
            endPage = totalQuestions/size+1;
        }
        return endPage;
    }
}
