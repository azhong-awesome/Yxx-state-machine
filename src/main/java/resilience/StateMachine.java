package resilience;

/**
 * @author: yuanjinzhong
 * @date: 2024/4/28 17:04
 * @description: 事件触发状态转移, 转移到具体状态之后, 执行该状态下的业务动作, 该业务动作中可能会继续发出事件
 */
public interface StateMachine {


    static StateMachine ofDefaults(String name) {
        return new TradeStateMachine(name);
    }

    /**
     * 发射事件， 这个可能是状态机的核心，发出事件，通过事件去驱动状态转移
     * <p>
     * OrderStatusEnum orderStatusEnum = orderOperaMachine.fireEvent(OrderStatusEnum.CLOSE, OrderEvent.ADMIN_CLOSE, order);
     */
    void fireEvent();


    /**
     * 作为一个交易状态机，它里面做的事情不应该是这样， 而应该是：下单， 支付成功，支付失败 （接受支付回调）， 主动取消支付，过期不去续约，过期去续约
     * <p>
     * 应该是一些有业务含义的动作
     */
    void transitionToEffective();

    void transitionToExpired();

    void transitionToTerminate();
}
