package com.github.ashvard.gdx.simple.animation.io.interpret.predicates;

import com.github.ashvard.gdx.simple.animation.fsm.FsmContext;
import com.github.ashvard.gdx.simple.animation.fsm.FsmPredicate;
import com.github.ashvard.gdx.simple.animation.io.interpret.expression.Expression;

public class ExpressionGtPredicate implements FsmPredicate {

    private final Expression expression;

    public ExpressionGtPredicate(Expression expression) {
        this.expression = expression;
    }

    @Override
    public boolean predicate(FsmContext context) {
        return expression.gt(context);
    }
}
