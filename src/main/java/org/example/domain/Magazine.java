package org.example.domain;

import org.example.exceptions.ValidationException;

public class Magazine extends Publication {
    private final int issueNumber;

    public Magazine(String id, String title, int issueNumber) {
        super(id, title);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        if (issueNumber <= 0) {
            throw new ValidationException("El número de edición debe ser un entero positivo");
        }
    }

    @Override
    public String summarize() {
        return "Revista[id=" + getId() + ", título=\"" + getTitle() + "\"" + ", edición=" + issueNumber + "]";
    }
}

