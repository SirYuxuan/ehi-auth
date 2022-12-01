package com.yuxuan66.ehi.auth.templates;

import com.yuxuan66.ehi.auth.common.EhiAuth;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.IOException;
import java.util.Map;

/**
 * @author Sir丶雨轩
 * @date 2020/6/16
 */
public class FreeMarkerAuth implements TemplateDirectiveModel {

    private EhiAuth ehiAuth;

    public FreeMarkerAuth(EhiAuth ehiAuth){
        this.ehiAuth = ehiAuth;
    }

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

        String authCode = map.get("code").toString();

        if(ehiAuth.getPermission().contains(authCode)){
            templateDirectiveBody.render(environment.getOut());
        }
    }
}
