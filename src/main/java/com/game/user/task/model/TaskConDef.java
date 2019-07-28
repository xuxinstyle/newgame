package com.game.user.task.model;

import com.game.user.task.constant.TaskConditionType;
import com.game.util.StringUtil;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 11:02
 */
public class TaskConDef {
    /**
     * 条件类型
     */
    private TaskConditionType type;
    /**
     * 完成的参数 第一个参数 ：第二个参数  第一个参数表示完成条件需要的值
     */
    private String value;

    @JsonIgnore
    private String[] values;

    public String[] getValues() {
        if (values == null) {
            values = value.split(StringUtil.MAOHAO);
        }
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public TaskConditionType getType() {
        return type;
    }

    public void setType(TaskConditionType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
