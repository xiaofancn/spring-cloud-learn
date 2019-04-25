package org.fansxnet.common.mybatis.provider;


import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.jdbc.SQL;
import org.fansxnet.common.PojoConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fanzj
 * @description: TODO
 * @date 2019/4/259:02
 */
public class SimpleSqlProvider extends SqlProvider {

    public String insertSelective(Object paramObject) {
        String entityKey = "entity";
        Object record = ((MapperMethod.ParamMap) paramObject).get(entityKey);
        PojoConfig pojoConfig = loadConfig(record);
        SQL sql = new SQL();
        sql.INSERT_INTO(pojoConfig.getTable());
        List<String> props = new ArrayList<>(pojoConfig.getPropColumn().size());
        pojoConfig.getPropColumn().keySet().forEach(prop -> {
            try {
                if (invoke(record, prop) != null) {
                    props.add(prop);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        sql.INTO_COLUMNS(buildInsertSqlPart(pojoConfig.getPropColumn(), props, false, null));
        sql.INTO_VALUES(buildInsertSqlPart(pojoConfig.getPropColumn(), props, true, entityKey));
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Object paramObject) {
        String entityKey = "entity";
        Object record = ((MapperMethod.ParamMap) paramObject).get(entityKey);
        PojoConfig pojoConfig = loadConfig(record);
        SQL sql = new SQL();
        buildUpdateSetSqlPart(pojoConfig,sql,record,entityKey);
        String keyProp = pojoConfig.getPropKey()[0];
        sql.WHERE(String.format("%s=#{%s.%s}", pojoConfig.getPropColumn().get(keyProp), entityKey, keyProp));
        return sql.toString();
    }

    public String updateByExampleSelective(Object paramObject) {
        String entityKey = "entity", exampleKey = "example";
        Object record = ((MapperMethod.ParamMap) paramObject).get(entityKey);
        PojoConfig pojoConfig = loadConfig(record);
        ExampleX exampleX = (ExampleX) ((MapperMethod.ParamMap) paramObject).get(exampleKey);
        SQL sql = exampleX.getSql();
        buildUpdateSetSqlPart(pojoConfig,sql,record,entityKey);
        exampleX.getConditions().forEach(con->{
            sql.WHERE(con.replace("#{", "#{" + exampleKey + "."));
        });
        return sql.toString();
    }


    private void buildUpdateSetSqlPart(PojoConfig pojoConfig,SQL sql,Object record,String entityKey) {
        sql.UPDATE(pojoConfig.getTable());
        pojoConfig.getPropColumn().keySet().forEach(prop -> {
            try {
                if (invoke(record, prop) != null) {
                    sql.SET(String.format("%s=#{%s.%s}", pojoConfig.getPropColumn().get(prop), entityKey, prop));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private String[] buildInsertSqlPart(Map<String, String> propColumn, List<String> props, boolean isProp, String entityKey) {
        String[] ret = new String[props.size()];
        for (int i = 0; i < props.size(); i++) {
            if (isProp) {
                ret[i] = (String.format("#{%s.%s}", entityKey, props.get(i)));
            } else {
                ret[i] = (propColumn.get(props.get(i)));
            }
        }
        return ret;
    }
}
