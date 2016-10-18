package org.seckill.exception;

/**
 * Created by pengfei on 2016/10/17.
 */
public class SeckillClosedException  extends SeckillException {

    public SeckillClosedException(String message) {
        super(message);
    }

    public SeckillClosedException(String message, Throwable cause) {
        super(message, cause);
    }
}
