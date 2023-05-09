package org.random;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GenerateRandom {
    private static Faker faker;
    private static final Random random = new Random();

    private GenerateRandom() {
    }

    public static int randomPeriod(int begin, int end) {
        return random.nextInt(end + 1 - begin) + begin;
    }

    public static char randomSpecSymbol() {
        String specSymbols = "!@#$%^&*()_+~`;:.,<>/|?№";

        return specSymbols.charAt(random.nextInt(specSymbols.length()));
    }

    public static char randomSymbol(String locale) {
        String symbols;
        if (locale.equals("ru") || locale.equals("RU") || locale.equals("ру")) {
            symbols = "йцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁ";
        } else {
            symbols = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        }

        return symbols.charAt(random.nextInt(symbols.length()));
    }

    public static String generateDate(int plusDaysToCurrent, String pattern) {
        return LocalDate.now().plusDays(plusDaysToCurrent).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String generateCity() {
        List<String> cities = new ArrayList<>();

        cities.add("Казань");
        cities.add("Нижний Новгород");
        cities.add("Нальчик");
        cities.add("Санкт-Петербург");
        cities.add("Вологда");
        cities.add("Москва");
        cities.add("Ханты-Мансийск");

        return cities.get(random.nextInt(cities.size()));
    }

    public static String generateCity(String locale) {
        faker = new Faker(new Locale(locale));
        return faker.address().city();
    }

    public static String generateFullName(String locale) {
        faker = new Faker(new Locale(locale));
        return faker.name().fullName().replaceAll("ё", "е").replaceAll("Ё", "Е");
    }

    public static String generateFirstName(String locale) {
        faker = new Faker(new Locale(locale));
        return faker.name().firstName().replaceAll("ё", "е").replaceAll("Ё", "Е");
    }

    public static String generateLastName(String locale) {
        faker = new Faker(new Locale(locale));
        return faker.name().lastName().replaceAll("ё", "е").replaceAll("Ё", "Е");
    }

    public static String generateFirstAndLastNames(String locale) {
        return generateFirstName(locale) + " " + generateLastName(locale);
    }

    public static String generatePhone(String locale) {
        faker = new Faker(new Locale(locale));

        return faker.phoneNumber().phoneNumber()
                .replaceAll("\\)", "")
                .replaceAll("\\(", "")
                .replaceAll("-", "");
    }
    public static String generateLogin(String locale) {
        return generateFirstName(locale) + generateLastName(locale);
    }

    public static String generateEmail() {
        faker = new Faker(new Locale("en"));
        return faker.internet().emailAddress();
    }

    public static String generatePassword(int lengthIs8AndMore) {
        if (lengthIs8AndMore < 8) {
            lengthIs8AndMore = 8;
        }
        StringBuilder password = new StringBuilder();
        String lowerLetters = "qwertyuiopasdfghjklzxcvbnm";
        String upperLetters = lowerLetters.toUpperCase();
        String digitChars = "1234567890";
        String specChars = "!?@#$%^&*-+";
        String allLetters = lowerLetters + upperLetters;
        String allChars = lowerLetters + upperLetters + digitChars + specChars;

        char[] charArray = new char[lengthIs8AndMore - 4];

        for(int i = 0; i < 3; i++){
            password.append(allLetters.charAt(random.nextInt(allLetters.length())));
        }
        for(int i = 3; i < 5; i++){
            password.append(lowerLetters.charAt(random.nextInt(lowerLetters.length())));
        }

        charArray[0] = specChars.charAt(random.nextInt(specChars.length()));
        charArray[1] = digitChars.charAt(random.nextInt(digitChars.length()));
        charArray[2] = upperLetters.charAt(random.nextInt(upperLetters.length()));

        for(int i = 3; i < charArray.length; i++){
            charArray[i] = allChars.charAt(random.nextInt(allChars.length()));
        }
        int count = 0;
        while (count < lengthIs8AndMore - 4) {
            int numberInCharArray = random.nextInt(charArray.length);
            password.append(charArray[numberInCharArray]);
            count++;
            char[] tempForDeleteUsedChar = new char[charArray.length - 1];
            int newNumberInCharArray = 0;
            for(int i = 0; i < charArray.length; i++){
                if(i != numberInCharArray){
                    tempForDeleteUsedChar[newNumberInCharArray] = charArray[i];
                    newNumberInCharArray++;
                }
            }
            charArray = tempForDeleteUsedChar;
        }
        return password.toString();
    }
    public static String generatePassword() {
        return generatePassword(8);
    }
}
