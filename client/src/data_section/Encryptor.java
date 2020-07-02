package data_section;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Encrypting the password with MD2 algorithm
public class Encryptor implements Encrepted {
    private String tempPaper = "";

    @Override
    public String encrypt(String password) throws NoSuchAlgorithmException {
        StringBuilder stringBuilder = new StringBuilder();
        MessageDigest md5 = MessageDigest.getInstance("MD2");
        byte[] bytes;
        if (!tempPaper.equals("")) {
//            Key ayes = new SecretKeySpec(tempPaper.getBytes(), "MD5"); //encripting temp pepe (AES - шифр)
            md5.update(tempPaper.getBytes());
            md5.update(password.getBytes());
            bytes = md5.digest();
        }else {
            bytes = md5.digest(password.getBytes());
        }

        for (byte b : bytes) {
            stringBuilder.append(String.format("%02X", b));
        }
        return stringBuilder.toString();
    }

    public void setTempPaper(String paper){
        tempPaper = paper.replace("SERVER_KEY:","");
    }
}
