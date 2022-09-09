package encryptor.impl;

import encryptor.Encryptor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@Builder(builderMethodName = "internalBuilder")
@AllArgsConstructor
public class PolybianSquareEncryptor implements Encryptor {

    /**
     * ширина таблицы, задается пользователем в качестве ключа
     */
    private int width;
    /**
     * высота таблицы, рассчитывается в конструкторе из ширины, заданной пользователем и длины алфавита
     */
    private int height;
    /**
     * строка, содержащая исходный алфавит
     */
    @NonNull
    private String alphabet;
    /**
     * массив символов алфавита, перемешанный в случайном порядке (по нему и происходит шифрование)
     */
    private char[] charAlphabet;

    /**
     * Можно использовать просто конструктор, куда всё будет передаваться
     */
    public static PolybianSquareEncryptorBuilder builder(@NotNull String alphabet, int width) {
        int lastLineLength = alphabet.length() % width;
        int height = lastLineLength == 0 ? alphabet.length() / width : alphabet.length() / width + width;

        String shuffledAlphabet = shuffleString(alphabet);

        return internalBuilder()
                .alphabet(alphabet)
                .charAlphabet(shuffledAlphabet.toCharArray())
                .width(width)
                .height(height);
    }

    /**
     *этот конструктор можно использовать вместо @Builder
     */
    public PolybianSquareEncryptor(@NotNull String alphabet, int width) {
        int lastLineLength = alphabet.length() % width;
        int height = lastLineLength == 0 ? alphabet.length() / width : alphabet.length() / width + width;

        String shuffledAlphabet = shuffleString(alphabet);

        this.alphabet = alphabet;
        this.charAlphabet = shuffledAlphabet.toCharArray();
        this.width = width;
        this.height = height;
    }

    /**
     * Метод для перемешки символов в строке
     *
     * @param toShuffle исходная строка
     * @return строку, в которой символы исходной строки находятся в случайном порядке
     */
    private static String shuffleString(String toShuffle) {

        boolean debug = false;
        if (debug) {
            return " 9876543210.,-ZYXWVUTSRQPONMLKJIHGFEDCBA";
        }

        Random rand = new Random();
        char[] chars = toShuffle.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            int randomIndexToSwap = rand.nextInt(chars.length);
            char temp = chars[randomIndexToSwap];
            chars[randomIndexToSwap] = chars[i];
            chars[i] = temp;
        }
        return textFromCharArray(chars);
    }

    /**
     * метод, возвращающий номер символа в перемешанном алфавите
     *
     * @param symbol символ, номер которого нужно найти
     * @return номер символа в перемешанном алфавите
     */
    public int getIndexOfSymbol(char symbol) {
        int index = 0;
        for (int i = 0; i < charAlphabet.length; i++) {
            if (symbol == charAlphabet[i]) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * метод, шифрующий отдельный символ согласно формуле шифрования
     *
     * @param symbol символ, который нужно зашифровать
     * @return зашифрованный символ
     */
    public char encryptSymbol(char symbol) {
        int originalIndex = getIndexOfSymbol(symbol);
        int encryptedIndex = (originalIndex + width) % charAlphabet.length;
        return charAlphabet[encryptedIndex];
    }

    /**
     * метод, дешифрующий отдельный символ согласно формуле дешифрования
     *
     * @param symbol символ, который нужно дешифровать
     * @return дешифрованный символ
     */
    public char decryptSymbol(char symbol) {
        int originalIndex = getIndexOfSymbol(symbol);
        int encryptedIndex = (originalIndex - width + charAlphabet.length) % charAlphabet.length;
        return charAlphabet[encryptedIndex];
    }

    /**
     * метод, собирающий все символы массива в строку
     *
     * @param chars массив символов, который нужно собрать в строку
     * @return строку, состоящую из исходных символов массива
     */
    public static String textFromCharArray(char[] chars) {
        StringBuilder text = new StringBuilder();
        for (char symbol : chars) {
            text.append(symbol);
        }
        return text.toString();
    }

    /**
     * метод, шифрующий строку целиком
     *
     * @param text исходный текст
     * @return зашифрованный текст
     */
    @Override
    public String encrypt(@NotNull String text) {
        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            chars[i] = encryptSymbol(chars[i]);
        }
        return textFromCharArray(chars);
    }

    /**
     * метод, дешифрующий строку целиком
     *
     * @param text исходный зашифрованный текст
     * @return дешифрованный текст
     */
    @Override
    public String decrypt(@NotNull String text) {
        char[] chars = text.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            chars[i] = decryptSymbol(chars[i]);
        }
        return textFromCharArray(chars);
    }

    /**
     * метод, выводящий таблицу, по которой шифруем
     */
    @Override
    public void show() {
        System.out.println("===============");
        for (int i = 0; i < charAlphabet.length; i++) {
            if (i > 0 && i % width == 0) {
                System.out.print("\n");
            }
            System.out.print(charAlphabet[i] + "\t");
        }
        System.out.println("\n===============");
    }


}
