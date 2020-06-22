package data_section;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Encrypting the password with MD2 algorithm
public class Encryptor implements Encrepted {

    @Override
    public String encrypt(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD2");
        StringBuilder stringBuilder = new StringBuilder();
        byte[] bytes = md5.digest(password.getBytes());
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02X", b));
        }
        return stringBuilder.toString();
    }
}
