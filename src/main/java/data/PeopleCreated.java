package data;

public class PeopleCreated extends People {
    private String id;
    private String createdAt;

    public PeopleCreated() {
        super();
    }

    public PeopleCreated(String name, String job, String id, String createdAt) {
        super(name, job);
        this.id = id;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


}
