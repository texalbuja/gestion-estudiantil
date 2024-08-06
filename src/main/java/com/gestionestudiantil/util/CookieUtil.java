package com.gestionestudiantil.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil
{
  public static final String COOKIE_AUTENTICACION = "tokenAutenticacion";
  public static final int EXPIRACION_UN_MES = 2592000;
  
  public static String getCookieValue(HttpServletRequest request, String name)
  {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) { Cookie[] arrayOfCookie1;
      int j = (arrayOfCookie1 = cookies).length; for (int i = 0; i < j; i++) { Cookie cookie = arrayOfCookie1[i];
        if (name.equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }
  
  public static void addCookie(HttpServletResponse response, String name, String value, int maxAge)
  {
    Cookie cookie = new Cookie(name, value);
    cookie.setPath("/");
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }
  
  public static void removeCookie(HttpServletResponse response, String name) {
    addCookie(response, name, null, 0);
  }
}