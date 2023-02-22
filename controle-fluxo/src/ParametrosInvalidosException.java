public class ParametrosInvalidosException  extends Exception{
    public ParametrosInvalidosException(String errorMessage, Throwable throwable){
        super(errorMessage,throwable);
    }
}
