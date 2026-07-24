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
            throw new UserRegistrationException("User already exists");
        } else if (user.getLogin().length() < MINIMAL_LOGIN_LENGTH) {
            throw new UserRegistrationException("Login too short");
        } else if (user.getPassword().length() < MINIMAL_PASSWORD_LENGTH) {
            throw new UserRegistrationException("Password too short");
        } else if (user.getAge() < MINIMAL_AGE) {
            throw new UserRegistrationException("User is not adult");
        }
        storageDao.add(user);
        return user;
    }
}
