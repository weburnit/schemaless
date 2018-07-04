package com.weburnit.schemaless.schema;

public interface ConstraintBuilderFunction<F, O> {

    O apply(F f);
}
