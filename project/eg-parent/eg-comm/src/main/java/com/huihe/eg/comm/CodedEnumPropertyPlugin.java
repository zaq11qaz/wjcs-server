package com.huihe.eg.comm;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.Annotations;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.swagger.schema.ApiModelProperties;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CodedEnumPropertyPlugin implements ModelPropertyBuilderPlugin {
    private List<Class> list = Arrays.asList(SystemEum.class,
            Class.forName("com.cy.framework.util.result.ResultEnum"), UserEum.class);

    public CodedEnumPropertyPlugin() throws ClassNotFoundException {
    }

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<ApiModelProperty> annotation = Optional.absent();
        if (context.getAnnotatedElement().isPresent()) {
            annotation = annotation.or(ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
        }
        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = annotation.or(Annotations.findPropertyAnnotation(
                    context.getBeanPropertyDefinition().get(),
                    ApiModelProperty.class));
        }
        for (Class cl : list) {
            final Class<?> rawPrimaryType = context.getBeanPropertyDefinition().get().getRawPrimaryType();
            //过滤得到目标类型
            if (annotation.isPresent() && cl.isAssignableFrom(rawPrimaryType)) {
                //获取CodedEnum的code值
                Object[] values = rawPrimaryType.getEnumConstants();
                List<String> displayValues = new ArrayList<>();
                try {
                    for (Object cl2 : values) {
                        Method getCode = cl2.getClass().getMethod("getCode");
                        Method getDesc = cl2.getClass().getMethod("getDesc");
                        displayValues.add(getCode.invoke(cl2).toString() + ":" + getDesc.invoke(cl2).toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                final AllowableListValues allowableListValues = new AllowableListValues(displayValues, rawPrimaryType.getTypeName());
                //固定设置为int类型
                final ResolvedType resolvedType = context.getResolver().resolve(String.class);
                context.getBuilder().allowableValues(allowableListValues).type(resolvedType);
            }
        }
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
