package boco.component;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HaversineTest {
    @InjectMocks
    private Haversine haversine;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testHaversineDistantPoints() {
        double distance = Haversine.distance(50.0 + 0.3/6, 05.0 + 4.2/6, 58.0 + 3.8/6, 03.0 + 0.4/6);
        Assertions.assertTrue(distance < 970000);
        Assertions.assertTrue(distance > 968000);
    }

    @Test
    void testHaversineIdenticalPoints() {
        double distance = Haversine.distance(50.0, 05.0, 50.0, 05.0);
        Assertions.assertEquals(0.0, distance);
    }

    @Test
    void testHaversineNorthPole() {
        double distance = Haversine.distance(90, 5, 90, 15);
        Assertions.assertEquals(0.0, distance);
    }
}
