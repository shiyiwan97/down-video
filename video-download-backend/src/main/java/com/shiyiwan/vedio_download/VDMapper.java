package com.shiyiwan.vedio_download;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class VDMapper {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSourceProperties dataSourceProperties;

    public int getDownloadState() {
        String sql = "SELECT state FROM STATEMENT WHERE id = 1";
        List<Map<String, Object>> returnMap = jdbcTemplate.queryForList(sql);
        return (int)returnMap.get(0).get("state");
    }

    public void setDownloadState(int state){
        String sql = "UPDATE STATEMENT SET state = " + state + " WHERE id = 1";
        jdbcTemplate.update(sql);
    }
}
