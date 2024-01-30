package soo.company.comm.files;


import lombok.extern.slf4j.Slf4j;

import java.io.*;

import static java.util.Objects.requireNonNull;

@Slf4j
public class FileProcess {

    public static String fileReadByOffset(String filename) throws IOException {
        File file = null;
        BufferedReader in = null;
        StringWriter out = null;
        String contents = "";
        char[] buf = new char[1024];
        int len = 0;

        try {
            file = new File(filename);

            if (file.exists()) {
                in = new BufferedReader(new FileReader(file));
                out = new StringWriter();

                while ((len = in.read(buf, 0, buf.length)) != -1) {
                    out.write(buf, 0, len);
                }

                contents = out.toString();
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);

        } finally {

            try {
                requireNonNull(in).close();
            } catch (Exception ignored) {
            }
            try {
                requireNonNull(out).close();
            } catch (Exception ignored) {
            }

        }

        return contents;
    }

    public static boolean deletefile(String filename) throws IOException {
        boolean rtn = true;
        File file = null;

        try {
            file = new File(filename);

            if (file.exists()) {
                rtn = false;
            } else {
                if (!file.delete()) {
                    rtn = false;
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            rtn = false;
        }
        return rtn;
    }

    public static boolean fileWrite(String filename, StringBuffer contents) {
        boolean rtn = true;
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            fw.write(contents.toString());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            rtn = false;
        } finally {
            try {
                requireNonNull(fw).close();
            } catch (Exception ignored) {
            }
        }

        return rtn;
    }

    public static boolean fileWrite(String filename, String contents) {
        boolean rtn = true;
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename);
            fw.write(contents);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
            rtn = false;
        } finally {
            try {
                requireNonNull(fw).close();
            } catch (Exception ignored) {
            }
        }

        return rtn;
    }

    public void fileWriteByEncoding(String filename, String contents, String encoding) throws Exception {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(new File(filename), false), encoding));
            bw.write(contents);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw new Exception(e.getMessage());
        } finally {
            try {
                requireNonNull(bw).close();
            } catch (Exception ignored) {
            }
        }
    }

    public StringBuffer fileReadByAll(String fileName) {
        StringBuffer sb = new StringBuffer();
        File fi = new File(fileName);
        BufferedReader in = null;
        FileReader fr = null;

        try {
            if (fi.exists()) {
                fr = new FileReader(fi);
                in = new BufferedReader(fr);

                String aaa = "";

                while ((aaa = in.readLine()) != null) {
                    sb.append(aaa).append("\n");
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        } finally {
            try {
                requireNonNull(in).close();
                fr.close();
            } catch (Exception e1) {
                log.error(e1.getLocalizedMessage(), e1);
            }
        }

        return sb;
    }

    public void appendFile(String fileName, String Contents) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(Contents, 0, Contents.length());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw new Exception(e.getMessage());
        }
    }

    public static void overFile(String fileName, String Contents) throws Exception {
        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(fileName, false));
            bw.write(Contents, 0, Contents.length());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw new Exception(e.getMessage());
        } finally {
            try {
                requireNonNull(bw).close();
            } catch (Exception ignored) {
            }
            ;
        }
    }

}
