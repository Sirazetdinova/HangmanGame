package backend.academy.dialogs.common;

public interface Validator<T> {
    void validate(T input);
}
