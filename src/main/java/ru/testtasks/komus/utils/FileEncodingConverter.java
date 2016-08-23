package ru.testtasks.komus.utils;


import java.io.*;


public class FileEncodingConverter {
    public static  File convertToUtf8(File inFile) throws UnsupportedEncodingException {
        File out = null;
        try {
            String inEncoding;
            FileInputStream fis = new FileInputStream(inFile);
            int[] first3bytes = new int[3];
            for (int i = 0; i < 3; i++)
                first3bytes[i] = fis.read();
            fis.close();
            if (first3bytes[0] == 239 && first3bytes[1] == 187 && first3bytes[2] == 191) {
                inEncoding = "UTF-8";

            } else {
                inEncoding="Cp1251";
            }
            Reader r = new BufferedReader(new InputStreamReader(new FileInputStream(inFile), inEncoding));
            out = File.createTempFile("outTemp", "tmp");
            Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), "UTF-8"));
            char[] buffer = new char[4096];
            int len;
            while ((len = r.read(buffer)) != -1)
                w.write(buffer, 0, len);
            r.close();
            w.flush();
            w.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return out;
    }
}
