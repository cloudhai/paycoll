package com.hai.web.routing;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by cloud on 2017/6/5.
 */

@Intercepts({
        @Signature(type= Executor.class,method = "update",args={MappedStatement.class, Object.class}),
        @Signature(type= Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})
})
public class DataSourceInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement) objects[0];
        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replace("\\t\\n\\r","");
        System.out.println("sql:"+sql);
        if(ms.getResource().contains("bill")){
            RoutingToken.setToken(RoutingToken.TOKEN_XF);
        }else{
            RoutingToken.setToken(RoutingToken.TOKEN_KP);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if(target instanceof Executor){
            return Plugin.wrap(target,this);
        }else{
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public void readAndWriteSplit(MappedStatement ms){
        //读方法
        if(ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
            //!selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
            if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                // dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
            } else {
//                BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
//                String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
//                if(sql.matches(REGEX)) {
//                    dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
//                } else {
//                    dynamicDataSourceGlobal = DynamicDataSourceGlobal.READ;
//                }
            }
        }
    }
}
