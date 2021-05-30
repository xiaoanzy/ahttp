package com.jhxaa.http.util;

import java.io.*;

public class IoUtil {


    public static void colse(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void colse(Closeable... closeables) {
        if (ObjectUtil.isNotEmpty(closeables)) {
            try {
                for (Closeable closeable : closeables) {
                    if (closeable != null) {
                        closeable.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readLineStringStream(InputStream inputStream) {
        return readLineStringStream(inputStream, true);
    }

    public static String readLineStringStream(InputStream inputStream, boolean isClose) {
        BufferedReader bufferedReader = null;
        StringBuilder builder = new StringBuilder();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StringUtil.ENCODING_UTF_8));
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                builder.append(temp);
//                    sbf.append("\r\n");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            if (isClose) {
//                IoUtil.colse(inputStream, bufferedReader);
//            } else {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            }
        }
        return builder.toString();
    }

    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        return inputStreamToByteArray(inputStream, true);
    }

    public static byte[] inputStreamToByteArray(InputStream inputStream, boolean isClose) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int by = 0;
        try {
            while (((by = inputStream.read()) != -1)) {
                byteArrayOutputStream.write(by);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isClose) {
                IoUtil.colse(inputStream, byteArrayOutputStream);
            } else {
                IoUtil.colse(byteArrayOutputStream);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
//    public void close() {
//        if (EmptyUtil.isNotEmpty(is)) {
//            try {
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (EmptyUtil.isNotEmpty(br)) {
//            try {
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (EmptyUtil.isNotEmpty(os)) {
//            try {
//                os.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        if (EmptyUtil.isNotEmpty(connection)) {
//            connection.disconnect();
//        }
//    }

}
