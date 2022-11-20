package ru.job4j.dreamjob.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ru.job4j.dreamjob.model.User;

public final class UserHttpSessionUtil {
  private UserHttpSessionUtil() {
  }

  public static User getUser(HttpSession session) {
    User user = (User) session.getAttribute("user");
    if (user == null) {
      user = new User();
      user.setName("Гость");
    }
    return user;
  }

  public static void setUser(HttpServletRequest rq, User user) {
    HttpSession session = rq.getSession();
    session.setAttribute("user", user);
  }
}
