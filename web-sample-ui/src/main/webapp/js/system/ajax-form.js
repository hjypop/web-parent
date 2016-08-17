/**
 * 
 */


var aForm = {


    /**
     * 实例化分页html部分
     *
     * @param formId formId 一般写定为 ‘table-form’ 如：示例ajaxFormExample.jsp中 < div id="table-form" class="dataTables_wrapper" >
     * @param pageNum
     * @param startRow
     * @param endRow
     * @param pages
     * @param total
     * @param isFirstPage true or false 是否是第一页
     * @param prePage
     * @param isLastPage true or false 是否是最后一页
     * @param nextPage
     */
    init : function(formId  , pageNum , startRow , endRow , pages , total , isFirstPage , prePage , isLastPage , nextPage){
        var html_ =
            '<div id="a1" class="dataTables_info" >'
                +'当前第' + pageNum +'页|正在显示 ' + startRow +' 到 ' + endRow + ' 条|共 <span style="color:#ff0000">' + pages + ' </span>页 ' + total + ' 条记录'
            +'</div>'
            +'<div id="a2" class="dataTables_paginate paging_full_numbers" >'
                +'<span id="first-page" class="first paginate_button paginate_button_disabled" onclick="aForm.formPaging(1)">'  // TODO 这里写数字 1 是否会有潜在异常
                    +'首页'
                +'</span>';

        if(!isFirstPage){ // 如果是第一页，则只显示下一页、尾页
            html_ += '<span id="previous-page" class="previous paginate_button paginate_button_disabled" onclick="aForm.formPaging(\'' + prePage + '\')">' // TODO 这里是否会有潜在异常
                                +'上一页'
                            +'</span>';
        }
        html_ += '<span id="dynamic-page-num"></span>';
        if(!isLastPage){ // 如果是末页，则只显示上一页
            html_ += '<span id="next-page" class="next paginate_button"   onclick="aForm.formPaging(\'' + nextPage + '\')">'  // TODO 这里是否会有潜在异常
                                +'下一页'
                        +'</span>'
                        +'<span id="last-page" class="last paginate_button" onclick="aForm.formPaging(\'' + pages +'\')" >'  // TODO 这里是否会有潜在异常
                            +'末页'
                        +'</span>';
        }
        html_ += '</div>';
        $("#" + formId).append(html_);
        aForm.oneToSeven(pageNum , pages);


    },

    /**
     * 上一页与下一页中间的7个数字的切换与颜色变换
     * @param pageNum
     * @param pages
     */
    oneToSeven : function(pageNum , pages ){
        var curpage = parseInt(pageNum);                                   // parseInt('${pageList.pageNum}');
        var pclassAc = 'paginate_active';       // 当前按钮样式类
        var pclassBt = 'paginate_button';       // 非当前样式类
        var pageCount =  parseInt(pages) ;                 //parseInt('${pageList.pages}');
        var html_ = '';
        if(pageCount > 0 && pageCount < 8){
            for(var i =1 ; i < pageCount+1 ; i++){
                if(curpage == i){
                    html_ += '<span class="' + pclassAc + '">' + i + '</span>';
                }else{
                    html_ += '<span class="' + pclassBt + '"  onclick="aForm.formPaging(\'' + i +'\')" >' + i + '</span>';
                }
            }
        }else if(pageCount > 7){
            if(curpage < 5){
                for(var i =1 ; i < 8 ; i++){
                    if(curpage == i){
                        html_ += '<span class="' + pclassAc + '">' + i + '</span>';
                    }else{
                        html_ += '<span class="' + pclassBt + '"  onclick="aForm.formPaging(\'' + i +'\')" >' + i + '</span>';
                    }
                }
            }else{
                var arr = new Array();
                if((pageCount - curpage) < 7 ){ // 最后7页
                    for(var i = 0 ; i < 7 ; i ++){
                        arr[i] = pageCount - 6 + i;
                    }
                }else{
                    arr[0] = curpage - 3;
                    arr[1] = curpage - 2;
                    arr[2] = curpage - 1;
                    arr[3] = curpage;
                    arr[4] = curpage + 1;
                    arr[5] = curpage + 2;
                    arr[6] = curpage + 3;
                }
                for(var i = 0 ; i < arr.length ; i ++){
                    if(curpage == arr[i]){
                        html_ += '<span class="' + pclassAc + '">' + arr[i] + '</span>';
                    }else{
                        html_ += '<span class="' + pclassBt + '"  onclick="aForm.formPaging(\'' + arr[i] +'\')" >' + arr[i] + '</span>';
                    }
                }
            }

        }
        $("#dynamic-page-num").append(html_);
    },

    formPaging : function(pn){
        var ps = $("#select-page-size").val();
        var actions = '${bpath}' + '${pageUrl}&pageNum=' + pn +'&pageSize=' + ps;
        $('#page-form').attr("action", actions);
        $("#page-form").submit();
    }


}













