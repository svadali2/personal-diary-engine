package com.diary.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.xml.bind.DatatypeConverter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ValidatorService {

    private final SimpleDateFormat dateTimeFormatter=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static final String SECRET_KEY = "admin";

    public boolean isValidDate(String dateToFormat) {
        try {
            dateTimeFormatter.setLenient(false);
            dateTimeFormatter.parse(dateToFormat);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
    }

}
