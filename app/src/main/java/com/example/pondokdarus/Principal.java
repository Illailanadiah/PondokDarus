package com.example.pondokdarus;

public class Principal {
    private String name;
    private String id;
    private String role;
    private String imageUrl;

    // Default constructor required for calls to DataSnapshot.getValue(Principal.class)
    public Principal() {
    }

    public Principal(String name, String id, String role, String imageUrl) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
