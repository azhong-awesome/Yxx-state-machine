import org.junit.jupiter.api.Test;
import resilience.StateMachine;

/**
 * @author: yuanjinzhong
 * @date: 2024/4/28 17:02
 * @description:
 */
public class StateMachineTest {


    @Test
    public void test(){

        StateMachine stateMachine = StateMachine.ofDefaults("测试状态机");

        // 这里应该是接受到支付成功的消息,然后触发到Effective
        stateMachine.transitionToEffective();

    }


}
