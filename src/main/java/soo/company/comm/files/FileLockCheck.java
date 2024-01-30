package soo.company.comm.files;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

@Slf4j
public class FileLockCheck {

    public FileLockCheck() {
    }

    public boolean FileLckChk(String fileName) {
        FileChannel channel = null;
        FileLock lock = null;

        try {
            File file = new File(fileName);
            channel = new RandomAccessFile(file, "rw").getChannel();

            // lock 획득할때까지 wait (블록) : channel.lock();
            // lock 여부 확인 후 바로 리턴 (비블록) : channel.tryLock();
            lock = channel.tryLock(0L, Long.MAX_VALUE, false);

            if (lock == null) {
                log.info("File lock setting - FALSE");
                return false;
            } else {
                log.info("File lock setting - TRUE");
                return true;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return false;
        }
    }

    public void DaemonLockCheck(String fileName) {
        boolean LockChk = false;
        String LCKFile = "";
        try {
            LCKFile = fileName;
            LockChk = FileLckChk(LCKFile);

            if (!LockChk) {
                // Logger로 LOCK으로 인한 종료임을 기록.
                log.info("Already process start!.\n" + fileName + " file already lock setting.");
                System.exit(0);
            }

        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            System.exit(0);
        }
    }
}
