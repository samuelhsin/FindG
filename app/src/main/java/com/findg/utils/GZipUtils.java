package com.findg.utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZIP Tools
 *
 * @author <a href="mailto:zlex.dongliang@gmail.com">author</a>
 * @since 1.0
 */
public abstract class GZipUtils {

    public static final int BUFFER = 1024;
    public static final String EXT = ".gz";
    public static final String encoding = "ISO-8859-1";

    /**
     * data compress
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] compress(byte[] data) throws Exception {

        byte[] output;

        try (ByteArrayInputStream bais = new ByteArrayInputStream(data); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            compress(bais, baos);
            output = baos.toByteArray();
            baos.flush();
        }

        return output;
    }

    /**
     * data compress
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String compressToStr(byte[] data) throws Exception {

        String str = null;
        byte[] output = compress(data);

        if (output != null) {
            str = new String(output, encoding);
        }

        return str;
    }

    /**
     * file compress
     *
     * @param file
     * @throws Exception
     */
    public static void compress(File file) throws Exception {
        compress(file, true);
    }

    /**
     * file compress
     *
     * @param file
     * @param delete delete old file
     * @throws Exception
     */
    public static void compress(File file, boolean delete) throws Exception {
        try (FileInputStream fis = new FileInputStream(file); FileOutputStream fos = new FileOutputStream(file.getPath() + EXT)) {
            compress(fis, fos);
            fos.flush();
            if (delete) {
                file.delete();
            }
        }
    }

    /**
     * data compress
     *
     * @param is
     * @param os
     * @throws Exception
     */
    public static void compress(InputStream is, OutputStream os) throws Exception {

        try (GZIPOutputStream gos = new GZIPOutputStream(os)) {
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = is.read(data, 0, BUFFER)) != -1) {
                gos.write(data, 0, count);
            }

            gos.finish();
            gos.flush();

        }
    }

    /**
     * file compress
     *
     * @param path
     * @throws Exception
     */
    public static void compress(String path) throws Exception {
        compress(path, true);
    }

    /**
     * file compress
     *
     * @param path
     * @param delete is deleted old file
     * @throws Exception
     */
    public static void compress(String path, boolean delete) throws Exception {
        File file = new File(path);
        compress(file, delete);
    }

    /**
     * data decompress
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] decompress(byte[] data) throws Exception {

        try (ByteArrayInputStream bais = new ByteArrayInputStream(data); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            decompress(bais, baos);
            data = baos.toByteArray();
            baos.flush();
        }

        return data;
    }

    /**
     * data decompress
     *
     * @param dataStr
     * @return
     * @throws Exception
     */
    public static byte[] decompressFromStr(String dataStr) throws Exception {
        byte[] data = null;
        if (dataStr != null && !dataStr.isEmpty()) {
            data = strToBytes(dataStr, encoding);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(data); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                decompress(bais, baos);
                data = baos.toByteArray();
                baos.flush();
            }
        }
        return data;
    }

    /**
     * data decompress
     *
     * @param dataStr
     * @return
     * @throws Exception
     */
    public static String decompressToStr(String dataStr) throws Exception {
        String result = null;
        if (dataStr != null && !dataStr.isEmpty()) {
            byte[] data = strToBytes(dataStr, encoding);
            try (ByteArrayInputStream bais = new ByteArrayInputStream(data); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                decompress(bais, baos);
                data = baos.toByteArray();
                baos.flush();
            }
            result = new String(data, encoding);
        }
        return result;
    }

    /**
     * file decompress
     *
     * @param file
     * @throws Exception
     */
    public static void decompress(File file) throws Exception {
        decompress(file, true);
    }

    /**
     * file decompress
     *
     * @param file
     * @param delete is deleted old file
     * @throws Exception
     */
    public static void decompress(File file, boolean delete) throws Exception {

        try (FileInputStream fis = new FileInputStream(file); FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT, ""))) {
            decompress(fis, fos);
            fos.flush();
            if (delete) {
                file.delete();
            }
        }

    }

    /**
     * data decompress
     *
     * @param is
     * @param os
     * @throws Exception
     */
    public static void decompress(InputStream is, OutputStream os) throws Exception {

        try (GZIPInputStream gis = new GZIPInputStream(is)) {
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = gis.read(data, 0, BUFFER)) != -1) {
                os.write(data, 0, count);
            }
        }

    }

    /**
     * file decompress
     *
     * @param path
     * @throws Exception
     */
    public static void decompress(String path) throws Exception {
        decompress(path, true);
    }

    /**
     * file decompress
     *
     * @param path
     * @param delete is deleted old file
     * @throws Exception
     */
    public static void decompress(String path, boolean delete) throws Exception {
        File file = new File(path);
        decompress(file, delete);
    }

    public static byte[] bytesStrToBytes(String bytesStr) throws Exception {
        String[] byteValues = bytesStr.replaceFirst("\\[", "").replaceFirst("\\]", "").split(",");
        byte[] bytes = new byte[byteValues.length];
        for (int i = 0, len = bytes.length; i < len; i++) {
            bytes[i] = Byte.parseByte(byteValues[i].trim());
        }
        return bytes;
    }

    public static byte[] strToBytes(String str, String encoding) throws Exception {
        return str.getBytes(encoding);
    }

    public static final void main(String[] args) throws Exception {

        String testData = "[N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,Y,Y,Y,Y,Y,Y]".replaceFirst("\\[", "").replaceFirst("\\]", "");

        System.err.println("test data:\t" + testData);

        byte[] input = testData.getBytes();
        System.err.println("test data length:\t" + input.length);

        byte[] data = GZipUtils.compress(input);

        System.err.println("after compress:\t");
        System.err.println("after compress length:\t" + data.length);

        String dataStr = new String(data, encoding);
        System.err.println("after compress(str):\t" + dataStr);
        System.err.println("after compress str length:\t" + dataStr.length());

        data = strToBytes(dataStr, encoding);
        byte[] output = GZipUtils.decompress(data);
        String outputStr = new String(output, encoding);
        System.err.println("after decompress:\t" + outputStr);
        System.err.println("after decompress length:\t" + output.length);

    }

}
