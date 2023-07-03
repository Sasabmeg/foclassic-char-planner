package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class SkillBookProto {
    @Getter @Setter private SkillProto proto;
    @Getter @Setter private String name;
    @Getter @Setter private int value;

    public SkillBookProto(SkillProto proto, String name, int value) {
        this.proto = proto;
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkillBookProto)) return false;

        SkillBookProto skillBook = (SkillBookProto) o;

        if (value != skillBook.value) return false;
        if (!Objects.equals(proto, skillBook.proto)) return false;
        return Objects.equals(name, skillBook.name);
    }

    @Override
    public int hashCode() {
        int result = proto != null ? proto.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + value;
        return result;
    }
}
