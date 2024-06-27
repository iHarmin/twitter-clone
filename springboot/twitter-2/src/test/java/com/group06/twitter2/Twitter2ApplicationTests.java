/*
    Citation: This code is taken from PasswordValidatorTest.java which is in csci3130-lab4 repository which was taught by TA.
 */

package com.group06.twitter2;

import com.group06.twitter2.extras.PasswordValidator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;
import org.junit.Before;

@SpringBootTest
class Twitter2ApplicationTests {

    @Test
    void contextLoads() {
    }

    PasswordValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new PasswordValidator();
    }

    /*
        Citation: This test case code is taken from PasswordValidatorTest.java which is in csci3130-lab4 repository which was taught by TA.
     */
    @Test
    public void testValidPassword() {
        PasswordValidator validator = new PasswordValidator();
        String password = "Password123!";
        boolean isValid = validator.validatePassword(password);
        Assert.assertTrue("Valid password should be accepted", isValid);
    }

    /*
        Citation: This test case code is taken from PasswordValidatorTest.java which is in csci3130-lab4 repository which was taught by TA.
     */
    @Test
    public void testPasswordTooShort() {
        PasswordValidator validator = new PasswordValidator();
        String password = "Pa1!";
        boolean isValid = validator.validatePassword(password);
        Assert.assertFalse("Password too short should be rejected", isValid);
    }

    /*
        Citation: This test case code is taken from PasswordValidatorTest.java which is in csci3130-lab4 repository which was taught by TA.
     */
    @Test
    public void testPasswordMissingUppercase() {
        PasswordValidator validator = new PasswordValidator();
        String password = "password123!";
        boolean isValid = validator.validatePassword(password);
        Assert.assertFalse("Password missing uppercase should be rejected", isValid);
    }

    /*
        Citation: This test case code is taken from PasswordValidatorTest.java which is in csci3130-lab4 repository which was taught by TA.
     */
    @Test
    public void testPasswordMissingLowercase() {
        PasswordValidator validator = new PasswordValidator();
        String password = "PASSWORD123!";
        boolean isValid = validator.validatePassword(password);
        Assert.assertFalse("Password missing lowercase should be rejected", isValid);
    }

    /*
        Citation: This test case code is taken from PasswordValidatorTest.java which is in csci3130-lab4 repository which was taught by TA.
     */
    @Test
    public void testPasswordMissingSpecialCharacter() {
        PasswordValidator validator = new PasswordValidator();
        String password = "Password123";
        boolean isValid = validator.validatePassword(password);
        Assert.assertFalse("Password missing special character should be rejected", isValid);
    }
}