package lot.hack.setting.value.values;

import java.util.ArrayList;
import java.util.List;

import lot.hack.setting.value.Value;

public class ValueMode extends Value<ValueMode.Mode> {
    private final List<ValueMode.Mode> modes = new ArrayList<>();

    public ValueMode(String name, Mode defaultValue) {
        super(name, defaultValue);
    }

    public ValueMode(String name) {
        super(name, null);
    }

    public Mode create(String name) {
        Mode mode = new Mode(name);
        mode.setParent(this);
        modes.add(mode);
        return mode;
    }

    public <E extends Mode> E tree(E mode) {
        mode.setParent(this);
        modes.add(mode);
        return mode;
    }

    public class Mode {
        private final String name;
        private ValueMode parent;

        public Mode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setParent(ValueMode parent) {
            this.parent = parent;
        }

        public boolean selected() {
            return parent.value == this;
        }

        public <E extends Mode> E select() {
            parent.value = this;
            return (E) this;
        }
    }
}
