package awe.ideeninitiative.model.builder;

import awe.ideeninitiative.model.Nachricht;

public final class NachrichtBuilder {
    private String text;

    private NachrichtBuilder() {
    }

    public static NachrichtBuilder aNachricht() {
        return new NachrichtBuilder();
    }

    public NachrichtBuilder withText(String text) {
        this.text = text;
        return this;
    }

    public Nachricht build() {
        Nachricht nachricht = new Nachricht();
        nachricht.setText(text);
        return nachricht;
    }
}
