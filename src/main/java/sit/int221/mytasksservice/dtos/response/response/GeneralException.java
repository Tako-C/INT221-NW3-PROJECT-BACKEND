package sit.int221.mytasksservice.dtos.response.response;

public class GeneralException extends RuntimeException {
//    public GeneralException(String meesage) {
//        super(meesage);
//    }
    public synchronized Throwable fillInStackThis() {
        return this;
    }
}
