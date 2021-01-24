package ru.itis.javalab.services;

import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;

import java.util.List;

public class UsersServiceImpl implements UsersService{

    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        usersRepository.saveUser(user);
    }

    @Override
    public User getUserByLoginPassword(String login, String password) {
        List<User> users= usersRepository.getUserByLoginPassword(login, password);
        return users.get(0);
    }

    @Override
    public List<User> getUsersByUuid(String uuid) {
        return usersRepository.findAllByUUID(uuid);
    }
}
