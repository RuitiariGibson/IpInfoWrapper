package app;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>
 * This is the class where the main magic happens. Just kidding.
 * This class is responsible for fetching the data and serializing it to {@link ResponseModel} object
 * </p>
 * example
 * <pre class="code"><code class="java">
 *     public class Main {
 *     public static void main(String[] args){
 *      NetworkImpl network=NetworkImpl.getInstance();
 *      network.setLogLevel(LogLevel.BODY);
 *      // you can use {@link Optional}.ifPresentOrElse() to handle instances when the response model is null
 *      network.makeRequest().ifPresent(responseModel -> {
 *          System.out.println(responseModel.getCity());
 *          System.out.println(responseModel.getCountry());
 *          System.out.println(responseModel.getHostname());
 *          System.out.println(responseModel.getIp());
 *          System.out.println(responseModel.getRegion());
 *      });
 *     }
 *     </code></pre>
 */
@Data
public class NetworkImpl {
    private int timeout=60;
    private TimeUnit timeUnit= TimeUnit.SECONDS;
    private LogLevel logLevel=LogLevel.BASIC;

    /*
    Reason why this class should be a singleton is to prevent repetitive network calls from being made due to multiple instance calls creation
    Network call is an expensive affair so we only need to have a single entry point
     */
    // prevent another thread from seeing the half initialized state of this class instance
    private static volatile NetworkImpl instance;
    private NetworkImpl(){
        if (instance!=null){
            // prevent construction of the class instance through reflection
            throw new RuntimeException("use getInstance to get an instance of this class");
        }
    }
    public static NetworkImpl getInstance(){
        if (instance==null){
            // prevent unnecessary synchronization
            synchronized (NetworkImpl.class){
                if (instance==null) instance=new NetworkImpl();
            }
        }
        return instance;
    }
    /**
     * The method to expose / to be used by the client
     * @return We return a response model which can be null thus we wrap it inside an {@link Optional}
     */
    public Optional<ResponseModel> makeRequest(){
        ResponseModel responseModel=null;
        Request request=new Request.Builder()
                .url("https://ipinfo.io")
                .addHeader("Content-Type","application/json")
                .addHeader("Accept","application/json")
                .build();
        // please note the response body should not be accessed in diff thread from the one making the network call
        // also the method call response.body() is a one off call hence once you call it the content held is flushed so you cannot re-call it again
        OkHttpClient client= provideClient();
       try(Response response=client.newCall(request).execute()){
           if (response.isSuccessful() && response.body()!=null){
               Gson gson=new Gson();
               String json=response.body().string();
               Type type=new TypeToken<ResponseModel>(){}.getType();
             responseModel=gson.fromJson(json,type);
           }
       }catch (IOException e){
           // parse the exception accordingly
           if (e instanceof UnknownHostException || e instanceof SocketTimeoutException){
               Logger.getLogger(NetworkImpl.class.getSimpleName()).log(Level.SEVERE,"Unknown host exception occurred. Please check your internet connection and try again");
           }else {
               Logger.getLogger(NetworkImpl.class.getSimpleName()).log(Level.SEVERE,"An unknown error occurred. Please try again later");
           }
           System.exit(1);
       }
       return  Optional.ofNullable(responseModel);
    }

/*
 Provides an instance of OkHttpClient
 */
    private OkHttpClient provideClient() {
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(provideLogLevel());
      return new OkHttpClient.Builder().connectTimeout(getTimeout(), getTimeUnit())
                .readTimeout(60,TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .retryOnConnectionFailure(true)
                .build();

    }
    /*
     Determine the logging level to be used that is it can be either basic/body/headers/none
     */
    private HttpLoggingInterceptor.Level provideLogLevel(){
        HttpLoggingInterceptor.Level level;
        switch (logLevel) {
            case BASIC:
                level = HttpLoggingInterceptor.Level.BASIC;
                break;
            case BODY:
                level = HttpLoggingInterceptor.Level.BODY;
                break;
            case HEADERS:
                level = HttpLoggingInterceptor.Level.HEADERS;

                break;
            default:
                level = HttpLoggingInterceptor.Level.NONE;
                break;
        }
        return level;
    }
}
