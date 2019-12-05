package school.of.thought.model;

public class Disease {
    private String imageUrl;
    private String name;
    private String shortDesc;

    public Disease() {
    }

    public Disease(String imageUrl, String name, String shortDesc) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.shortDesc = shortDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getShortDesc() {
        return shortDesc;
    }
}
