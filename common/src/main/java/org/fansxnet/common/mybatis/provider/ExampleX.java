package org.fansxnet.common.mybatis.provider;

import lombok.Data;
import org.apache.ibatis.jdbc.SQL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author fanzj
 * @description: TODO
 * @date 2019/4/2317:42
 */
@Data
public class ExampleX extends HashMap<String, Object> {
    private SQL sql ;
    private List<String> conditions;
    public ExampleX(){
        this.sql = new SQL();
        conditions = new ArrayList<>(8);
    }

    public ExampleX where(String condition) {
        this.sql.WHERE(condition);
        return this;
    }
    public ExampleX where(String exp, Object val) {
        if (val != null) {
            conditions.add(exp);
            this.put(getPropByCondition(exp),val);
        }
        return this;
    }

    private String getPropByCondition(String exp){
        int inOf = exp.indexOf("#{");
        if (inOf >= 0) {
            return exp.substring(inOf + 2, exp.indexOf("}", inOf)).trim();
        }
        return exp;
    }


    public static void test(String[] args) {
        //#{userId} = 19
        String exp = "#{userId} = 19";
        int inOf = exp.indexOf("#{");
        if (inOf < 0) {
        }
        System.out.println(exp.substring(inOf + 2, exp.indexOf("}", inOf)).trim());
    }
}
