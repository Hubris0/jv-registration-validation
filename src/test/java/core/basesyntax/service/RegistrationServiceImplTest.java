package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationServiceImplTest {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static User user = new User();
    private static RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_inputIsNull_notOk() {
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(null);
        assertThrows(NullPointerException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_alreadyExists_notOk() throws UserRegistrationException {
        user.setLogin("login1");
        user.setPassword("password");
        user.setAge(18);
        registrationService.register(user);
        User secondUser = new User();
        secondUser.setLogin("login1");
        secondUser.setPassword("password");
        secondUser.setAge(18);
        assertEquals(storageDao.get(user.getLogin()).getLogin(), secondUser.getLogin());
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(secondUser));
    }

    @Test
    void register_loginTooShort_notOk() throws UserRegistrationException {
        user.setLogin("log");
        user.setPassword("password");
        user.setAge(18);
        assertTrue(user.getLogin().length() < MINIMAL_LOGIN_LENGTH);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordTooShort_notOk() throws UserRegistrationException {
        user.setLogin("login1");
        user.setPassword("pass");
        user.setAge(18);
        assertTrue(user.getPassword().length() < MINIMAL_PASSWORD_LENGTH);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageTooYoung_notOk() {
        user.setLogin("login1");
        user.setPassword("password");
        user.setAge(15);
        assertTrue(user.getAge() < MINIMAL_AGE);
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userRegistration_ok() throws UserRegistrationException {
        user.setLogin("login1");
        user.setPassword("password");
        user.setAge(18);
        registrationService.register(user);
        assertFalse(user.getLogin().length() < MINIMAL_LOGIN_LENGTH);
        assertFalse(user.getPassword().length() < MINIMAL_PASSWORD_LENGTH);
        assertFalse(user.getAge() < MINIMAL_AGE);
        User storedUser = storageDao.get(user.getLogin());
        assertEquals(user, storedUser);
    }
}
