package com.good.word.redis.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.good.word.redis.entity.ApsprolinePlanSeq;
import com.good.word.redis.mapper.IUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/io")
public class IOExcelController {

    @Autowired
    private IUserMapper userMapper;

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        List<ApsprolinePlanSeq> list = userMapper.list();
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        list.addAll(list);
        //fixme Invalid row number (1048576) outside allowable range (0..1048575)
        String filename = new String("计划明细-导出".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        ExcelWriter excelWriter = null;
        try {
            List<String> excludeFieldNames = new ArrayList<>();
            excludeFieldNames.add("id");
            excludeFieldNames.add("tenantId");
            excludeFieldNames.add("dr");
            excludeFieldNames.add("ts");
            excelWriter = EasyExcel.write(outputStream, ApsprolinePlanSeq.class)
                    .excludeColumnFiledNames(excludeFieldNames).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(list, writeSheet);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }

    }

    @GetMapping("/singleThreadQuery")
    public void singleThreadQuery() {
        StopWatch stopWatch = new StopWatch("singleThreadQuery");
        stopWatch.start("查询全部");
        List<ApsprolinePlanSeq> list = userMapper.list();
        System.out.println(list.size());
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
