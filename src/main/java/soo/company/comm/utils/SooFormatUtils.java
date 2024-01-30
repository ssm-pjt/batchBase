package soo.company.comm.utils;

import java.text.DecimalFormat;
import java.util.Random;


/**
 * Format 관련 처리 클래스
 */
public abstract class SooFormatUtils {

    /**
     * 사업자등록번호 XXX-XX-XXXXX 형식으로 변환.
     *
     * @param data - 사업자등록번호(XXXXXXXXXX)
     * @return String - 사업자등록번호(XXX-XX-XXXXX)
     */
    public static String sooFormatBusinessNo(String data) {
        String returnData;
        try {
            if (null == data) {
                return "";
            } else if (data.equals("&nbsp;")) {
                returnData = "&nbsp;";
            } else if (data.length() >= 13) {
                returnData = data.trim().replaceAll("-", "");
                returnData = returnData.substring(0, 6) + "-" + returnData.substring(6, 13);
            } else {
                returnData = data.trim().replaceAll("-", "");
                returnData = returnData.substring(0, 3) + "-" + returnData.substring(3, 5) + "-" + returnData.substring(5);
            }
        } catch (Exception e) {
            returnData = data;
        }
        return returnData;
    }

    /**
     * 휴대폰번호 XXX-XXXX-XXXX 형식으로 변환.
     *
     * @param data - 휴대폰번호(XXXXXXXXXX)
     * @return String - 휴대폰번호(XXX-XXXX-XXXX)
     */
    public static String sooFormatMobile(String data) {
        String returnData;
        try {
            if (null == data) {
                return "";
            }
            returnData = data.replaceAll("-", "").replaceAll("/", "").replaceAll(",", "").replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");
            if (returnData.length() >= 10) {
                if (returnData.length() == 11) {
                    returnData = returnData.substring(0, 3) + "-" + returnData.substring(3, 7) + "-" + returnData.substring(7);
                } else if (returnData.length() == 10) {
                    returnData = returnData.substring(0, 3) + "-" + returnData.substring(3, 6) + "-" + returnData.substring(6);
                }
            }
        } catch (Exception e) {
            returnData = data;
        }
        return returnData;
    }

    /**
     * 전화번호 XXX-XXXX-XXXX 형식으로 변환.
     *
     * @param data - 전화번호(XXXXXXXXXX)
     * @return String - 전화번호(XXX-XXXX-XXXX)
     */
    public static String msooFormatTelephone(String data) {
        String returnData;
        String remainData = "";
        try {
            if (null == data) {
                return "";
            }
            if (data.indexOf("~") != -1) {
                remainData = data.substring(data.indexOf("~"));
                data = data.substring(0, data.indexOf("~"));
            }
            returnData = data.replaceAll("-", "").replaceAll("/", "").replaceAll(",", "").replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");
            if (returnData.length() > 0) {
                if (returnData.substring(0, 1).equals("0")) {
                    if (returnData.substring(0, 2).equals("02")) {
                        returnData = returnData.substring(0, 2) + "-" + returnData.substring(2, returnData.length() - 4) + "-" + returnData.substring(returnData.length() - 4);
                    } else {
                        returnData = returnData.substring(0, 3) + "-" + returnData.substring(3, returnData.length() - 4) + "-" + returnData.substring(returnData.length() - 4);
                    }
                } else {
                    if (returnData.length() > 4) {
                        returnData = returnData.substring(0, returnData.length() - 4) + "-" + returnData.substring(returnData.length() - 4);
                    }
                }
            }
        } catch (Exception e) {
            returnData = data;
        }
        return returnData + remainData;
    }

    /**
     * 우편번호 XXX-XXX 형식으로 변환.
     *
     * @param data - 우편번호(XXXXXX)
     * @return String - 우편번호(XXX-XXX)
     */
    public static String sooFormatPost(String data) {
        String returnData;
        try {
            if (null == data) {
                return "";
            }
            returnData = data.replaceAll("-", "").replaceAll("/", "").replaceAll(",", "").replaceAll("\\.", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll(" ", "");
            if (returnData.length() == 6) {
                returnData = returnData.substring(0, 3) + "-" + returnData.substring(3);
            } else {
                returnData = data;
            }
        } catch (Exception e) {
            returnData = data;
        }
        return returnData;
    }

    /**
     * 숫자 형식으로 변환.
     *
     * @param data - 숫자(XXXXXXXXXX)
     * @return String - 숫자(XXX,XXX,XXX)
     */
    public static String sooFormatNumberInteger(String data) {
        try {
            DecimalFormat df = new DecimalFormat("#,###,###,###,###,###");
            return df.format(data);
        } catch (Exception e) {
            return data;
        }
    }

    /**
     * 숫자 형식으로 변환.
     *
     * @param data - 숫자(XXXXXXXXXX)
     * @return String - 숫자(XXX,XXX,XXX)
     */
    public static String sooFormatNumberDecimal(String data) {
        try {
            DecimalFormat df = new DecimalFormat("#,###,###,###,###,###.##");
            return df.format(data);
        } catch (Exception e) {
            return data;
        }
    }

    /**
     * 숫자 형식으로 변환.
     *
     * @param data - 숫자(XXXXXXXXXX)
     * @return String - 숫자(XXX,XXX,XXX)
     */
    public static String sooFormatNumberLong(long data) {
        String result = String.valueOf(data);
        try {
            DecimalFormat df = new DecimalFormat("#,###,###,###,###,###");
            return df.format(result);
        } catch (Exception e) {
            return result;
        }
    }


    /**
     * 원하는 바이트수만큼 data 변환
     *
     * @param data - 숫자
     * @param size - 숫자
     * @return String - 숫자(XXX,XXX,XXX)
     */
    public static String byteCheckRetrunData(String data, int size) {
        String resultData = "";
        String byteData;
        int byte_length = 0;
        try {
            for (int i = 0; i < data.length(); i++) {
                byteData = String.valueOf(data.charAt(i));

                if (3 == byteData.getBytes().length) {
                    byte_length += byteData.getBytes().length - 1;
                } else {
                    byte_length += byteData.getBytes().length;
                }

                if (byte_length > size) {
                    break;
                } else {
                    resultData += byteData;
                }
            }
            return resultData;
        } catch (Exception e) {
            return data;
        }
    }

    /**
     * 원하는 바이트수만큼 data 변환
     *
     * @return 암호
     */
    public static String makeTemporaryPassword() {
        Random random = new Random();
        String password = "";
        String temp = "0abc1de#f2ghi3jkl@4mno5pqr6stu7vwx!8yz9";
        for (int i = 0; i < 12; i++) {
            password += String.valueOf(temp.charAt(random.nextInt(39)));
        }
        return password;
    }

    /**
     * USD 형식 변환
     *
     * @return 암호
     */
    public static String sooFormatNum_usd(double d) {
        return (new DecimalFormat("#,###.0###")).format(d);
    }

    /**
     * String -> Double
     *
     * @return 암호
     */
    public static double stringParseDouble(String s) {
        if (s == null || s.equals("") || s == "null")
            s = "0";
        return Double.parseDouble(s);
    }

    /**
     * 숫자 형식 변환
     *
     * @return 암호
     */
    public static String formatNum(double d) {
        return (new DecimalFormat("###,###,###,###,##0")).format(d);
    }

    /**
     * 숫자 형식 변환 소수점 2째자리
     *
     * @return 암호
     */
    public static String formatNumFloatTwo(String s) {
        if (s == null) {
            return "";
        }
        return (new DecimalFormat("###,###,###,###,###.00")).format(Double.parseDouble(s));
    }

    /**
     * 숫자 형식 변환 소수점 3째자리
     *
     * @return 암호
     */
    public static String formatNumFloatThree(String s) {
        if (s == null) {
            return "";
        }
        return (new DecimalFormat("###,###,###,###,###.000")).format(Double.parseDouble(s));
    }

    // 결재 html 작성시 만들어지는 포맷팅 Type 1
    public static String sooFormatSapNumber(String text) {
        String returnValue = "0";
        try {
            // returnValue = text.replaceAll("", "&nbsp;");

            if (text == null || "".equals(text)) {
                returnValue = "&nbsp;";
            } else {
                returnValue = text;
            }

            if (!returnValue.equals("&nbsp;")) {
                if (returnValue.indexOf("-") > -1) {
                    returnValue = "-" + formatNum(Double.parseDouble(returnValue.replaceAll("-", "")));
                } else {
                    returnValue = formatNum(Double.parseDouble(returnValue));
                }
            }
            if (returnValue.equals("0"))
                returnValue = "&nbsp;";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    // 결재 html 작성시 만들어지는 포맷팅 Type 2
    public static String sooFormatSapNumberFloatTwo(String text) {
        String returnValue = "";

        try {
            // returnValue = text.replaceAll("", "&nbsp;");
            if (text == null || "".equals(SooStringUtils.getNullToEmpty(text))) {
                returnValue = "&nbsp;";
            } else {
                returnValue = text;
            }

            if (!returnValue.equals("&nbsp;")) {
                if ( returnValue.startsWith("-") ) {
                    returnValue = "-" + formatNum(Double.parseDouble(returnValue.replaceAll("-", "")));
                } else {
                    returnValue = formatNumFloatTwo(returnValue);
                }
            }
            if (returnValue.equals(".00"))
                returnValue = "&nbsp;";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    // 결재 html 작성시 만들어지는 포맷팅 Type 3
    public static String sooFormatSapNumberFloatThree(String text) {
        String returnValue = "";

        try {
            // returnValue = text.replaceAll("", "&nbsp;");
            if (text == null || "".equals(text)) {
                returnValue = "&nbsp;";
            } else {
                returnValue = text;
            }

            if (!returnValue.equals("&nbsp;")) {
                if ( returnValue.startsWith("-") ) {
                    returnValue = "-" + formatNum(Double.parseDouble(returnValue.replaceAll("-", "")));
                } else {
                    returnValue = formatNumFloatThree(returnValue);
                }
            }
            if (returnValue.equals(".000"))
                returnValue = "&nbsp;";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }


}
