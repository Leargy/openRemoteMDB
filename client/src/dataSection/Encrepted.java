package dataSection;

import java.security.NoSuchAlgorithmException;

public interface Encrepted {
    String encrypt(String password) throws NoSuchAlgorithmException;
}
