package com.fss.mobiletrading.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rule {

    /*
     * Quantity rule
     */
    public static boolean checkQuantityMaxRule(int qtty, int max) {
        if (qtty > max) {
            return false;
        }
        return true;
    }

    public static boolean checkQuantityMaxRule(String tradeplace, int qtty) {
        if (tradeplace != null && tradeplace.matches("01")) {
            if (qtty > 19900) {
                return false;
            }
        }
        return true;
    }

    /*
     * Price rule
     */
    public static boolean checkPriceRule(String tradeplace, String price) {
        BigDecimal gia;
        if (tradeplace == null || price == null) {
            return false;
        }

        try {
            gia = new BigDecimal(price);
        } catch (Exception e) {
            // TODO: handle exception
            gia = new BigDecimal(0);
        }

        // check bước giá
        if (tradeplace.matches("01")) {
            if (gia.doubleValue() > 0 && gia.doubleValue() <= 49.9) {

            } else {
                if (gia.doubleValue() >= 50.0 && gia.doubleValue() <= 99.5) {
                    if ((price.length() - 1) > 0) {
                        if (price.charAt(price.length() - 1) != '0'
                                && price.charAt(price.length() - 1) != '5') {
                            return false;
                        }
                    }
                } else {
                    if ((price.length() - 1) > 0
                            && price.charAt(price.length() - 1) != '0') {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // check trần sàn
    public static boolean checkPriceCeilFloorRule(String price, String ceiling,
                                                  String floor) {

        BigDecimal gia;
        if (price == null) {
            return true;
        }

        try {
            gia = new BigDecimal(price);
        } catch (Exception e) {
            // TODO: handle exception
            gia = new BigDecimal(0);
        }

        if (ceiling == null || floor == null) {
            return true;
        } else {
            BigDecimal giaTran = null;
            BigDecimal giaSan = null;
            try {
                giaTran = new BigDecimal(ceiling).setScale(1,
                        RoundingMode.HALF_DOWN);
            } catch (Exception e) {
                // TODO: handle exception
            }
            try {
                giaSan = new BigDecimal(floor).setScale(1,
                        RoundingMode.HALF_DOWN);
            } catch (Exception e) {
                // TODO: handle exception
            }

            if (gia.setScale(1).compareTo(giaTran) > 0
                    || gia.setScale(1).compareTo(giaSan) < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkPriceFormat(String price) {

        // check nhập nhiều số 0 ở đầu tiên
        if (price.startsWith("00")) {
            return false;
        }

        // check dấu chấm ở đầu tiên
        if (price.startsWith(".")) {
            return false;
        }

        /*
        * Bo check nhiều số thập phân sau dấu phẩy vi so giao dich ha noi cho phep dat ma ETF vs buoc gia 1
        * */
        // check nhiều số thập phân sau dấu phẩy
//        int dotPosition = price.indexOf(".");
//        if (dotPosition != -1) {
//            if ((price.length() - dotPosition) > 2) {
//                return false;
//            }
//        }

        // check 2 dấu chấm thập phân
        if (price.contains("..")) {
            return false;
        }

        return true;
    }

    public static boolean checkSymbol(String symbol) {
        if (symbol.contains(",,")) {
            return false;
        }
        if (symbol.startsWith(",")) {
            return false;
        }
        return true;
    }
}
