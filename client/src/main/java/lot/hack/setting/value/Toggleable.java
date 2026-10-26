package lot.hack.setting.value;

public interface Toggleable {
    default void setToggled(boolean toggled) {
        if (toggled != isToggled()) {
            if (toggled) {
                onEnable();
            } else {
                onDisable();
            }
        }
    }
    
    boolean isToggled();

    default void toggle() {
        setToggled(!isToggled());
    }

    default void onEnable() {}

    default void onDisable() {}
}
