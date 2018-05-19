# netty-client
netty-client是在netty的基础上为了简化netty客户端开发而的一套工具，使用netty-client可以很容易的开发出一个netty的客户端程序。
该项目适合于编写netty服务端的测试代码，发送测试包到指定服务端然后得到响应信息或像http协议一样的实时响应的程序。

下面以请求http服务器为例，了解如何使用netty-client开发一个netty客户端程序.
```
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import org.junit.Test;

public class ClientTest {

	private static String host = "www.baidu.com";
	private static Integer port = 80;

	@Test
	public void clientTest() throws Exception {

		//创建客户端
		IClient<HttpRequest, HttpResponse> client = new ClientBuilder<HttpRequest, HttpResponse>(host,port)
				.addHandler(new HttpRequestEncoder())
				.addHandler(new HttpResponseDecoder())
				.builder();

		//创建httprequest并发送
		DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/");
		HttpResponse response = client.execute(request);

		System.out.println(response);
		client.close();
	}
}
```
