package loverduck.clover.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 이미지 저장할 때 쓰는 generator
 */
public class MD5Generator {
	 private String result;

	    public MD5Generator(String input) throws UnsupportedEncodingException, NoSuchAlgorithmException {
	        MessageDigest mdMD5 = MessageDigest.getInstance("MD5");
	        mdMD5.update(input.getBytes("UTF-8"));
	        byte[] md5Hash = mdMD5.digest();
	        StringBuilder hexMD5hash = new StringBuilder();
	        for(byte b : md5Hash) {
	            String hexString = String.format("%02x", b);
	            hexMD5hash.append(hexString);
	        }
	        result = hexMD5hash.toString();
	    }

	    public String toString() {
	        return result;
	    }

}
