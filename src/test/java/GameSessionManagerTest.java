import game.battleship.core.GameSession;
import game.battleship.core.GameSessionManager;
import game.battleship.repository.GameSessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GameSessionManagerTest {

    private GameSessionRepository repository;
    private GameSessionManager sessionManager;

    @BeforeEach
    void setup() {
        repository = mock(GameSessionRepository.class);
        sessionManager = new GameSessionManager(repository);
    }

    @Test
    void testSaveSession_shouldCallRepositorySave() {
        GameSession session = mock(GameSession.class);
        when(session.getSessionId()).thenReturn("sessionId1");

        sessionManager.saveSession(session);

        verify(repository).save(session);
    }

    @Test
    void testGetSession_shouldReturnSessionFromRepository() {
        GameSession session = mock(GameSession.class);
        when(repository.findById("s1")).thenReturn(session);

        GameSession result = sessionManager.getSession("s1");

        assertNotNull(result);
        assertEquals(session, result);
        verify(repository).findById("s1");
    }

    @Test
    void testDeleteSession_shouldCallRepositoryDelete() {
        sessionManager.deleteSession("to-delete");

        verify(repository).delete("to-delete");
    }

    @Test
    void testGetAllSessions_shouldReturnListCopy() {
        List<GameSession> sessions = List.of(mock(GameSession.class), mock(GameSession.class));
        when(repository.findAll()).thenReturn(sessions);

        List<GameSession> result = sessionManager.getAllSessions();

        assertEquals(2, result.size());
        assertThrows(UnsupportedOperationException.class, () -> result.add(mock(GameSession.class)));
        verify(repository).findAll();
    }

    @Test
    void testGetValidatedSession_whenSessionExists() {
        GameSession session = mock(GameSession.class);
        when(repository.findById("session123")).thenReturn(session);

        GameSession result = sessionManager.getValidatedSession("session123");

        assertNotNull(result);
        assertEquals(session, result);
    }

    @Test
    void testGetValidatedSession_whenSessionMissing() {
        when(repository.findById("invalid")).thenReturn(null);

        GameSession result = sessionManager.getValidatedSession("invalid");

        assertNull(result);
    }
}
