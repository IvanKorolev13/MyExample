package org.random;

import com.github.javafaker.Faker;

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

    public static int randomIntInRange(int begin, int end) {
        return random.nextInt(end + 1 - begin) + begin;
    }

    public static String randomSymbolsInString(String str, int countSymbols) {
        if (countSymbols < 1) {
            countSymbols = 1;
        }
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < countSymbols; i++) {
            result.append(str.charAt(random.nextInt(str.length())));
        }

        return result.toString();
    }

    public static String randomSpecSymbol(int countSymbols) {
        return randomSymbolsInString("!@#$%^&*()_+~`;:.,<>/|?№", countSymbols);
    }

    public static String randomLetter(String locale, int countSymbols) {
        String symbols;
        if (locale.equals("ru") || locale.equals("RU") || locale.equals("ру")) {
            symbols = "йцукенгшщзхъфывапролджэячсмитьбюёЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮЁ";
        } else {
            symbols = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        }

        return randomSymbolsInString(symbols, countSymbols);
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

    public static String deleteSymbolsInString(String str, String exclude) {
        if (exclude.length() == 0) {
            return str;
        }

        String[] charInExclude = exclude.split("", exclude.length());
        for (String s : charInExclude) {
            if ("ё".equals(s)) {
                str = str.replaceAll(s.toLowerCase(), "е");
            } else if ("Ё".equals(s)) {
                str = str.replaceAll(s.toLowerCase(), "Е");
            } else {
                str = str.replaceAll(s.toLowerCase(), "").replaceAll(s.toUpperCase(), "");
            }
        }
        return str;
    }

    public static String generateFullName(String locale, String exclude) {
        return deleteSymbolsInString(new Faker(new Locale(locale)).name().fullName(), exclude);
    }

    public static String generateFullName(String locale) {
        return deleteSymbolsInString(new Faker(new Locale(locale)).name().fullName(), "");
    }

    public static String generateFirstName(String locale, String exclude) {
        return deleteSymbolsInString(new Faker(new Locale(locale)).name().firstName(), exclude);
    }

    public static String generateFirstName(String locale) {
        return deleteSymbolsInString(new Faker(new Locale(locale)).name().firstName(), "");
    }

    public static String generateLastName(String locale, String exclude) {
        return deleteSymbolsInString(new Faker(new Locale(locale)).name().lastName(), exclude);
    }

    public static String generateLastName(String locale) {
        faker = new Faker(new Locale(locale));
        return deleteSymbolsInString(faker.name().lastName(), "");
    }

    public static String generateFirstAndLastNames(String locale, String delimiter, String exclude) {
        return generateFirstName(locale, exclude) + delimiter + generateLastName(locale, exclude);
    }

    public static String generateFirstAndLastNames(String locale, String exclude) {
        return generateFirstName(locale, exclude) + " " + generateLastName(locale, exclude);
    }

    public static String generateFirstAndLastNames(String locale) {
        return generateFirstName(locale, "") + " " + generateLastName(locale, "");
    }

    public static String generatePhone(String locale, String exclude) {
        return deleteSymbolsInString(new Faker(new Locale(locale)).phoneNumber().phoneNumber(), exclude);
    }

    public static String generatePhone(String locale) {
        return deleteSymbolsInString(new Faker(new Locale(locale)).phoneNumber().phoneNumber(), "-()");
    }

    public static String generateLogin(String locale) {
        return generateFirstName(locale) + generateLastName(locale);
    }

    public static String generateEmail() {
        return new Faker(new Locale("en")).internet().emailAddress();
    }

    public static String generatePassword(int lengthIs8AndMore, String exclude) {
        if (lengthIs8AndMore < 8) {
            lengthIs8AndMore = 8;
        }
        StringBuilder password = new StringBuilder();
        String lowerLetters = deleteSymbolsInString("qwertyuiopasdfghjklzxcvbnm", exclude);
        String upperLetters = lowerLetters.toUpperCase();
        String digitChars = deleteSymbolsInString("1234567890", exclude);
        String specChars = deleteSymbolsInString("!?@#$%^&*-+", exclude);
        String allLetters = lowerLetters + upperLetters;
        String allChars = lowerLetters + upperLetters + digitChars + specChars;

        char[] charArray = new char[lengthIs8AndMore - 4];

        //first two symbols are any letters
        for (int i = 0; i < 2; i++) {
            password.append(allLetters.charAt(random.nextInt(allLetters.length())));
        }

        //next two symbols are lower letters
        for (int i = 2; i < 4; i++) {
            password.append(lowerLetters.charAt(random.nextInt(lowerLetters.length())));
        }

        //must have a spec symbol, a digit and an upper letter
        charArray[0] = specChars.charAt(random.nextInt(specChars.length()));
        charArray[1] = digitChars.charAt(random.nextInt(digitChars.length()));
        charArray[2] = upperLetters.charAt(random.nextInt(upperLetters.length()));

        //everything else is filled with any symbols
        for (int i = 3; i < charArray.length; i++) {
            charArray[i] = allChars.charAt(random.nextInt(allChars.length()));
        }

        //mixing the element in the charArray
        for (int i = 0; i < lengthIs8AndMore - 4; i++) {
            int numberOfRandomSymbolInCharArray = random.nextInt(charArray.length);
            password.append(charArray[numberOfRandomSymbolInCharArray]);
            char[] tempForDeleteUsedChar = new char[charArray.length - 1];
            int numberInTemp = 0;

            //delete used element from the charArray
            for (int j = 0; j < charArray.length; j++) {
                if (j != numberOfRandomSymbolInCharArray) {
                    tempForDeleteUsedChar[numberInTemp] = charArray[j];
                    numberInTemp++;
                }
            }
            charArray = tempForDeleteUsedChar;
        }
        return password.toString();
    }
    public static String generatePassword(int lengthIs8AndMore) {
        return generatePassword(lengthIs8AndMore, "");
    }

    public static String generatePassword() {
        return generatePassword(8, "");
    }
}
