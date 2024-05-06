package resilience;

import resilience.state.InitState;
import resilience.state.TradeState;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: yuanjinzhong
 * @date: 2024/4/28 17:04
 * @description: 交易状态机
 * <p>
 * 事件触发状态转移,转移到具体状态之后,执行该状态下的业务动作,该业务动作中可能会继续发出事件
 */
public class TradeStateMachine implements StateMachine {

    private final AtomicReference<TradeState> stateReference;

    private final String name;


    public TradeStateMachine(String name) {
        this.stateReference = new AtomicReference<>(new InitState());
        this.name = name;
    }

    @Override
    public void fireEvent() {

    }

    @Override
    public void transitionToEffective() {

    }

    @Override
    public void transitionToExpired() {

    }

    @Override
    public void transitionToTerminate() {

    }
}
