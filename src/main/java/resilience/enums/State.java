package resilience.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: yuanjinzhong
 * @date: 2024/4/28 17:02
 * @description: 权益包项目的状态：合同状态:0-待生效;1-已生效;2-作废
 */
@AllArgsConstructor
@Getter
public enum State {

    INIT("初始化"),

    EFFECTIVE("生效中"),

    EXPIRED("过期"),

    TERMINATE("终止");

    private final String desc;

}
