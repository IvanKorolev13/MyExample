package ru.dudar.serialization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

public class ReadObject {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("people.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Person person1 = (Person) ois.readObject();
            Person person2 = (Person) ois.readObject();

            System.out.println(person1);
            System.out.println(person2);

            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) { //ошибка может возникнуть, если при считывании нет нужного класса Person
            e.printStackTrace();
        }
//считывание массива и новый метод отпределения методов потока (tryObject)
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("peopleArr.bin"))){
            int personCount = ois.readInt();
            Person[] people = new Person[personCount];
            for (int i = 0; i < personCount; i++) {
                people[i] = (Person) ois.readObject();
            }

            System.out.println(Arrays.toString(people));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) { //ошибка может возникнуть, если при считывании нет нужного класса Person
            e.printStackTrace();
        }
    }
}
