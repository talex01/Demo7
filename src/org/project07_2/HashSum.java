package org.project07_2;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class HashSum {
    static String getMD5(String filename) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(filename));
        String md5 = DigestUtils.md5Hex(inputStream);
        inputStream.close();
        return md5;
    }

    static String getSHA(String filename) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(filename));
        String sha = DigestUtils.sha256Hex(inputStream);
        inputStream.close();
        return sha;
    }
}
