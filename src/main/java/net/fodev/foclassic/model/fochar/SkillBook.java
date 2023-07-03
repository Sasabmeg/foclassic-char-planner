package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class SkillBook {
    @Getter @Setter private SkillBookProto proto;
    @Getter @Setter private int used;
    @Getter @Setter private int maxUses;

    public SkillBook(SkillBookProto proto, int used, int maxUses) {
        this.proto = proto;
        this.used = used;
        this.maxUses = maxUses;
    }

    public void readBook() {
        used++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkillBook)) return false;

        SkillBook skillBook = (SkillBook) o;

        if (used != skillBook.used) return false;
        if (maxUses != skillBook.maxUses) return false;
        return Objects.equals(proto, skillBook.proto);
    }

    @Override
    public int hashCode() {
        int result = proto != null ? proto.hashCode() : 0;
        result = 31 * result + used;
        result = 31 * result + maxUses;
        return result;
    }
}
