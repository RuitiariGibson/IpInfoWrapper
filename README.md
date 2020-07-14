<h1 align="center">IpInfo Wrapper</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>

</p>

<p align="center">  
This is a small simple extensible wrapper around the [IpInfo] (https://ipinfo.io/)  which enables you to get the user's location. The sole purpose of this wrapper
is to eliminate the pain staking process involved to get the your current location using the ipInfo Java libary. And the best part is you do not need an api key to use.
to use it :-).
</p>
</br>

## Example usage
```
<pre class="code"><code class="java">
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
```


## Open-source libraries & language used
- Java jdk 11
- [OkHttp3 Squareup](https://github.com/square/okhttp) - HTTP is the way modern applications network. 
   It’s how we exchange data & media. Doing HTTP efficiently makes your stuff load faster and saves bandwidth.
- [OkHttp3 Logging Interceptor] (https://github.com/square/okhttp) - An OkHttp interceptor which logs HTTP request and response data.
- [Lombok] (https://projectlombok.org/)- Project Lombok is a java library that automatically plugs into your editor and build tools, spicing up your java.
     Never write another getter or equals method again.
- [Gson](https://github.com/square/gson/) - A modern JSON library for Java.


## Find this repository useful? :heart:
Support it by starring this repository. :star: <br>
And __[follow](https://github.com/RuitiariGibson/)__ me for my next creations! 🤩

# License
```xml
Designed and developed by 2020 Gibson Ruitiari (GibsonRuitiari@gmail.com)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
