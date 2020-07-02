package extension_modules;

public class ClassUtils {
    public static String retrieveExecutedMethod() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        return stackTrace[1].getMethodName();
    }
}
