package by.pkasila.quizer.tasks.math;

import by.pkasila.quizer.common.Result;
import by.pkasila.quizer.common.MathOperation;
import by.pkasila.quizer.tasks.TaskVariant;
import by.pkasila.quizer.tasks.VariantTask;

public class VariantExpressionTask extends AbstractExpressionTask implements VariantTask {

    private final TaskVariant variant;

    private final Double[] variants;

    public VariantExpressionTask(int left, MathOperation operator, int right, double w1, double w2, TaskVariant variant) {
        super(left, operator, right);
        variants = switch (variant) {
            case A -> new Double[]{computeAnswer(), w1, w2};
            case B -> new Double[]{w1, computeAnswer(), w2};
            case C -> new Double[]{w1, w2, computeAnswer()};
        };
        this.variant = variant;
    }

    @Override
    public boolean isInvalid() {
        if (Math.abs(variants[0] - variants[1]) < 0.001 || Math.abs(variants[0] - variants[2]) < 0.001 || Math.abs(variants[1] - variants[2]) < 0.001) {
            return true;
        }
        return super.isInvalid();
    }

    @Override
    public String getText() {
        return getTextVariant();
    }

    @Override
    public Result validate(String answer) {
        return validateVariant(answer);
    }

    @Override
    public TaskVariant getCorrectVariant() {
        return variant;
    }

    @Override
    public String[] getAnswerVariants() {
        return new String[]{variants[0].toString(), variants[1].toString(), variants[2].toString()};
    }
}
