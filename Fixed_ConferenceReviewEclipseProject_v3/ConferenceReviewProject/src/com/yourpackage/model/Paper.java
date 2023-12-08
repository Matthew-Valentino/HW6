package model;

public class Paper {
    private int id;
    private String title;
    // Assuming other properties needed, just adding the id and title for the snippet provided
    public Paper(int id, String title) {
        this.id = id;
        this.title = title;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
