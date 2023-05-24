package net.fodev.foclassic.model.fochar;

import lombok.Getter;
import lombok.Setter;

public class Special {
    @Getter @Setter private SpecialProto proto;
    @Getter @Setter private int value;

    public Special(SpecialProto proto, int value) {
        this.proto = proto;
        this.value = value;
    }

    public void increase(int value) {
        this.value += value;
    }

    public void decrease(int value) {
        this.value -= value;
    }

    public String getValueRank() {
        String result = "N/A";
        if (value < 1) return "V.Bad";
        if (value > 10) return "Heroic";
        switch(value) {
            case 1:
                result = "V.Bad";
                break;
            case 2:
                result = "Bad";
                break;
            case 3:
                result = "Poor";
                break;
            case 4:
                result = "Fair";
                break;
            case 5:
                result = "Avrg";
                break;
            case 6:
                result = "Good";
                break;
            case 7:
                result = "V.Good";
                break;
            case 8:
                result = "Great";
                break;
            case 9:
                result = "Exl.";
                break;
            case 10:
                result = "Heroic";
                break;
        }
        return result;
    }

    public String getName() {
        return proto.getName();
    }

    @Override
    public String toString() {
        return proto.getName();
    }

    public String getDescription() {
        return proto.getDescription();
    }

    public String getImage() {
        return proto.getImage();
    }
}