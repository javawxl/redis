package com.good.word.redis.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.good.word.redis.common.entity.BaseEntity;
import lombok.Data;

@Data
public class ApsprolinePlanSeq extends BaseEntity {

    @ExcelProperty(value = "车系", index = 0)
    private String breed;
    @ExcelIgnore
    private String carmodid;
    @ExcelIgnore
    private String carnobefthree;
    @ExcelIgnore
    private String carnoplanno;
    @ExcelIgnore
    private String changeitem;
    @ExcelIgnore
    private String client;
    @ExcelIgnore
    private String customerno;
    @ExcelIgnore
    private String district;
    @ExcelIgnore
    private String especialorder;
    @ExcelIgnore
    private String kind;
    @ExcelIgnore
    private String need;
    @ExcelProperty(value = "订单号", index = 1)
    private String ordno;
    @ExcelIgnore
    private String plantype;
    @ExcelProperty(value = "下限日期", index = 3)
    private String planenddate;
    @ExcelProperty(value = "上线日期", index = 2)
    private String planstartdate;
    @ExcelProperty(value = "工厂", index = 4)
    private String plant;
    @ExcelIgnore
    private String prolinesourceseqno;
    @ExcelProperty(value = "生产线", index = 7)
    private String prodline;
    @ExcelIgnore
    private String productlinesource;
    @ExcelIgnore
    private String projectnum;
    @ExcelProperty(value = "数量", index = 5)
    private String quantity;
    @ExcelIgnore
    private String remark1;
    @ExcelIgnore
    private String remark2;
    @ExcelIgnore
    private String remark3;
    @ExcelIgnore
    private String remark4;
    @ExcelIgnore
    private String schedulingscene;
    @ExcelProperty(value = "车序", index = 6)
    private int sequence;
    @ExcelIgnore
    private String shiftcode;
    @ExcelIgnore
    private String shiftname;
    @ExcelIgnore
    private String shopunit;
    @ExcelIgnore
    private String subordinatelinesign;
    @ExcelIgnore
    private String unitplanno;
    @ExcelIgnore
    private String worktimejp;
    @ExcelIgnore
    private String yieldly;
    @ExcelIgnore
    private String periodweek;
    @ExcelIgnore
    private String periodstartdate;
    @ExcelIgnore
    private String periodenddate;
    @ExcelIgnore
    private String otdstatus;
    @ExcelIgnore
    private String importid;
}
