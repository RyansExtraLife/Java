package com.example.AssetManagementSystem.modules;

import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import org.junit.Test;

import static org.junit.Assert.*;

public class Campus_Class_Unit_Test {

    @Test
    public void isInvalidCampusTestValueOneEmpty() {

        // Tests to see if empty value fails the test.
        String TestString = "";

        Campus test_Campus = new Campus(99, TestString, "10/10/19", "BadeDate");

        assertFalse(test_Campus.isValidCampus());
    }

    @Test
    public void isInvalidCampusTestValueTwoEmpty() {

        // Tests to see if empty value fails the test.
        String TestString = "";

        Campus test_Campus = new Campus(99, "Test Campus", TestString, "10/11/19");

        assertFalse(test_Campus.isValidCampus());
    }

    @Test
    public void isInvalidCampusTestValueThreeEmpty() {

        // Tests to see if empty value fails the test.
        String TestString = "";

        Campus test_Campus = new Campus(99, "Test Campus", "10/10/19", TestString);

        assertFalse(test_Campus.isValidCampus());
    }

    @Test
    public void isInvalidValidCampusTestParsableDate() {

        // Tests to see if invalid dates fails the test.
        Campus test_Campus = new Campus(99, "Test Campus", "BadDate", "10/11/19");

        assertFalse(test_Campus.isValidCampus());
    }

    @Test
    public void isInvalidCampusTestTime() {

        // Tests to see if start date after end date fails the test.
        Campus test_Campus_two = new Campus(99, "Test Campus", "10/10/19", "10/8/19");

        assertFalse(test_Campus_two.isValidCampus());
    }


    @Test
    public void isValidCampus() {

        //Control test to is if correct info passes.
        Campus test_Campus_two = new Campus(99, "Test Campus", "10/10/19", "10/11/19");

        assertTrue(test_Campus_two.isValidCampus());
    }

}