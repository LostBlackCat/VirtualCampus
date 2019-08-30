package com.seu.vCampus.util;

import java.util.ArrayList;

public class Person extends Message{
    private String Name;
    private String StudentNumber;
    private short AuthorityLevel;
    private short LendBooksNumber;
    private short ECardBalance;



    private boolean isMatched;


    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public Person(){
        this.Type = MESSAGE_TYPE.TYPE_PERSON;
    }

    public String getName() {
        return Name;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public short getAuthorityLevel() {
        return AuthorityLevel;
    }

    public short getLendBooksNumber() {
        return LendBooksNumber;
    }

    public short getECardBalance() {
        return ECardBalance;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }

    public void setAuthorityLevel(short authorityLevel) {
        AuthorityLevel = authorityLevel;
    }

    public void setLendBooksNumber(short lendBooksNumber) {
        LendBooksNumber = lendBooksNumber;
    }

    public void setECardBalance(short ECardBalance) {
        this.ECardBalance = ECardBalance;
    }
}
