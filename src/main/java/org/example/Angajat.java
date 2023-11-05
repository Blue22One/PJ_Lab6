package org.example;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Angajat {
    private String nume;
    private String post;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data_ang;
    private float salariu;

    public Angajat() {
    }

    public Angajat(String nume, String post, LocalDate data_ang, float salariu) {
        this.nume = nume;
        this.post = post;
        this.data_ang = data_ang;
        this.salariu = salariu;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "nume='" + nume + '\'' +
                ", post='" + post + '\'' +
                ", data_ang=" + data_ang +
                ", salariu=" + salariu +
                '}';
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public LocalDate getData_ang() {
        return data_ang;
    }

    public void setData_ang(LocalDate data_ang) {
        this.data_ang = data_ang;
    }

    public float getSalariu() {
        return salariu;
    }

    public void setSalariu(float salariu) {
        this.salariu = salariu;
    }
}
