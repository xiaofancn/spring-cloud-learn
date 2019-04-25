package org.fansxnet.common.mybatis.provider;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.fansxnet.common.PojoConfig;
import org.fansxnet.common.mybatis.annotation.Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fanzj
 * @description: TODO
 * @date 2019/4/259:09
 */
@Slf4j
public abstract class SqlProvider {
    static final Map<String, PojoConfig> pojoConfigs = new HashMap<>();


    protected Model getModelAnnotation(Object record) {
        return getModelAnnotation(record.getClass());
    }

    protected Model getModelAnnotation(Class<?> entityClass) {
        return entityClass.getAnnotation(Model.class);
    }

    protected Object invoke(Object data, String propName) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        if (data == null) {
            return null;
        }
        int of = propName.indexOf('.');
        if (of == -1) {
            Method method = data.getClass().getMethod("get" + propName.substring(0, 1).toUpperCase() + propName.substring(1));
            if (method == null) {
                return null;
            }
            return method.invoke(data);
        }
        String propAbc = propName.substring(0, of);
        Method method = data.getClass().getMethod("get" + propAbc.substring(0, 1).toUpperCase() + propAbc.substring(1));
        return invoke(method.invoke(data), propName.substring(of + 1));
    }

    protected PojoConfig loadConfig(Object record) {
        Model model = getModelAnnotation(record);
        return load(model.json());
    }

    protected PojoConfig loadConfig(Class<?> entityClass) {
        Model model = getModelAnnotation(entityClass);
        return load(model.json());
    }

    protected synchronized PojoConfig load(String json) {
        PojoConfig config = pojoConfigs.get(json);
        if (config == null) {
            try {
                String path = "classpath:mapper/json/" + (json.endsWith(".json") ? json : json + ".json");
                log.info("load pojo config {}", path);
                config = JSON.parseObject(new FileInputStream(org.springframework.util.ResourceUtils.getFile(path)), PojoConfig.class);
                pojoConfigs.put(json, config);
            } catch (IOException e) {
                log.error("加载配置文件错误");
                e.printStackTrace();
            }
        }
        return config;
    }
}
