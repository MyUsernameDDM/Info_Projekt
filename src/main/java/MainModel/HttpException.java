package MainModel;

import java.io.IOException;
import okhttp3.Response;

public class HttpException extends IOException {
    private final Response response;

    public HttpException(Response response) {
        super(response.message());
        this.response = response;
    }

    public Response response() {
        return response;
    }
}
