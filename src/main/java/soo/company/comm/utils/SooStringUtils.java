package soo.company.comm.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SooStringUtils {

    /**
     * 해단 문자가 몇 Byte로 구서왼 UTF-8 코드인지 검사
     *
     * @param
     * @return
     */
    public static int availibleByteNum(char c) {

        // UTF-8은 최대 4바이트를 사용하고 ASCII는 1바이트 그외의 문자들은 2~3바이트 까지 조합하여 사용한다.
        // 즉, 어느 나라 문자이냐에 따라서 몇 바이트를 사용하는지 모르기 때문에 하나의 charater가 몇 바이트 대역에
        // 있는지 조사하여 한문자의 바이트를 조사... 이를 더해 나가면 문자 단위로 몇 바이트를 차지 하는지 정확하게 조사할 수 있다.
        int ONE_BYTE_MIN = 0x0000;
        int ONE_BYTE_MAX = 0x007F;

        int TWO_BYTE_MIN = 0x0800;
        int TWO_BYTE_MAX = 0x07FF;

        int THREE_BYTE_MIN = 0x0800;
        int THREE_BYTE_MAX = 0xFFFF;

        int SURROGATE_MIN = 0x10000;
        int SURROGATE_MAX = 0x10FFFF;

        int digit = (int) c;

        if (ONE_BYTE_MIN <= digit && digit <= ONE_BYTE_MAX) return 1;
        else if (TWO_BYTE_MIN <= digit && digit <= TWO_BYTE_MAX) return 2;
        else if (THREE_BYTE_MIN <= digit && digit <= THREE_BYTE_MAX) return 3;
        else if (SURROGATE_MIN <= digit && digit <= SURROGATE_MAX) return 4;

        return -1;
    }

    /**
     * 문자열 byte 길이 return
     */
    public static int byteLength(String str, String charsetName) throws Exception {
        if (str == null) {
            return 0;
        } else {
            byte[] temp = str.getBytes(charsetName);
            return temp.length;
        }
    }


    /**
     * 문자를 통화단위로 변환한다.
     * 2000000 -> 2,000,000
     */
    static public String getDisplayDigit(String strNum) {
        String displayDigit = "0";
        try {
            displayDigit = getDisplayDigit(new Double(strNum.trim()).longValue());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return displayDigit;
    }

    /**
     * 숫자를 통화단위로 변환한다. 2000000 -> 2,000,000
     */
    static public String getDisplayDigit(long num) {
        String code = "" + num;
        String rcode;
        int i, dat_num;

        if (code.length() == 0) return "0";

        rcode = "";

        dat_num = code.length();

        StringBuilder rcodeBuilder = new StringBuilder(rcode);
        for (i = (dat_num - 1); i >= 0; i--) {

            if ((dat_num - i - 1) != 0) {
                if ((dat_num - i - 1) % 3 == 0) {
                    if (code.charAt(i) != '-' && code.charAt(i + 1) != '.') {
                        rcodeBuilder.insert(0, ",");
                    }
                }
            }
            rcodeBuilder.insert(0, code.charAt(i));
        }
        rcode = rcodeBuilder.toString();

        return rcode;
    }

    /**
     * 사업자번호를 보이기위한 형식으로 변환한다.. "5058106346" -> "505-81-06346"
     */
    static public String getDisplaySaNo(String data) {
        String str = "";
        data = getNullToEmpty(data).trim();

        if (data.length() >= 10) {
            try {
                str = data.substring(0, 3) + "-" + data.substring(3, 5) + "-" + data.substring(5, 10);
            } catch (Exception e) {

                e.printStackTrace();
            }
        } else {
            str = data;
        }
        return str;
    }

    /**
     * String 을 byte 길이 만큼 자르기.
     *
     * @param str 짜르고 싶은 문장
     * @param byteLength 짜르고 싶은 길이
     * @return 일정길이로 짜른 문자열을 반환한다.
     */
    public String cutEucKrString(String str, int byteLength) {
        int length = str.length();
        int retLength = 0;
        int tempSize = 0;
        int asc;
        for (int i = 1; i <= length; i++) {
            asc = (int) str.charAt(i - 1);
            if (asc > 127) {
                if (byteLength >= tempSize + 2) {
                    tempSize += 2;
                    retLength++;
                } else {
                    return str.substring(0, retLength);
                }
            } else {
                if (byteLength > tempSize) {
                    tempSize++;
                    retLength++;
                }
            }
        }
        return str.substring(0, retLength);
    }

    /**
     * UTF-8 문자열 지정한 Byte 길이만큼 자르기
     * <p>
     * UTF-8은 최대 4바이트를 사용하고 ASCII는 1바이트 그외의 문자들은 2~3바이트 까지 조합하여 사용한다.
     * 즉, 어느 나라 문자이냐에 따라서 몇 바이트를 사용하는지 모르기 때문에 하나의 charater가 몇 바이트 대역에
     * 있는지 조사하여 한문자의 바이트를 조사... 이를 더해 나가면 문자 단위로 몇 바이트를 차지 하는지 정확하게 조사할 수 있다.
     */
    public static String cutUTF8String(String str, int maxByteSize) throws Exception {
        //널일 경우에는 그냥 리턴
        if (str == null) return null;

        if (str.length() == 0) return str;

        byte[] strByte = str.getBytes("UTF-8");

        if (strByte.length <= maxByteSize) return str;

        //마지막 줄임말
        int trailByteSize = 0;

        //줄임말의 바이트 수 계산
        //if (trail != null)
        //trailByteSize = trail.getBytes("UTF-8").length;

        //실질적으로 포함되는 최대 바이트 수는 trailByte를 뺀 것이다.
        maxByteSize = maxByteSize - trailByteSize;

        //마지막 바이트 위치
        int endPos = 0;
        //현재까지 조사한 바이트 수
        int currByte = 0;

        for (int i = 0; i < str.length(); i++) {
            //순차적으로 문자들을 가져옴.
            char ch = str.charAt(i);

            //이 문자가 몇 바이트로 구성된 UTF-8 코드인지를 검사하여 currByte에 누적 시킨다.
            currByte = currByte + availibleByteNum(ch);

            //현재까지 조사된 바이트가 maxSize를 넘는다면 이전 단계 까지 누적된 바이트 까지를 유효한 바이트로 간주한다.
            if (currByte > maxByteSize) {
                endPos = currByte - availibleByteNum(ch);
                break;
            }

        }

        //원래 문자열을 바이트로 가져와서 유효한 바이트 까지 배열 복사를 한다.
        byte[] newStrByte = new byte[endPos];

        System.arraycopy(strByte, 0, newStrByte, 0, endPos);

        String newStr = new String(newStrByte, "UTF-8");
        //logger.info(newStr.getBytes("UTF-8").length+ " " + newStr);
        //newStr += trail;

        return newStr;
    }


    /**
     *
     * String이 null일때 ""로 바꾸어준다.
     *
     * @param str 비교할 데이터
     * @param strDefault Null 시 반환 값
     * @return Null 일 경우 Default 값을 반환
     */
    static public String getNullToDefault(String str, String strDefault) {
        if (str == null) {
            str = strDefault;
        } else if (str.trim().equals("null")) {
            str = strDefault;
        } else if (str.trim().equals("NULL")) {
            str = strDefault;
        } else if (str.trim().isEmpty()) {
            str = strDefault;
        }
        return str;
    }

    /**
     * 문자열이 null일때 ""로 바꾸어준다. NullPointerException
     */
    static public String getNullToEmpty(String str) {
        return getNullToEmpty(str, "");
    }

    /**
     * 문자열이 null일때 ""로 바꾸어준다. NullPointerException
     */
    static public String getNullToEmpty(String str, String strDefault) {
        if (str == null)
            str = strDefault;
        return str;
    }

}
