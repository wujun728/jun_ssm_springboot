package com.ruoyi.framework.config.dialect;

import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.utils.StrUtils;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * 自定义复选框标签（单个复选框）
 */
public class RyCheckboxTag extends AbstractElementTagProcessor {
    // 标签名
    private static final String TAG_NAME = "checkbox";
    // 优先级
    private static final int PRECEDENCE = 10000;

    public RyCheckboxTag(String dialectPrefix) {
        super(
                // 此处理器将仅应用于HTML模式
                TemplateMode.HTML,
                // 要应用于名称的匹配前缀
                dialectPrefix,
                // 标签名称：匹配此名称的特定标签
                TAG_NAME,
                // 将标签前缀应用于标签名称
                true,
                // 无属性名称：将通过标签名称匹配
                null,
                // 没有要应用于属性名称的前缀
                false,
                // 优先(内部方言自己的优先)
                PRECEDENCE
        );
    }

    /**
     * 处理自定义标签 DOM 结构
     * @param context  模板页上下文
     * @param tag      待处理标签
     * @param handler  元素标签结构处理器
     */
    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, IElementTagStructureHandler handler) {
    	StringBuilder html = new StringBuilder("");
        //获取标签的属性值
        String name = tag.getAttributeValue("name");
        String cssClass = tag.getAttributeValue("class");
        String itemLabel = tag.getAttributeValue("itemLabel");
        String itemValue = tag.getAttributeValue("itemValue");
        String required = tag.getAttributeValue("required");
        String value = tag.getAttributeValue("value");
        String delims = tag.getAttributeValue("delims");

        //传入的分隔符为空或未传入，则默认为逗号
        delims = StrUtil.isBlank(delims) ? "," : delims;

        html.append("<label class=\""+cssClass+"\"><input type=\"checkbox\" name=\""+name+"\" value=\""+itemValue+"\"");
        if(StrUtil.isNotBlank(required)) {
            html.append(" required=\""+required+"\"");
        }
        if(StrUtils.containsAny(value, delims, itemValue)) {
            html.append(" checked=\"checked\"");
        }
        html.append("/>"+itemLabel+"</label>");
    	handler.replaceWith(html.toString(), false);
    }
}