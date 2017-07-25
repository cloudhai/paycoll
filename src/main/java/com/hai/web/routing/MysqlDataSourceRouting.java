package com.hai.web.routing;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * mysql 数据源动态切换
 * Created by cloud on 2017/6/5.
 */
public class MysqlDataSourceRouting extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return RoutingToken.getToken();
    }
}
