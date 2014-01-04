package net.javacrumbs.streams;

@FunctionalInterface
public interface MyFunction {
    void block();

    default int number() {
        block();
        return 1;
    }

}
