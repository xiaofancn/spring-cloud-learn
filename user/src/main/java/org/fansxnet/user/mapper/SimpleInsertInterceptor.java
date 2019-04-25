//package org.fansxnet.user.mapper;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.ibatis.binding.MapperMethod;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
//import org.apache.ibatis.mapping.*;
//import org.apache.ibatis.plugin.*;
//import org.fansxnet.common.PojoConfig;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.*;
//
///**
// * @Description: <br>
// * @Project: hades <br>
// * @CreateDate: Created in 2019/4/5 09:39 <br>
// * @Author: <a href="xiaofancn@qq.com">abc</a>
// * <p>
// * see
// * https://www.programcreek.com/java-api-examples/?api=org.apache.ibatis.mapping.ParameterMap
// */
//@Slf4j
////@Component
//@Intercepts({
//        @Signature(
//                type = Executor.class,
//                method = "update",
//                args = {MappedStatement.class, Object.class}),
//})
//public class SimpleInsertInterceptor implements Interceptor {
//
//    @Value("${mybatis.pojo-config-locations:'classpath:json/'}")
//    private String dir;
//
//    static final Map<String, PojoConfig> pojoConfigs = new HashMap<>();
//
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//
//        if (!mappedStatement.getSqlCommandType().equals(SqlCommandType.INSERT) && !mappedStatement.getSqlCommandType().equals(SqlCommandType.UPDATE)) {
//            return invocation.proceed();
//        }
//
//        final BoundSql boundSql  = mappedStatement.getBoundSql(invocation.getArgs()[1]);
//        if(!"Common".equals(boundSql.getSql())){
//            return invocation.proceed();
//        }
//
//        MapperMethod.ParamMap<Object> paramMap = (MapperMethod.ParamMap<Object>) invocation.getArgs()[1];
//        Object data = paramMap.get("entity");
//        Model model = data.getClass().getAnnotation(Model.class);
//        if (!"Common".equals(boundSql.getSql())) {
//            return invocation.proceed();
//        }
//
//        if (mappedStatement.getSqlCommandType().equals(SqlCommandType.INSERT)) {
//            PojoConfig config = load(model.json());
//            StringBuilder sql = new StringBuilder(String.format("insert into %s ", config.getTable()));
//            List<String> prop = new ArrayList<>(config.getPropColumn().size());
//            List<String> colu = new ArrayList<>(config.getPropColumn().size());
//            List<ParameterMapping> ppps = new ArrayList<>(config.getPropColumn().size());
//            config.getPropColumn().forEach((k, v) -> {
//                try {
//                    Object val = invoke(data, k);
//                    if (invoke(data, k) != null) {
//                        prop.add("?");
//                        colu.add(v.contains("`") ? v : "`" + v + "`");
//                        ppps.add(
//                                new ParameterMapping.Builder(mappedStatement.getConfiguration(),"entity."+k, mappedStatement.getConfiguration().getTypeHandlerRegistry().getTypeHandler(val.getClass())).build()
//                        );
//                    }
//                } catch (Exception e) {
//                    log.info("处理插入语句错误{}", e.getLocalizedMessage());
//                    e.printStackTrace();
//                }
//            });
//            sql.append(String.format("(%s) values (%s)", StringUtils.join(colu, ","), StringUtils.join(prop, ",")));
//            BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql.toString(), ppps, boundSql.getParameterObject());
//            log.info("new sql: {}", newBoundSql.getSql());
//            MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql), config.getPropKey());
//            invocation.getArgs()[0] = newMs;
//        } else if (mappedStatement.getSqlCommandType().equals(SqlCommandType.UPDATE)) {
//            PojoConfig config = load(model.json());
//            StringBuilder sql = new StringBuilder(String.format("update %s set ", config.getTable()));
//            boolean updateForParams = paramMap.containsKey("params");
//            List<String> colu = new ArrayList<>(config.getPropColumn().size());
//            List<ParameterMapping> ppps = new ArrayList<>(config.getPropColumn().size());
//            config.getPropColumn().forEach((k, v) -> {
//                try {
//                    Object val = invoke(data, k);
//                    if (invoke(data, k) != null) {
//                        colu.add(v.contains("`") ? v : "`" + v + "`=?");
//                        ppps.add(builderParameterMapping(mappedStatement, "entity."+k, val));
//                    }else{
//
//                    }
//                } catch (Exception e) {
//                    log.info("处理插入语句错误{}", e.getLocalizedMessage());
//                    e.printStackTrace();
//                }
//            });
//
//            StringBuilder where = new StringBuilder(" where 1=1");
//            if (config.getPropKey() != null && config.getPropKey().length > 0) {
//                if(!updateForParams){
//                    String k = config.getPropKey()[0];
//                    where.append(String.format(" and %s=? ", config.getPropColumn().get(k)));
//                    ppps.add(builderParameterMapping(mappedStatement,  "entity."+k, invoke(data, k)));
//                }
//            }
//            sql.append(String.format(" %s ", StringUtils.join(colu, ",")));
//            sql.append(where);
//            BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql.toString(), ppps, boundSql.getParameterObject());
//            log.info("new sql: {}", newBoundSql.getSql());
//            MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql), config.getPropKey());
//            invocation.getArgs()[0] = newMs;
//        }
//        return invocation.proceed();
//    }
//
//
//    private ParameterMapping builderParameterMapping(MappedStatement mappedStatement, String k, Object val) {
//        return new ParameterMapping.Builder(mappedStatement.getConfiguration(), k, mappedStatement.getConfiguration().getTypeHandlerRegistry().getTypeHandler(val.getClass())).build();
//    }
//
//    private synchronized PojoConfig load(String json) {
//        PojoConfig config = pojoConfigs.get(json);
//        if (config == null) {
//            try {
//                String path = dir + "/" + (json.endsWith(".json") ? json : json + ".json");
//                log.info("load pojo config {}", path);
//                config = JSON.parseObject(FileUtils.readFileToString(org.springframework.util.ResourceUtils.getFile(path)), PojoConfig.class);
//                pojoConfigs.put(json, config);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return config;
//    }
//
//    public Object plugin(Object target) {
//        log.info("target:{}", target);
//        return Plugin.wrap(target, this);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//
//    }
//
//    private Object invoke(Object data, String propName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
//        if (data == null) {
//            return null;
//        }
//        int of = propName.indexOf('.');
//        if (of == -1) {
//            Method method = data.getClass().getMethod("get" + propName.substring(0, 1).toUpperCase() + propName.substring(1));
//            if (method == null) {
//                return null;
//            }
//            return method.invoke(data);
//        }
//        String propAbc = propName.substring(0, of);
//        Method method = data.getClass().getMethod("get" + propAbc.substring(0, 1).toUpperCase() + propAbc.substring(1));
//        return invoke(method.invoke(data), propName.substring(of + 1));
//    }
//
//    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource, String[] keyProps) {
//        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
//        builder.resource(ms.getResource());
//        builder.fetchSize(ms.getFetchSize());
//        builder.statementType(ms.getStatementType());
//        builder.keyGenerator(ms.getKeyGenerator());
//        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
//            builder.keyProperty(ms.getKeyProperties()[0]);
//        }
//        if (keyProps != null && keyProps.length > 0) {
//            builder.keyProperty("entity."+keyProps[0]);
//            builder.keyGenerator(new Jdbc3KeyGenerator());
//        }
//        builder.timeout(ms.getTimeout());
//        builder.parameterMap(ms.getParameterMap());
//        builder.resultMaps(ms.getResultMaps());
//        builder.resultSetType(ms.getResultSetType());
//        builder.cache(ms.getCache());
//        builder.flushCacheRequired(ms.isFlushCacheRequired());
//        builder.useCache(ms.isUseCache());
//        return builder.build();
//    }
//
//    public static class BoundSqlSqlSource implements SqlSource {
//        private BoundSql boundSql;
//
//        public BoundSqlSqlSource(BoundSql boundSql) {
//            this.boundSql = boundSql;
//        }
//
//        public BoundSql getBoundSql(Object parameterObject) {
//            return boundSql;
//        }
//    }
//}
