package nju.software.util;

import org.apache.commons.codec.digest.DigestUtils;

public class SecurityUtil {
	public static String md5hex(String value) {
		return DigestUtils.md5Hex(value);
	}
}
