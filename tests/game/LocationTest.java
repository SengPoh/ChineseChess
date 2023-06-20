package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Location.java.
 *
 * @author Lee Seng Poh
 * @version 20-6-2023
 */
class LocationTest {

    @Test
    public void init()
    {
        Location location = new Location(5, 10);
        assertEquals(location.getX(), 5);
        assertEquals(location.getY(), 10);
    }

    @Test
    public void add_coordinates()
    {
        Location location = new Location(5, 10);
        Location result = location.add(6, 12);
        assertEquals(result.getX(), 11);
        assertEquals(result.getY(), 22);
        assertEquals(location.getX(), 5);
        assertEquals(location.getY(), 10);
    }

    @Test
    public void add_minusCoordinates()
    {
        Location location = new Location(5, 10);
        Location result = location.add(-6, -12);
        assertEquals(result.getX(), -1);
        assertEquals(result.getY(), -2);
        assertEquals(location.getX(), 5);
        assertEquals(location.getY(), 10);
    }


    @Test
    public void add_location()
    {
        Location location = new Location(5, 10);
        Location location2 = new Location(3, 2);
        Location result = location.add(location2);
        assertEquals(result.getX(), 8);
        assertEquals(result.getY(), 12);
        assertEquals(location.getX(), 5);
        assertEquals(location.getY(), 10);
    }

    @Test
    public void add_minusLocation()
    {
        Location location = new Location(5, 10);
        Location location2 = new Location(-3, -2);
        Location result = location.add(location2);
        assertEquals(result.getX(), 2);
        assertEquals(result.getY(), 8);
        assertEquals(location.getX(), 5);
        assertEquals(location.getY(), 10);
    }

    @Test
    public void equals_EqualLocation_True() {
        Location loc1 = new Location(5, 5);
        Location loc2 = new Location(5, 5);
        assertEquals(loc1, loc2);
    }

    @Test
    public void equals_NonLocationObject_False() {
        Location loc1 = new Location(5, 5);
        Object obj = new Object();
        assertNotEquals(loc1, obj);
    }

    @Test
    public void equals_SameXDistinctY_False() {
        Location loc1 = new Location(5, 1);
        Location loc2 = new Location(5, 8);
        assertNotEquals(loc1, loc2);
    }

    @Test
    public void equals_DistinctXSameY_False() {
        Location loc1 = new Location(3, 8);
        Location loc2 = new Location(25, 8);
        assertNotEquals(loc1, loc2);
    }

    @Test
    public void hashCode_EqualLocation_Equals() {
        Location loc1 = new Location(5, 5);
        Location loc2 = new Location(5, 5);
        assertEquals(loc1.hashCode(), loc2.hashCode());
    }

    @Test
    public void hashCode_SeparateHashing_Equal() {
        Location loc1 = new Location(12, 3);
        int hashCode = loc1.hashCode();
        assertEquals(hashCode, loc1.hashCode());
    }
}