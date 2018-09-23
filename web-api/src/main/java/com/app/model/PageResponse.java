package com.app.model;

public class PageResponse extends BaseResponse {

    private int total=0;
    private int pageSize=0;
    private int currentPage=0;
    private int totalPages=0;
    private String pagingMsg="";
    public boolean moreItemExist=false;

    // ==== Getters and Setters ====
    public int getTotal() {return total;}
    public void setTotal(int total) {
        this.total = total;
        this.pageSize = total;
        this.currentPage = 1;
        this.totalPages = 1;
    }

    public int getPageSize() {return pageSize;}
    public void setPageSize(int pageSize) {this.pageSize = pageSize;}

    public int getCurrentPage() {return currentPage;}
    public void setCurrentPage(int currentPage) {this.currentPage = currentPage;}

    public int getTotalPages() {return totalPages;}
    public void setTotalPages(int totalPages) {this.totalPages = totalPages;}

    public String getPagingMsg() {return pagingMsg;}
    public void setPagingMsg(String pagingMsg) {this.pagingMsg = pagingMsg;}

    public void setPageStats(int total, int pageSize, int currentPage, String pagingMsg){
        if (pageSize>0){
            this.total = total;
            this.pageSize = pageSize;
            this.currentPage = currentPage;
            this.totalPages =  (total/pageSize) +  ( (total % pageSize==0) ?0:1) ;
            this.pagingMsg = pagingMsg;
        }
    }


}