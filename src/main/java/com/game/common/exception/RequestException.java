package com.game.common.exception;

import com.game.util.I18nId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 只能在账号线程池和场景线程池抛出该异常
 *
 * @Author：xuxin
 * @Date: 2019/7/17 17:54
 */
public class RequestException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(RequestException.class);
    private int errorCode;

    public RequestException(int i18nId) {
        this.errorCode = i18nId;
    }

    @Override
    public String toString() {
        return "RequestException{" +
                "errorCode=" + errorCode +
                '}';
    }

    public static void throwException(int i18nId) {
        RequestException exception = new RequestException(i18nId);
        if (i18nId == I18nId.ERROR) {
            logger.warn("RequestException:{},{}", exception, new Throwable().getStackTrace()[1]);
        }
        throw exception;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
