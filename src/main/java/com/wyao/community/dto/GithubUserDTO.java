package com.wyao.community.dto;

public class GithubUserDTO {

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public GithubUserDTO(String login, Long id, String bio) {
        this.login = login;
        this.id = id;
        this.bio = bio;
    }

    private String login;
    private Long id;
    private String bio;
}
