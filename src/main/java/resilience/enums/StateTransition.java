package resilience.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: yuanjinzhong
 * @date: 2024/4/28 17:05
 * @description:
 */
@AllArgsConstructor
@Getter
public enum StateTransition {
    INIT_EFFECTIVE(State.INIT, State.EFFECTIVE),

    INIT_TERMINATE(State.INIT, State.TERMINATE),

    EFFECTIVE_EXPIRED(State.EFFECTIVE, State.EXPIRED),

    EXPIRED_TERMINATE(State.EXPIRED, State.TERMINATE),

    EXPIRED_EFFECTIVE(State.EXPIRED, State.EFFECTIVE);


    private static final Map<Map.Entry<State, State>, StateTransition> STATE_TRANSITION_MAP = Arrays
            .stream(StateTransition.values())
            .collect(Collectors.toMap(v -> new AbstractMap.SimpleEntry<>(v.fromState, v.toState), Function.identity()));


    private final State fromState;

    private final State toState;

    public static StateTransition transitionBetween(State fromState, State toState) {

        StateTransition stateTransition = STATE_TRANSITION_MAP.get(new AbstractMap.SimpleEntry<>(fromState, toState));
        if (stateTransition == null) {
            throw new RuntimeException(String.format("非法状态转换:%s->%s", fromState, toState));
        }

        return stateTransition;
    }



}
