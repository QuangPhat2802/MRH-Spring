package com.training.test;

import org.apache.commons.codec.digest.DigestUtils;

import com.training.common.constant.Constants;

public class PasswordGenerator {

	public static String ecryptMD5(String inputStr) {
		return DigestUtils.md5Hex(DigestUtils.md5Hex(inputStr + Constants.SALT_CONST));
	}

	public static void main(String[] args) {
		System.out.println(ecryptMD5("123456"));
	}
}
