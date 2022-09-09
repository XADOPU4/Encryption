package encryptor;

public interface Encryptor {
    String encrypt(String text);
    String decrypt(String text);
    void show();
}
