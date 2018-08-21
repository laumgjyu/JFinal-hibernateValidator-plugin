# JFinal Hibernate-Validator Plugin
JFinal的hibernate-validator插件，能够在jfinal中使用hibernate-validator插件的基本功能

## 准备
本插件需要依赖jfinal-java8依赖:
将项目中的jfinal依赖替换为: 
```xml
  <dependency>
      <groupId>com.jfinal</groupId>
      <artifactId>jfinal-java8</artifactId>
      <version>3.4</version>
  </dependency>
```
编译时开启-parameters参数. 详见: [jFinal文档](http://www.jfinal.com/doc/3-3)

## 使用
- 可以将项目根目录下的jFinal-hibernateValidator-plugin.jar添加到自己的本地项目依赖中。  
然后在自己的maven pom.xml中添加如下依赖：
    ```xml
    <dependency>
        <groupId>org.hibernate.validator</groupId>
        <artifactId>hibernate-validator</artifactId>
        <version>6.0.7.Final</version>
    </dependency>
    <dependency>
        <groupId>javax.validation</groupId>
        <artifactId>validation-api</artifactId>
        <version>2.0.1.Final</version>
    </dependency>
    <dependency>
        <groupId>org.jboss.logging</groupId>
        <artifactId>jboss-logging</artifactId>
        <version>3.3.2.Final</version>
    </dependency>
    <dependency>
        <groupId>javax.el</groupId>
        <artifactId>javax.el-api</artifactId>
        <version>3.0.0</version>
    </dependency>
    ```

- 如果使用jetty本地启动, 由于jetty没有实现javax.el, 所以还需要添加如下依赖:
    ```xml
     <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>javax.el</artifactId>
            <version>2.2.4</version>
     </dependency>
    ```

- 在jfinal的配置文件中添加全局拦截器: 
    ```java
      	/**
      	 * 配置全局拦截器
      	 */
      	public void configInterceptor(Interceptors me) {
      		me.addGlobalActionInterceptor(new ParamValidateInterceptor());
      	}
    ```
    
- controller中action添加注解:  
    ```java
        public void test(@Length(min = 3)String a) {
            renderJson(a);
        }
    ```
    可以使用hibernate.validator和javax.validation的注解注解在action的方法参数上  
    ***注意: 发起请求时请求参数的名称需要和方法名对应***
    可以使用的注解包括:  
    
    <table>
      <thead>
      <tr>
        <th>注解</th>
        <th>作用</th>
      </tr>
      </thead>
      <tbody>
      <tr>
        <th>@Valid</th>
        <th>被注释的元素是一个对象，需要检查此对象的所有字段值</th>
      </tr>
      <tr>
        <td>@Null</td>
        <td>被注释的元素必须为 null</td>
      </tr>
      <tr>
        <td>@NotNull</td>
        <td>被注释的元素必须不为 null</td>
      </tr>
      <tr>
        <td>@AssertTrue</td>
        <td>被注释的元素必须为 true</td>
      </tr>
      <tr>
        <td>@AssertFalse</td>
        <td>被注释的元素必须为 false</td>
      </tr>
      <tr>
        <td>@Min(value)</td>
        <td>被注释的元素必须是一个数字，其值必须大于等于指定的最小值</td>
      </tr>
      <tr>
        <td>@Max(value)</td>
        <td>被注释的元素必须是一个数字，其值必须小于等于指定的最大值</td>
      </tr>
      <tr>
        <td>@DecimalMin(value)</td>
        <td>被注释的元素必须是一个数字，其值必须大于等于指定的最小值</td>
      </tr>
      <tr>
        <td>@DecimalMax(value)</td>
        <td>被注释的元素必须是一个数字，其值必须小于等于指定的最大值</td>
      </tr>
      <tr>
        <td>@Size(max, min)</td>
        <td>被注释的元素的大小必须在指定的范围内</td>
      </tr>
      <tr>
        <td>@Digits (integer, fraction)</td>
        <td>被注释的元素必须是一个数字，其值必须在可接受的范围内</td>
      </tr>
      <tr>
        <td>@Past</td>
        <td>被注释的元素必须是一个过去的日期</td>
      </tr>
      <tr>
        <td>@Future</td>
        <td>被注释的元素必须是一个将来的日期</td>
      </tr>
      <tr>
        <td>@Pattern(value)</td>
        <td>被注释的元素必须符合指定的正则表达式</td>
      </tr>
      <tr>
        <td>@Email</td>
        <td>被注释的元素必须是电子邮箱地址</td>
      </tr>
      <tr>
        <td>@Length(min=, max=)</td>
        <td>被注释的字符串的大小必须在指定的范围内</td>
      </tr>
      <tr>
        <td>@NotEmpty</td>
        <td>被注释的字符串的必须非空</td>
      </tr>
      <tr>
        <td>@Range(min=, max=)</td>
        <td>被注释的元素必须在合适的范围内</td>
      </tr>
      </tbody>
    </table>
    
- 自定义对象验证:  
    *需要为自定义接收参数对象的字段生成getter和setter方法*
    ```java
    public class Test {
        public String getTest1() {
            return test1;
        }
    
        public void setTest1(String test1) {
            this.test1 = test1;
        }
    
        @Null
        private String test1;
    }
    ```
    
    action注解,注意此处要使用本插件提供的@ValidParam注解:  
    ```java
    public void test2(@ValidParam Test test) {
        renderJson(test);
    }
    ```
    
    **此处注意请求参数, 请求参数的key应该为test.test1, 即对象名.字段名首字母小写; 如果不想使用这种方式, 可以在添加@Param("")注解,如下**
    ```java
    public void test2(@Para("") @ValidParam Test test) {
        renderJson(test);
    }
    ```
    
    @ValidParam可以和hibernate.validation javax.validation中的注解一起使用:  
    ```java
    public void test2(@Para("") @ValidParam Test test,@Length(min = 3)String a) {
        renderJson(test);
    }
    ```
    
- 嵌套对象的验证:  
    *由于jfinal限制暂时不支持嵌套验证*
    
- 分组验证
分组类
```java
public class RegisterGroup {
}
```

实体Bean
```java
public class UserInfo {

    //默认分组 Default.class
    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11)
    private String phone;

    //默认分组 Default.class
    @NotBlank(message = "用户名不能为空")
    @Length(min = 6, max = 32)
    private String username;

    //默认分组 Default.class
    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32)
    private String password;

    //RegisterGroup分组 RegisterGroup.class
    @Range(min = 18, max = 120, message = "年龄应该在18到120周岁之间", groups = {RegisterGroup.class})
    private int age;

    //RegisterGroup分组 RegisterGroup.class
    @NotBlank(message = "生日不能为空")
    @Past(message = "生日必须大于今天", groups = {RegisterGroup.class})
    private Date birthday;
}
```

验证方法,action注解
```java
    //只校验Default分组, 可以不写
    public void test2(@Para("") @ValidParam(groups={Default.class}) UserInfo userInfo) {
        renderJson(test);
    }
```

```java
    //只校验RegisterGroup分组
    public void test2(@Para("") @ValidParam(groups={RegisterGroup.class}) UserInfo userInfo) {
        renderJson(test);
    }
```

```java
    //校验RegisterGroup分组和Default分组
    public void test2(@Para("") @ValidParam(groups={Default.class,RegisterGroup.class}) UserInfo userInfo) {
        renderJson(test);
    }
```