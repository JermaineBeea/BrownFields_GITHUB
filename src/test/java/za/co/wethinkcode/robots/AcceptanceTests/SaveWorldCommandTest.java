
package za.co.wethinkcode.robots.AcceptanceTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import za.co.wethinkcode.robots.commands.SaveWorldCommand;
import za.co.wethinkcode.robots.domain.DomainWorld;
import za.co.wethinkcode.robots.persistence.WorldDao;

public class SaveWorldCommandTest {
    private WorldDao mockDao;
    private SaveWorldCommand command;

    @BeforeEach
    public void setup() {
        mockDao = mock(WorldDao.class);
        command = new SaveWorldCommand(mockDao);
    }
 @Test
    public void testExecuteSavesWorldAndPrintsMessage() {
        // Arrange
        DomainWorld mockWorld = mock(DomainWorld.class);
        when(mockWorld.getName()).thenReturn("TestWorld");

        // Capture System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Act
        command.execute(mockWorld);
    // Assert
        verify(mockDao, times(1)).saveWorld(mockWorld);
        assertTrue(outContent.toString().contains("World 'TestWorld' saved."));

        // Reset System.out
        System.setOut(System.out);
    }
}
   
