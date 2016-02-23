 package ru.javawebinar.topjava;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;

import static java.util.Objects.requireNonNull;
import ru.javawebinar.topjava.util.UserMealsUtil;

// Mock implementation

public class LoggedUser extends org.springframework.security.core.userdetails.User {
    private UserTo userTo;

    public LoggedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public static LoggedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object user = auth.getPrincipal();
        return (user instanceof LoggedUser) ? (LoggedUser) user : null;
    }

    public static LoggedUser get() {
        LoggedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public UserTo getUserTo() {
        return userTo;
    }

    public static int id() {
        return get().userTo.getId();
    }

    @Override
    public String toString() {
        return userTo.toString();
    }

    public static int getCaloriesPerDay() {
        return get().userTo.getCaloriesPerDay();
    }

    public void update(UserTo newTo) {
        userTo = newTo;
    }
}
