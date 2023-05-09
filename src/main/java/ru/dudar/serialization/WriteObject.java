package ru.dudar.serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteObject {
    public static void main(String[] args) {
        Person person1 = new Person(1, "Bob", 25, "M");
        Person person2 = new Person(2, "Mike", 56, "M");
        Person person3 = new Person(3, "Olga", 17, "W");
        Person[] people = {person1, person2, person3};
//запись объектов в файл (причем и массивы как объекты
        try {
            FileOutputStream fos = new FileOutputStream("people.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(person1);
            oos.writeObject(person2);

            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//записываем массив и новый метод отпределения методов потока (tryObject)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("peopleArr.bin"))){
            oos.writeInt(people.length);
            for (Person person : people) {
                oos.writeObject(person);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
