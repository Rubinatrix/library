package controller.property_editor;

import domain.Genre;
import service.interfaces.GenreService;

import java.beans.PropertyEditorSupport;

public class GenrePropertyEditor extends PropertyEditorSupport{

    public GenrePropertyEditor(GenreService dbCategoryManager) {
        this.dbCategoryManager = dbCategoryManager;
    }

    private GenreService dbCategoryManager;

    public String getAsText() {
        Genre obj = (Genre) getValue();
        if (null == obj) {
            return "";
        } else {
            return obj.getId().toString();
        }
    }

    public void setAsText(final String value) {
        try {
            Long id = Long.parseLong(value);
            Genre genre = dbCategoryManager.get(id);
            super.setValue(genre);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Binding error. Invalid id: " + value);
        }
    }
}
