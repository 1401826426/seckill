package org.seckill.exception;

/**
 * Created by pengfei on 2016/10/17.
 */
public class RepeatKillException  extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
