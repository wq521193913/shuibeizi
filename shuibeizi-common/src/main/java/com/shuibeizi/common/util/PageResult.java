package com.shuibeizi.common.util;

/**
 * 封装JSON格式的分页返回值
 * @author Administrator
 * @date 2016/9/20 0020 17:21
*/
public class PageResult {

    /**
     * 总条数
     */
    private Integer total;

    /**
     * 页数
     */
    private Integer pageNumber = 1;

    /**
     * 页面数据条数
     */
    private Integer pageSize = 10;
    /**
     * 数据ID(sql server分页使用)
     */
    private Integer rowId;

    /**
     * 分页数据
     */
    private Object rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(Object rows) {
        this.rows = rows;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    /**
     * 获取分页数据信息
     * @param total 总数据条数
     * @param data 分页数据
     * @return
     * @author wanqing
     * @date 2016/9/20 0020 17:41
    */
    public static PageResult getPageResult(Integer total, Object pageNumber, Object pageSize, Object data){
        PageResult pageResult = new PageResult();
        if(null == pageNumber){
            pageNumber = pageResult.getPageNumber();
        }
        if(null == pageSize){
            pageSize = pageResult.getPageSize();
        }
        pageResult.setPageNumber(Integer.valueOf(pageNumber.toString()));
        pageResult.setPageSize(Integer.valueOf(pageSize.toString()));
        pageResult.setTotal( null != total? total : 0);
        pageResult.setRows(data);
        return pageResult;
    }

    /**
     * 获取sql server分页数据信息
     * @param rowId 最大数据行数
     * @param data 分页数据
     * @return
     * @author wanqing
     * @date 2016/9/20 0020 17:41
     */
    public static PageResult getPageResultSql(Integer rowId, Object data){
        PageResult pageResult = new PageResult();
        pageResult.setRowId( null != rowId? rowId : 0);
        pageResult.setRows(data);
        return pageResult;
    }

    /**
     * 获取分页数据信息
     * @param total
     * @param data
     * @return
     */
    public static PageResult getPageResult(int total, Object data){
        return PageResult.getPageResult(total,null, null, data);
    }

    /**
     * 获取Result类型的分页数据信息
     * @param total 总数据条数
     * @param data 数据
     * @return
     * @author wanqing
     * @date 2016/9/20 0020 17:43
    */
    public static Result getJsonPageResult(int total, int pageNumber, int pageSize, Object data){
        Result result = new Result();
        result.setData(PageResult.getPageResult(total, pageNumber, pageSize, data));
        return result;
    }

    public static Result getJsonPageResult(PageResult pageResult){
        Result result = new Result();
        result.setData(pageResult);
        return result;
    }

}
