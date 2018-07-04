package com.weburnit.schemaless.schema;

@FunctionalInterface
public interface ElementBuilderFunction<F, O> {

    O apply(F f);
}
