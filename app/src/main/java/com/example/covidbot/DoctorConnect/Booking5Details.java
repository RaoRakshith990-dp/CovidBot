package com.example.covidbot.DoctorConnect;

public class Booking5Details {
    String age;
    String allergy;

    public Booking5Details() {
    }

    public Booking5Details(String age, String allergy) {
        this.age = age;
        this.allergy = allergy;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }
}
