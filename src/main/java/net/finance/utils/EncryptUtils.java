package net.finance.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.security.crypto.codec.Hex;

public class EncryptUtils {

	public static Optional<String> hashPassword(String password) {
		try {
			final MessageDigest dig = MessageDigest.getInstance("MD5");
			dig.reset();
			dig.update(password.getBytes(Charset.forName("UTF8")));
			final byte[] arrBytes = dig.digest();
			return Optional.of(new String(Hex.encode(arrBytes)));
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
}
