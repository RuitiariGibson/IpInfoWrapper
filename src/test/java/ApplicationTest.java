import app.LogLevel;
import app.NetworkImpl;
import app.ResponseModel;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
@RunWith(MockitoJUnitRunner.class)
public class ApplicationTest {

    @InjectMocks
    NetworkImpl network;
    @Test(expected = AssertionError.class)
    public void networkPropertiesTest(){
        network.setLogLevel(LogLevel.BASIC);
        assertEquals(LogLevel.BODY,network.getLogLevel());
        network.setTimeout(60);
        assertEquals(50,network.getTimeout());
        network.setTimeUnit(TimeUnit.MINUTES);
        assertEquals(TimeUnit.SECONDS,network.getTimeUnit());
    }

}
