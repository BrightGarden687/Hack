package lot.hack.setting.value;

import java.util.function.BooleanSupplier;

public abstract class Value<T> {
    private final String name;
    private final T defaultValue;

    protected T value;
    private BooleanSupplier visibility;

    protected Value(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public String getName() {
        return name;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public void reset() {
        value = defaultValue;
    }

    public boolean isVisible() {
        return visibility.getAsBoolean();
    }

    @SuppressWarnings("unchecked")
    public <E extends Value<?>> E visible(BooleanSupplier visibility) {
        this.visibility = visibility;
        return (E) this;
    }
}
