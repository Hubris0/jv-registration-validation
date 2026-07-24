package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMAL_AGE = 18;
    private static final int MINIMAL_LOGIN_LENGTH = 6;
    private static final int MINIMAL_PASSWORD_LENGTH = 6;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws UserRegistrationException {
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User.login already exists");
        }
        if (user.getLogin() == null || user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new UserRegistrationException("User.login missing or too short. " +
                    "Should be at least " + MINIMAL_LOGIN_LENGTH + " characters");
        }
        if (user.getPassword() == null || user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new UserRegistrationException("User.password missing or too short. " +
                    "Should be at least " + MINIMAL_PASSWORD_LENGTH + "characters");
        }
        if (user.getAge() == null || user.getAge() < MINIMAL_AGE) {
            throw new UserRegistrationException("User.age is missing or too low");
        }
        storageDao.add(user);
        return user;
    }
}
