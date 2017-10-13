package com.hl.money.utils;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hl.money.entity.Page;


/**
 * 类名称：翻页工具类
 * 内容摘要：计算总页数 计算数据库查询起始页
 * @version 1.0 2011-7-23
 */
public class PageUtil {

    /**
     * 获取到总页数
     * @param totalRecord 总条数
     * @param perPage 每页显示条数
     * @return int 总页数
     */
    public static int getTotalPageConut(int totalRecord, int perPage) {

        int totalPage = 0;
        // 判断总条数 每页显示条数取于 如果等于0则总条数除每页显示条数 否则加1
        if (totalRecord % perPage == 0) {
            totalPage = totalRecord / perPage;
        }
        else {
            totalPage = (totalRecord / perPage) + 1;
        }
        return totalPage;
    }

    /**
     * 取得分页开始条数
     * @param currentPage 当前页数
     * @param perPage 每页显示条数
     * @return int 每页开始条数
     */
    public static int getStartRecord(int currentPage, int perPage) {
        int startRecord = 0;
        currentPage--;
        // 如果当前为0则开始条数是0 否则每页开始条数为当前页减1乘每页显示条数
        if (currentPage == 0) {
            startRecord = 0;
        }
        else {
            // 设置当前页的初始条数
            startRecord = currentPage * perPage;
        }
        return startRecord;
    }
    
    /**
     * 取得分页查询的截止记录数
     * @param startRecord 分页查询的起始记录数
     * @param perPage 每页显示条数
     * @return int 分页查询的截止记录数
     */
    public static int getEndRecord(int startRecord, int perPage){
        
        return (startRecord + perPage);
    }
    
    /**
     * 取得json格式的分页对象
     * @param datas 当前页数据集
     * @param totalRecord 总条数
     * @param perpage 每页显示条数
     * @return
     */
    public static String getJsonData(List<?> datas, int totalRecord, int perpage){
        
        // 实例化分页对象
        Page page = new Page();
        page.setTotalRecord(totalRecord);
        page.setTotalPage(getTotalPageConut(totalRecord, perpage));
        page.setDataList(JSONArray.toJSONString(datas));
        
        return JSONObject.toJSONString(page);
    }

    /**
     * 分页工具条
     * @param currentPage 当前页码
     * @param perpage 每页显示条数
     * @param total 数据总条数
     * @param url 分页请求地址
     * @return String 分页html字符串
     * @author lawliet
     */
    public static String getPageHtml(int currentPage, int perpage, int total, String url) {
        
        // 计算总页数
        int pageCount = getPageCount(perpage, total);

        // 页数大于1
        if (pageCount > 1) {

            // 当前页大于1
            String pagePrefix = "<a href=\"" + url;
            
            // 分页结果Html
            StringBuilder builder = new StringBuilder();
            
            if (currentPage > 1) {
                // 上一页
                builder.append(pagePrefix + "&page=" + (currentPage - 1) + "\" class=\"pageBtn\">&lt;&lt; 上一页</a>");
            }

            if (currentPage == 4) {
                builder.append(pagePrefix + "&page=1\">1</a>");
            }
            if (currentPage >= 5) {
                builder.append(pagePrefix + "&page=1\">1</a>");
                builder.append("<strong style='background: none;'>...</strong>");
            }

            for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {

                int temp = 2;
                
                if (currentPage == 1) {
                    temp = 4;
                } else if (currentPage == 2) {
                    temp = 3;
                }
                
                if ((pageIndex >= (currentPage - temp) && pageIndex <= (currentPage + temp))) {
                    
                    if (currentPage == pageIndex) {
                        builder.append("<strong>" + currentPage + "</strong>");
                    } else {
                        builder.append(pagePrefix + "&page=" + pageIndex + "\">" + pageIndex + "</a>");
                    }
                }
            }

            if (pageCount >= 7 && pageCount - currentPage >= 4 ) {
                builder.append("<strong style='background: none;'>...</strong>");
                builder.append(pagePrefix + "&page=" + pageCount + "\">" + pageCount + "</a>");
            }
            
            if (((currentPage == (pageCount - 3)) && pageCount > 5 ) || (pageCount == 6 && currentPage <= 2 ) ) {
                builder.append(pagePrefix + "&page=" + pageCount + "\">" + pageCount + "</a>");
            }

            // 当前页小于总页数
            if (currentPage < pageCount) {
                // 下一页
                builder.append(pagePrefix + "&page=" + (currentPage + 1) + "\" class=\"pageBtn\">下一页 &gt;&gt;</a>");
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 根据每页显示条数及数据总条数取得总页数
     * @param perpage 每页显示条数
     * @param total 数据总条数
     * @return int 总页数
     * @author lawliet
     */
    public static int getPageCount(int perpage, int total) {
        return (total % perpage == 0) ? total / perpage : (total / perpage + 1);
    }
    
    
    /**
     * 分页工具条
     * @param currentPage 当前页码
     * @param perpage 每页显示条数
     * @param total 数据总条数
     * @param url 分页请求地址
     * @return String 分页html字符串
     * @author lawliet
     */
    public static String getBackPageHtml(int currentPage, int perpage, int total, String url) {
        
        // 计算总页数
        int pageCount = getPageCount(perpage, total);
        
        // 页数大于1
        if (pageCount > 1) {
            
            
            // 当前页大于1
            String pagePrefix = "<a href=\"" + url;
            
            // 分页结果Html
            StringBuilder builder = new StringBuilder("<a title=\"First Page\" href=\"" + url + "&page=1\">« First</a>");
            
            if (currentPage > 1) {
                // 上一页
                builder.append(pagePrefix + "&page=" + (currentPage - 1) + "\" title=\"Previous Page\" class=\"pageBtn\">« Previous</a>");
            }
            
            if (currentPage == 4) {
                builder.append(pagePrefix + "&page=1\" class=\"number\" >1</a>");
            }
            if (currentPage >= 5) {
                builder.append(pagePrefix + "&page=1\" class=\"number\" >1</a>");
                builder.append("<strong style='background: none;'> ～ </strong>");
            }
            
            for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {
                
                int temp = 2;
                
                if (currentPage == 1) {
                    temp = 4;
                } else if (currentPage == 2) {
                    temp = 3;
                }
                
                if ((pageIndex >= (currentPage - temp) && pageIndex <= (currentPage + temp))) {
                    
                    if (currentPage == pageIndex) {
                        builder.append("<a class=\"number current\" href=\"" + url + "\">" + currentPage + "</a>");
                    } else {
                        builder.append(pagePrefix + "&page=" + pageIndex + "\" class=\"number\" >" + pageIndex + "</a>");
                    }
                }
            }
            
            if (pageCount >= 7 && pageCount - currentPage >= 4 ) {
                builder.append("<strong style='background: none;'> ～ </strong>");
                builder.append(pagePrefix + "&page=" + pageCount + "\" class=\"number\" >" + pageCount + "</a>");
            }
            
            if (((currentPage == (pageCount - 3)) && pageCount > 5 ) || (pageCount == 6 && currentPage <= 2 ) ) {
                builder.append(pagePrefix + "&page=" + pageCount + "\" class=\"number\" >" + pageCount + "</a>");
            }
            
            // 当前页小于总页数
            if (currentPage < pageCount) {
                // 下一页
                builder.append(pagePrefix + "&page=" + (currentPage + 1) + "\" title=\"Next Page\" class=\"pageBtn\">Next »</a>");
            }
            
            builder.append("<a title=\"Last Page\" href=\"" + url + "&page="+ pageCount +"\">Last »</a>");
            
            return builder.toString();
        }
        return null;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    /**
     * 分页工具条（新样式）
     * @param currentPage 当前页码
     * @param perpage 每页显示条数
     * @param total 数据总条数
     * @param url 分页请求地址
     * @return String 分页html字符串
     * @author lawliet
     */
    public static String getPageToolHtml(int currentPage, int perpage, int total, String url) {
        
        // 计算总页数
        int pageCount = getPageCount(perpage, total);

        // 页数大于1
        if (pageCount > 1) {

            // 当前页大于1
            String pagePrefix = "<a href=\"" + url;
            
            // 分页结果Html
            StringBuilder builder = new StringBuilder();
            
            if (currentPage > 1) {
                // 上一页
                builder.append(pagePrefix + "&page=" + (currentPage - 1) + "\" class=\"pageBtn\">&lt;&lt; 上一页</a>");
            }

            if (currentPage == 4) {
                builder.append(pagePrefix + "&page=1\">1</a>");
            }
            if (currentPage >= 5) {
                builder.append(pagePrefix + "&page=1\">1</a>");
                builder.append("<strong style='background: none;color: #666;'>...</strong>");
            }

            for (int pageIndex = 1; pageIndex <= pageCount; pageIndex++) {

                int temp = 2;
                
                if (currentPage == 1) {
                    temp = 4;
                } else if (currentPage == 2) {
                    temp = 3;
                }
                
                if ((pageIndex >= (currentPage - temp) && pageIndex <= (currentPage + temp))) {
                    
                    if (currentPage == pageIndex) {
                        builder.append("<strong>" + currentPage + "</strong>");
                    } else {
                        builder.append(pagePrefix + "&page=" + pageIndex + "\">" + pageIndex + "</a>");
                    }
                }
            }

            if (pageCount >= 7 && pageCount - currentPage >= 4 ) {
                builder.append("<strong style='background: none;color: #666;'>...</strong>");
                builder.append(pagePrefix + "&page=" + pageCount + "\">" + pageCount + "</a>");
            }
            
            if (((currentPage == (pageCount - 3)) && pageCount > 5 ) || (pageCount == 6 && currentPage <= 2 ) ) {
                builder.append(pagePrefix + "&page=" + pageCount + "\">" + pageCount + "</a>");
            }

            // 当前页小于总页数
            if (currentPage < pageCount) {
                // 下一页
                builder.append(pagePrefix + "&page=" + (currentPage + 1) + "\" class=\"pageBtn\">下一页 &gt;&gt;</a>");
            }
            return builder.toString();
        }
        return null;
    }
}
