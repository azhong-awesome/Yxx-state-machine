# state-machine
有限状态机
# fluent api demo
* https://github.com/soujava/sample-fluent-api/tree/main
  * 仓库对应的博客: https://medium.com/xgeeks/fluent-api-creating-easier-more-intuitive-code-with-a-fluent-api-fced26dc8a02

* https://blog.csdn.net/significantfrank/article/details/104996419
***
# 用 Fluent API 设计状态机 (与chatGpt探讨)

当我们处理复杂的业务逻辑和流程时，状态机是一种非常常用的设计模式。然而，建立和管理状态机往往包含大量的样板代码。如何让这些代码更加简洁和直观呢？

让我们先看下面这段代码：

```java
StateMachineBuilder builder = new StateMachineBuilder();
builder.from("State1")
   .to("State2")
   .when(someCondition)
   .doAction(someAction)
   .endTransition()
   .from("State2")
   .to("State3")
   .when(someCondition)
   .doAction(someAction)
   .endTransition()
   .build();
```



看上去像是用自然语言描述了一个状态机。"从状态1当满足某条件时做某操作转到状态2，然后..." 这基本上就是一个自然语言的描述了。那么问题就来了，我们的 Java 在定义状态机的时候真的可以如此简洁直观么？答案是肯定的，那就是Fluent interface的应用。


接着让我们来看看如何实现这种 Fluent API：


我们选择了4个函数式接口和一个结束的标记接口来设计我们的状态机API。这4个函数式接口分别是 `From<S>, To<S>,  When<S, C>, Do<S, C, A>` 和一个标志接口 `End<S, C, A>`

```java
public interface From<S> {
    To<S> from(S state);
}
public interface To<S> {
    When<S, Condition> to(S state);
}
public interface When<S, C> {
    Do<S, C, Action> when(C condition);
}
public interface Do<S, C, A> {
    End<S, C, A> doAction(A action);
}
public interface End<S, C, A> {
    From<S> endTransition();
}
```


然后定义了一个 StateMachineBuilder 和 StateMachineBuilder.TransitionBuilder，他们分别实现了这些接口。

```java
public class StateMachineBuilder<S, C, A> implements From<S> {
    private StateMachine<S, C, A> stateMachine;

    public StateMachineBuilder() {
        this.stateMachine = new StateMachine<>();
    }

    @Override
    public To<S> from(S state) {
        return new TransitionBuilder(state);
    }

    private class TransitionBuilder implements To<S>, When<S, C>, Do<S, C, A>, End<S, C, A> {
        private Transition<S, C, A> transition;

        public TransitionBuilder(S state) {
            this.transition = new Transition<>();
            this.transition.setFromState(state);
        }

        @Override
        public When<S, C> to(S state) {
            this.transition.setToState(state);
            return this;
        }

        @Override
        public Do<S, C, A> when(C condition) {
            this.transition.setCondition(condition);
            return this;
        }

        @Override
        public End<S, C, A> doAction(A action) {
            this.transition.setAction(action);
            return this;
        }

        @Override
        public From<S> endTransition() {
            stateMachine.addTransition(transition);
            return StateMachineBuilder.this;
        }
    }
}
```


这样我们就有了一个基于Fluent API的状态机构建器 。这是一种DSL（领域特定语言，内部DSL的一种）的实现方式，它是一种使代码更加富有表达力和可读性的方法。在这个例子中，我们创建状态机的过程就像是在叙述一个自然语言故事。在我们使用这个状态机的DSL时，我们可以顺着构建过程一路写下去，同时也能清晰地看出整个状态机的构建流程。


这个例子只是Fluent API设计的一个小实例，你可以根据自己的需求，参考这个模式，设计出自己的Fluent API。



希望这个例子可以给你在设计状态机或者其他Fluent API时带来帮助。如果有任何回馈或建议，欢迎在评论区留言提出。
***

# 内部DSL和外部DSL的区别, 上面的实现称为什么DSL?

上面的实现是内部DSL的一个例子。内部DSL（也被称为嵌入式DSL）是一种特殊的API，它在宿主编程语言（在这个例子中是Java）中设计并实现。内部DSL可以直接使用宿主语言的语法、编译器/解释器、调试工具等。


更具体的说，内部DSL是被设计成流畅语法格式（Fluent Interface），让你的代码更加可读，而且用户无需额外学习一门新语言，只需理解API即可。


对比而言，外部DSL是一门完全独立的语言，例如SQL、Ant或正则表达式。它有自己的语法，需要另外的解析器来解读。


总的来说，上面的Fluent API实现属于内部DSL，通过利用Java的语法设计的一种特殊的API。