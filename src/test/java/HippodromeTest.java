import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HippodromeTest {
    private final String VALID_HORSE_NAME = "HORSE";
    private final double MIN_HORSE_SPEED = 0.1;

    @Test
    void throwExceptionAndValidateMessageWithNullList() {
        List<Horse> nullList = null;
        Exception expectedEx = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(nullList);
        });
        assertEquals("Horses cannot be null.", expectedEx.getMessage());
    }

    @Test
    void throwExceptionAndValidateMessageWithEmptyList() {
        List<Horse> emptyList = Collections.EMPTY_LIST;
        Exception expectedEx = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(emptyList);
        });
        assertEquals("Horses cannot be empty.", expectedEx.getMessage());
    }

    @Test
    void getHorses() {
        ArrayList<Horse> mockedHorses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Horse horse = new Horse(VALID_HORSE_NAME + i,MIN_HORSE_SPEED);
            mockedHorses.add(horse);
        }
        Hippodrome hippodrome = new Hippodrome(mockedHorses);
        assertEquals(mockedHorses, hippodrome.getHorses());
    }

    @Test
    void move() {
        ArrayList<Horse> mockedHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse mockedHorse = Mockito.mock(Horse.class);
            mockedHorses.add(mockedHorse);
        }
        Hippodrome hippodrome = new Hippodrome(mockedHorses);
        hippodrome.move();
        for (var val : mockedHorses) {
            verify(val).move();
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.3, 1.3, 2.4, 3.0, 4.9})
    void getWinner(double distance) {
        Horse expectedMockedHorse = Mockito.mock(Horse.class);
        when(expectedMockedHorse.getDistance()).thenReturn(distance);
        Horse mockedHorseMin = Mockito.mock(Horse.class);
        when(mockedHorseMin.getDistance()).thenReturn(MIN_HORSE_SPEED);
        Hippodrome hippodrome = new Hippodrome(List.of(expectedMockedHorse, mockedHorseMin));
        Horse winner = hippodrome.getWinner();
        assertEquals(expectedMockedHorse.getDistance(),winner.getDistance());
    }
}