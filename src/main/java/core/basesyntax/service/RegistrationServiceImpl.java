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
        if (user == null) {
            throw new UserRegistrationException("User is null");
        }
        if (user.getLogin() == null) {
            throw new UserRegistrationException("User.login is null.");
        }
        if (user.getPassword() == null) {
            throw new UserRegistrationException("User.password is null.");
        }
        if (user.getAge() == null) {
            throw new UserRegistrationException("User.age is null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserRegistrationException("User.login already exists.");
        }
        if (user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new UserRegistrationException("User.login too short. "
                    + "Should be at least " + MINIMAL_LOGIN_LENGTH + " characters");
        }
        if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new UserRegistrationException("User.password too short. "
                    + "Should be at least " + MINIMAL_PASSWORD_LENGTH + "characters");
        }
        if (user.getAge() < MINIMAL_AGE) {
            throw new UserRegistrationException("User.age is too low, needs to be at least "
                    + MINIMAL_AGE);
        }
        storageDao.add(user);
        return user;
    }
}
