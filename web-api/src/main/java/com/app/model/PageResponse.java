package com.app.model;

public class PageResponse extends BaseResponse {

    private Long total=0L;
    private Long pageSize=0L;
    private Long currentPage=0L;
    private Long totalPages=0L;
    private String pagingMsg="";
    public boolean moreItemExist=false;

    // ==== Getters and Setters ====
    public Long getTotal() {return total;}
    public void setTotal(Long total) {
        this.total = total;
        this.pageSize = total;
        this.currentPage = 1L;
        this.totalPages = 1L;
    }

    public Long getPageSize() {return pageSize;}
    public void setPageSize(Long pageSize) {this.pageSize = pageSize;}

    public Long getCurrentPage() {return currentPage;}
    public void setCurrentPage(Long currentPage) {this.currentPage = currentPage;}

    public Long getTotalPages() {return totalPages;}
    public void setTotalPages(Long totalPages) {this.totalPages = totalPages;}

    public String getPagingMsg() {return pagingMsg;}
    public void setPagingMsg(String pagingMsg) {this.pagingMsg = pagingMsg;}

    public void setPageStats(Long total, Long pageSize, Long currentPage, String pagingMsg){
        if (pageSize>0){
            this.total = total;
            this.pageSize = pageSize;
            this.currentPage = currentPage;
            this.totalPages = (Long)(total/pageSize) +  ( (total % pageSize==0) ?0:1) ;
            this.pagingMsg = pagingMsg;
        }
    }


}