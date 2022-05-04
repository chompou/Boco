package boco.component;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class BocoHasherTest {


    @Test
    void testStringEncode() {

        String original = "hashThisString";
        String otherString = "otherHash";

        String hashed = BocoHasher.encode(original);
        String hashedAgain = BocoHasher.encode(original);
        String hashOther = BocoHasher.encode(otherString);

        assertNotEquals(hashed, original);
        assertEquals(hashedAgain, hashed);
        assertNotEquals(hashOther, otherString);
        assertNotEquals(hashed, hashOther);
    }

    @Test
    void testLongEncode() {
        Long original = 1l;
        Long otherString = 2l;

        String hashed = BocoHasher.encode(original);
        String hashedAgain = BocoHasher.encode(original);
        String hashOther = BocoHasher.encode(otherString);

        assertNotEquals(hashed, original);
        assertEquals(hashedAgain, hashed);
        assertNotEquals(hashOther, otherString);
        assertNotEquals(hashed, hashOther);
    }
}