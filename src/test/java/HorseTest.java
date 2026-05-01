
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class HorseTest {
    private static final double VALID_SPEED = 1.0;
    private static final String VALID_NAME = "Буренка";
    private static final double VALID_DISTANCE = 0.5;
    private final double MOCK_STATIC_GET_NON_RANDOM_DOUBLE = 0.5;
    @Test
    void throwExceptionAndValidateMessageWithNullName() {
        String nullName = null;
        Exception expectedEx = assertThrows(IllegalArgumentException.class, () ->
        {
            new Horse(nullName, VALID_SPEED);
        });
        assertEquals("Name cannot be null.", expectedEx.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"\n", "\t", "", " "})
    void throwExceptionAndValidateMessageWithBlankName(String name) {
        Exception expectedEx = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, VALID_SPEED);
        });
        assertEquals("Name cannot be blank.", expectedEx.getMessage());
    }


    @Test
    void throwExceptionAndValidateMessageWithSpeedNegative() {
        Double negativeSpeed = -1.0;
        Exception expectedEx = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(VALID_NAME, negativeSpeed);
        });
        assertEquals("Speed cannot be negative.", expectedEx.getMessage());
    }

    @Test
    void throwExceptionAndValidateMessageWithDistanceNegative() {
        Double negativeDistance = -0.5;
        Exception expectedEx = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(VALID_NAME, VALID_SPEED, negativeDistance);
        });
        assertEquals("Distance cannot be negative.", expectedEx.getMessage());
    }

    @Test
    void createWithTwoParameters() {
        Horse horse = new Horse(VALID_NAME, VALID_SPEED);
        assertEquals(VALID_NAME, horse.getName());
        assertEquals(VALID_SPEED, horse.getSpeed());
        assertEquals(null, horse.getDistance());
    }

    @Test
    void createWithThreeParameters() {
        Horse horse = new Horse(VALID_NAME, VALID_SPEED, VALID_DISTANCE);
        assertEquals(VALID_NAME, horse.getName());
        assertEquals(VALID_SPEED, horse.getSpeed());
        assertEquals(VALID_DISTANCE, horse.getDistance());
    }

    @Test
    void getName() {
        Horse horse = new Horse(VALID_NAME, VALID_SPEED);
        assertEquals(VALID_NAME, horse.getName());
    }

    @Test
    void getSpeed() {
        Horse horse = new Horse(VALID_NAME, VALID_SPEED);
        assertEquals(VALID_SPEED, horse.getSpeed());
    }

    @Test
    void getDistance() {
        Horse horse = new Horse(VALID_NAME, VALID_SPEED, VALID_DISTANCE);
        assertEquals(VALID_DISTANCE, horse.getDistance());
    }

    /** В оригинале кода заданы  без констант значения аргументов min, max метода getRandomDouble(min, max), в связи с этим в тесте
     * также использую подход "без констант"*/
    @ParameterizedTest
    @ValueSource(doubles = {0.1,1.0, 1.5, 35.3})
    void move(double distance) {
        Horse horse = new Horse(VALID_NAME, VALID_SPEED, distance);
        try (MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            mockedHorse.when(()->Horse.getRandomDouble(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(MOCK_STATIC_GET_NON_RANDOM_DOUBLE);
            double expectedDistance = distance + horse.getSpeed() * MOCK_STATIC_GET_NON_RANDOM_DOUBLE;
            horse.move();
            assertEquals(expectedDistance,horse.getDistance());
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2,0.9), times(1));
        }
    }
}