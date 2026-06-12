package com.example.travelplanner.models;
import jakarta.persistence.*;

@Entity @Table (name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "user_id")
    private Integer id;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String phone;
    @Column
    private String gender;
    @Column
    private String password;
    @Column
    private String major;
    @Column
    private String role;
    @Column
    private String profilePic;

    public User()
    {}

    public User(Integer id, String firstname,
                String lastname, String email,
                String phone, String gender,
                String password, String major,
                String role, String profilePic) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
        this.major = major;
        this.role = role;
        this.profilePic = profilePic;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getMajor() {
        return major;
    }

    public String getRole() {
        return role;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
