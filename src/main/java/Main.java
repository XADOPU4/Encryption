import encryptor.Encryptor;
import encryptor.impl.PolybianSquareEncryptor;

public class Main {

    public static void main(String[] args) {
        polybian();
    }

    public static void polybian(){
        //Задаем алфавит
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ-,.0123456789 ";

        //На всякий случай вывод его и размера
        System.out.println("Alphabet: " + alphabet);
        System.out.println("Alphabet has length: " + alphabet.length());

        //создание шифратора
        Encryptor encryptor = PolybianSquareEncryptor
                .builder(alphabet, 5).build();

        //Создание такого же шифратора через конструктор
        Encryptor encryptor1 = new PolybianSquareEncryptor(alphabet, 5);


        //Показ таблицы в консоль
        encryptor.show();

        //Шифрование
        String text = "HELLO";
        String encrypted = encryptor.encrypt(text);

        System.out.println("Encrypted: \n" + text + "\n" + encrypted);

        //Дешифрование
        String decrypted = encryptor.decrypt(encrypted);
        System.out.println("Decrypted: \n" + encrypted + "\n" + decrypted);
    }
}
