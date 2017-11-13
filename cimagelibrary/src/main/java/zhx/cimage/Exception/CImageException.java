package zhx.cimage.exception;

/**
 * Created by zhouxue on 2017/4/20.
 * QQ 515278502
 */


public class CImageException {


    private final FailType type;

    private final Throwable cause;

    public CImageException(FailType type, Throwable cause) {
        this.type = type;
        this.cause = cause;
    }

    /** @return {@linkplain FailType Fail type} */
    public FailType getType() {
        return type;
    }

    /** @return Thrown exception/error, can be <b>null</b> */
    public Throwable getCause() {
        return cause;
    }

    public static void throwNullValus() {
        throw new RuntimeException("bitmap can not be null");
    }

    /** Presents type of fail while image loading */
    public static enum FailType {
        /** Input/output error. Can be caused by network communication fail or error while caching image on file system. */
        IO_ERROR,
        /**
         * Error while
         * {@linkplain android.graphics.BitmapFactory#decodeStream(java.io.InputStream, android.graphics.Rect, android.graphics.BitmapFactory.Options)
         * decode image to Bitmap}
         */
        DECODING_ERROR,
        /**
         *  and requested image wasn't cached in disk cache before.
         */
        NETWORK_DENIED,
        /** Not enough memory to create needed Bitmap for image */
        OUT_OF_MEMORY,
        /** Unknown error was occurred while loading image */
        UNKNOWN
    }
    public static void throwNullKey(){
        throw  new RuntimeException("key/url can not be null");
    };
}
