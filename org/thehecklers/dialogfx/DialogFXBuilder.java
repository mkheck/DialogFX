package org.thehecklers.dialogfx;

import java.util.HashMap;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;


/**
 * Created with IntelliJ IDEA.
 * User: hansolo
 * Date: 04.09.12
 * Time: 09:05
 * To change this template use File | Settings | File Templates.
 * 
 * Mods: HecklerMark
 * Date: 25.09.12
 * Desc: Added stylesheet capability. Guess I need to check out IDEA for this project now (sigh). :-)
 */
public class DialogFXBuilder <B extends DialogFXBuilder<B>> extends ControlBuilder<B> implements Builder<DialogFX> {
    private HashMap<String, Property> properties = new HashMap<>();

    protected DialogFXBuilder() {}

    public static DialogFXBuilder create() {
        return new DialogFXBuilder();
    }

    public final DialogFXBuilder buttons(final List<String> LABELS) {
        properties.put("buttonsLabels", new SimpleObjectProperty<List<String>>(LABELS));
        return this;
    }

    public final DialogFXBuilder buttons(final List<String> LABELS, final int DEFAULT_BUTTON, final int CANCEL_BUTTON) {
        properties.put("buttonsLabels1", new SimpleObjectProperty<List<String>>(LABELS));
        properties.put("buttonsDefaultButton", new SimpleIntegerProperty(DEFAULT_BUTTON));
        properties.put("buttonsCancelButton", new SimpleIntegerProperty(CANCEL_BUTTON));
        return this;
    }

    public final DialogFXBuilder type(final DialogFX.Type TYPE) {
        properties.put("type", new SimpleObjectProperty<DialogFX.Type>(TYPE));
        return this;
    }

    public final DialogFXBuilder message(final String MESSAGE) {
        properties.put("message", new SimpleStringProperty(MESSAGE));
        return this;
    }

    public final DialogFXBuilder modal(final boolean MODAL) {
        properties.put("modal", new SimpleBooleanProperty(MODAL));
        return this;
    }

    public final DialogFXBuilder titleText(final String TITLE_TEXT) {
        properties.put("titleText", new SimpleStringProperty(TITLE_TEXT));
        return this;
    }

    public final DialogFXBuilder stylesheet(final String STYLESHEET) {
        properties.put("stylesheet", new SimpleStringProperty(STYLESHEET));
        return this;
    }
    

    @Override public DialogFX build() {
        final DialogFX CONTROL = new DialogFX();

        for (String key : properties.keySet()) {
            if ("buttonsLabels".equals(key)) {
                CONTROL.addButtons(((ObjectProperty<List<String>>) properties.get(key)).get());
            } else if ("buttonsLabels1".equals(key)) {
                CONTROL.addButtons(((ObjectProperty<List<String>>) properties.get(key)).get(),
                                   ((IntegerProperty) properties.get("buttonsDefaultButton")).get(),
                                   ((IntegerProperty) properties.get("buttonsCancelButton")).get());
            } else if ("type".equals(key)) {
                CONTROL.setType(((ObjectProperty<DialogFX.Type>) properties.get(key)).get());
            } else if ("message".equals(key)) {
                CONTROL.setMessage(((StringProperty) properties.get(key)).get());
            } else if ("modal".equals(key)) {
                CONTROL.setModal(((BooleanProperty) properties.get(key)).get());
            } else if ("titleText".equals(key)) {
                CONTROL.setTitleText(((StringProperty) properties.get(key)).get());
            } else if ("stylesheet".equals(key)) {
                CONTROL.addStylesheet(((StringProperty) properties.get(key)).get());
            }
        }

        return CONTROL;
    }
}
