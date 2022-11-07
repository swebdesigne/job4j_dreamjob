package ru.job4j.dreamjob.utils;

public enum UserAddStatus {
    SUCCESSFULLY("Пользователь успешно зарегистрирован!"),
    UNSUCCESSFULLY("Пользователь с такой почтой уже существует!");
    private final String status;

    UserAddStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
