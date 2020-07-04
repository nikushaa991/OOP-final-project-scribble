package main.java;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    /*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
    public static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    /* Returns SHA representation of the string */
    public static String shaVal(String psw) throws NoSuchAlgorithmException {
        String result = "";
        MessageDigest md = MessageDigest.getInstance("SHA");
        byte[] res = md.digest(psw.getBytes());
        result = hexToString(res);
        return result;
    }
}
