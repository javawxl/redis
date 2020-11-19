package com.good.word.redis.interceptor;

import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
@Component
public class MapResultInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof DefaultResultSetHandler) {
            DefaultResultSetHandler defaultResultSetHandler = (DefaultResultSetHandler) target;
            Field f = ReflectionUtils.findField(DefaultResultSetHandler.class, "mappedStatement");
            f.setAccessible(true);
            MappedStatement ms = (MappedStatement) ReflectionUtils.getField(f, defaultResultSetHandler);
            String id = ms.getId();
            int pos = id.lastIndexOf('.');
            String mapperClassName = id.substring(0, pos);
            String methodName = id.substring(pos + 1, id.length());
            Method[] declaredMethods = Class.forName(mapperClassName).getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (methodName.equals(method.getName())) {
                    if (method.isAnnotationPresent(MappedColumn.class)) {
                        Statement statement = (Statement) invocation.getArgs()[0];
                        MappedColumn annotation = method.getAnnotation(MappedColumn.class);
                        return this.doHandleResultSet(statement.getResultSet(), annotation.key(), annotation.value());
                    }
                }
            }

        }
        return invocation.proceed();
    }

    private Object doHandleResultSet(ResultSet rs, String key, String value) {
        if (rs != null) {
            Map<Object, Object> map = new HashMap<>();
            try {
                while (rs.next()) {
                    Object k = rs.getObject(key);
                    Object v = rs.getObject(value);
                    map.put(k, v);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeResultSet(rs);
            }
            return new ArrayList<Map<Object, Object>>(){{add(map);}};
        }
        return null;
    }

    private void closeResultSet(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("debugger");
    }
}
