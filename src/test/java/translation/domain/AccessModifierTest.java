package translation.domain;

import org.junit.Test;

import static org.junit.Assert.*;
import static translation.domain.AccessModifier.*;

public class AccessModifierTest {

    @Test
    public void testTranslationFromShortToLongName() {
        assertEquals("Public", toLongName("P"));
        assertEquals("Public", toLongName("p"));
        assertEquals("Private", toLongName("X"));
        assertEquals("Private", toLongName("x"));
        assertEquals("View", toLongName("V"));
        assertEquals("View", toLongName("v"));
        assertEquals("Inherit", toLongName("I"));
        assertEquals("Inherit", toLongName("i"));
    }

    @Test
    public void testTranslationFromLongToShortName() {
        assertEquals("P", toShortName("PuBliC"));
        assertEquals("X", toShortName("privATE"));
        assertEquals("V", toShortName("VieW"));
        assertEquals("I", toShortName("inherit"));
    }

    @Test
    public void testCallingToLongNameWithNullThrowsException() {
        try {
            toLongName(null);
            fail("Expected runtime exception!");
        } catch(RuntimeException e) {
            assertEquals("shortName is NULL!", e.getMessage());
        }
    }

    @Test
    public void testCallingtoShortNameWithNullThrowsException() {
        try {
            toShortName(null);
            fail("Expected runtime exception!");
        } catch(RuntimeException e) {
            assertEquals("longName is NULL!", e.getMessage());
        }
    }

    @Test
    public void testCallingToLongNameWithInvalidValueThrowsException() {
        try {
            toLongName("Invalid Value");
            fail("Expected runtime exception!");
        } catch(RuntimeException e) {
            assertEquals("'Invalid Value' is an invalid access modifier!", e.getMessage());
        }
    }

    @Test
    public void testCallingToShortNameWithInvalidValueThrowsException() {
        try {
            toShortName("Q");
            fail("Expected runtime exception!");
        } catch(RuntimeException e) {
            assertEquals("'Q' is an invalid access modifier!", e.getMessage());
        }
    }
}