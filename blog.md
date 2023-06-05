## 画饼

- 修改jwt中的jwt_key，使得像自己的东西
- redis的key如何设置？
- avatar应该存储对象的名字，然后提供名字获取访问连接的接口

## 准备工作

- yml 文件，每个 application 都要对应一个 application.yml

- 修改dependency之后需要reload maven

- 每次修改framework之后都需要maven install

- easycode生成数据库实体类（entity）

  - 配置模板，不需要getset

- mapper

  - interface extends BaseMapper\<Article\>

- service

  大约是提供一些便捷的从class到响应内容的服务

  - interface extends IServer\<Article\>
  - extends ServiceImpl\<ArticleMapper, Article\> implements ArticleService
  - 实现类中@Service
  - 在application中需要scan之（autowired找不到）

- 关闭springsecurity

  在framework中，在maven中reload一下

- 在entity中使用注解告知表名

  - @TableName
  - @TableId表示主键

## 热门文章列表

#### 文章表分析

article.sql

- 使用逻辑删除表示删除
- 为什么status用char，del用int？
- 能不能给status换个名字

#### 设计需求

- 查询浏览量前10的文章的信息

  包括标题、浏览量

  降序展示？

- 点击文章详情进行浏览

- 不展示草稿

- 不展示删除的文章

#### 统一响应格式

基础版本？

- 使用枚举类统一HTTP的响应格式
- 定义响应类（ResposeResult\<T>）统一服务器响应格式
  - 响应类包裹一个数据

java的枚举貌似可以对应一个对象

#### 接口设计

`GET /article/hotArticleList`

#### 实现需求

- controller处理请求
- 使用responseResult封装数据返回

- 在service中实现响应内容的封装
  - interface中声明，implement中实现

#### 跨域问题解决

需要放在framework中

#### 筛选返回内容

返回的内容太多了，我只需要title之类的东西。

使用Bean拷贝Article中需要的内容到ArticleVO里，然后返回封装后的VO。

#### 使用常量类代替值

status列，已发布是0。

## Bean拷贝工具类封装

#### copyBean

将 T 类型拷贝到 U 类型中。copyBean 方法直接返回 U 类型对象。

#### copyBeanList

将 LIst\<U\>转换为 List\<T\>。copyBeanList 方法直接返回 LIst\<T\>对象。

## 分类列表

#### 需求分析

category.sql

- 只展示有正式文章发布的分类
- 只展示启用状态的分类

#### 接口设计

`GET /category/getCategoryList`

#### 实现

- 禁止3张表以下的多表查询？
- 先查询article的表（必须注入articleService），将正式发布的内容转换为list，然后使用stream遍历，用set保存存在的category id。
- 再设置一个querywrapper，将存在于set中的，并且category启用的内容查询出来
- 最后用vo筛选需要的数据

## 按照分类列表查询文章列表

#### 需求

- 分页查询

- 首页中需要查询所有文章
- 分类页面中查询对应分类的文章
- 要求查询正式发布的文章
- 置顶文章显示在最前面

#### 接口设计

`GET /article/articleList`

- 在url获得的参数

  @PathVariable

- 在请求体中获取的参数

  @RequestBody

- @RequestParam

```js
{
    category_id: string,	
    page_num: string,	// required
    page_size: string	// required
}
```

#### 实现

```java
io.blog.service.impl.articleList
```

- 查询article满足category_id的文章
  - 需要处理category_id为null的情况
- 按照isTop降序排序
- 使用Page根据参数分页
- 将articles = page.getRecord() 封装为vo
- 由于articles中只有category_id而没有category_name，需要遍历vo和articles，设置category_name

#### debug：分页功能

Page分页没有作用，需要配置MybatisPlus

#### 引入fastjson

fastjson比默认的json转换要快一点？

在config里面配置json

## 查询文章详情

`GET /article/{id} `

@PathVariable

#### 表分析



#### 实现

- 需要获得分类名字
- 查询可能为null

## 登陆功能

| 请求方式 | 请求路径 |
| -------- | -------- |
| POST     | /login   |

请求体：

~~~~json
{
    "username":"sg",
    "password":"1234"
}
~~~~

响应格式：

~~~~json
{
    "code": 200,
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0ODBmOThmYmJkNmI0NjM0OWUyZjY2NTM0NGNjZWY2NSIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTY0Mzg3NDMxNiwiZXhwIjoxNjQzOTYwNzE2fQ.ldLBUvNIxQCGemkCoMgT_0YsjsWndTg5tqfJb77pabk",
        "userInfo": {
            "avatar": "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi",
            "email": "23412332@qq.com",
            "id": 1,
            "nickName": "sg333",
            "sex": "1"
        }
    },
    "msg": "操作成功"
}
~~~~

#### 流程

登录

​	①自定义登录接口  

​				调用ProviderManager的方法进行认证 如果认证通过生成jwt

​				把用户信息存入redis中

​	②自定义UserDetailsService 

​				在这个实现类中去查询数据库

​	注意配置passwordEncoder为BCryptPasswordEncoder

校验：

​	①定义Jwt认证过滤器

​				获取token

​				解析token获取其中的userid

​				从redis中获取用户信息

​				存入SecurityContextHolder

#### 准备

- maven依赖

  redis, fastjson, jwt

- 配置

- User实体类

- 导入登陆功能的utils

#### 接口设计

`POST /login`

- post的参数在body里面

  @RequestBody

- 前台和后台的登陆不通用

#### 实现

- login，定义LoginService（在framework，主线方法），提供login方法

  注入authenticationManager来认证。

  注入会报错，需要在blog-main中定义SecurityConfig（与后台登陆不兼容），继承WebSecurityConfigurerAdapter，在配置类里注入authenticationManager。在config中override某个方法就可以转换为Bean，重写后加入@Bean注解。

- AuthenticationManager的配置在SecurityConfig，调用authenticate时会使用passwordEncoder来判断密码是否正确。

- 然后调用authenticationManager的方法验证之，此后会返回authentication类的对象，对象中包含UserDetails对象，需要自己实现UserDetailsService（原因是该方法会调用UserDetailsServiceImpl.loadUserByUsername，然后检查密码是否正确kana），该service会返回UserDetails对象。

- 前后台查询同一张表

  实现UserDetailsServiceImpl implement UserDetailsService，放在framework中。

- 还需要实现UserDetails类，将其实现为LoginUser。

- 回到LoginService中，调用authenticationManager.authenticate时会调用UserDetailsServiceImpl类的loadUserByUsername

  据用户名查询用户信息，使用UserMapper的select方法，抛出异常

  实现UserDetails，而后会在serviceImpl中返回给authenticate

- 在LoginService中判断authenticate是否为空（验证失败）

  不为空，用户信息（loginUser）存入redis（redis中设置过期）中（"loginuser:" + userId为key），封装token（jwt，jwt中的Subject是UserId的字符串）返回给前台，token可以在下次用到时解析为userid的字符串。

- 再配置securityConfig

  密码使用BCryptPasswordEncoder加密，猜测authenticate方法是通过这个来判断密码是否正确。
  
  加密，跨域

### 登陆认证过滤器

过滤器的作用是拦截每一次请求，在请求里面获取必要的信息（从请求体的token提取出userid，用userid从redis里取出登陆状态（登陆的话能取出LoginUser）放在SecurityContextHolder的context里），经过拦截器后，请求最终会到达Controller层。

前后台登陆认证操作不一样，放到main中。

登陆校验，前端会保存token，前端会将token放到请求头，然后请求之。

#### 流程

获取token，解析token，从redis中获取用户信息，存入SecurityContextHolder，于是可以使用getContext获取用户信息。

#### 新建filter包

@Component，继承OncePerRequestFilter，重写doFilterInternal

- 在请求头中获得token
  - 可能没有token，此时无需在拦截器中获得token
  - 若确实需要权限，请求会在Controller层中返回403 Forbidden
- 将token解析为claims（claims是userId）
  - 解析可能失败（claims为空）
  - 需要重新登陆（响应一个需要登陆的错误）
- 根据userId在redis里获取用户信息
- 将authenticationToken存入SecurityContextHolder（可能每一次请求之间不会串线）
- 调用filterChain.doFilter(request, response)，放行请求直接进入到Controller层
  - 若没有调用该方法，则会在filter中直接使用response响应请求，此时请求不会进入到Controller

#### 将filter配置到springsecurity中

```java
http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
```

#### 测试

将某个接口设置为需要权限。

```java
http.antMatchers("/article/list").authenticated();
```

### 认证异常处理

认证失败在前后台的处理方式一致

framework中，handler.security.AuthenticationEntryPointImpl 实现AuthenticationEntryPoint 重写commence以处理登陆失败。handler.security.AccessDeniedHandlerImpl 实现 AceessDeniedHandler 重写 handle 以处理授权失败处理器。

- 在SecurityConfig中配置处理器
- 没有携带token属于认证部分的异常处理，可以在EntryPoint中实现区分
  - 判断异常类型 instanceof，分别处理之

## 统一异常

BlogException继承RuntimeException

## 退出登陆

| 请求方式 | 请求地址 | 请求头          |
| -------- | -------- | --------------- |
| POST     | /logout  | 需要token请求头 |

需要token的原因是什么？需要根据token知道logout的用户是谁，然后在redis中找到对应的并删除之。

FIXME：如果没有在头部设置token的话会抛出异常

### 实现

删除redis中的用户信息。

- 由于存入redis的是User而非LoginUser，并且context的token是根据user生成的，所以getContext时需要注意get到的对象是User。

- 现将存入redis的数据更改为LoginUser

- 没有登陆执行logout并且携带token头的话会抛出登陆过期异常

- 没有登陆且没有携带token头调用logout会抛出异常，原因是不携带token会使得过滤器不获取token，进而不设置context，此时principal可能是一个表示匿名账户的String，故会出现转换异常的错误。

  解决办法是给logout方法设置权限（token是权限凭证）。

- 前台如果发现在携带token去logout时得到登陆过期的异常，应该直接回到未登陆状态

## 评论查询接口

### 表分析

- root_id表示该评论的根评论，-1则没有根评论？
- article_id表示评论在哪里
- type评论类型
- to_comment_user_id表示回复给谁，如果普通评论则-1
- to_comment_id表示回复哪条评论，如果普通评论则-1

### 接口设计

| 请求方式 | 请求地址             | 请求头            |
| -------- | -------------------- | ----------------- |
| GET      | /comment/commentList | 不需要token请求头 |

json格式请求参数：

articleId:文章id

pageNum: 页码

pageSize: 每页条数

响应格式为json:

~~~~json
{
    "code": 200,
    "data": {
        "rows": [
            {
                "articleId": "1",
                "children": [
                    {
                        "articleId": "1",
                        "content": "你说啥？",
                        "createBy": "1",
                        "createTime": "2022-01-30 10:06:21",
                        "id": "20",
                        "rootId": "1",	
                        "toCommentId": "1",
                        "toCommentUserId": "1",
                        "toCommentUserName": "sg333",
                        "username": "sg333"
                    }
                ],
                "content": "asS",
                "createBy": "1",
                "createTime": "2022-01-29 07:59:22",
                "id": "1",
                "rootId": "-1",
                "toCommentId": "-1",
                "toCommentUserId": "-1",
                "username": "sg333"
            }
        ],
        "total": "15"
    },
    "msg": "操作成功"
}
~~~~

### 实现根评论查询

- 不考虑子评论
- 按照时间升序

### 实现子评论查询

```java
class CommentVo {
    List<CommentVo> children;
}
```

- 查询的时候应该按照时间升序排序
- 根据toCommentUserId查询回复给谁

## 评论接口

首次接受用户的数据并发送到数据库。

### 接口

带token，表示评论的人，必须要登录后才能回复。

`POST,json /comment`

回复文章

```json
{
    "articleId": 1,
    "type": 0,
    "rootId": -1,
    "toCommentId": -1,
    "toCommentUserId": -1,
    "content": "wahaha"
}
```

回复评论

```json
{
    "articleId": 1,
    "type": 0,
    "rootId": 2,	// diff
    "toCommentId": -1,
    "toCommentUserId": -1,
    "content": "wahaha"
}
```

### 实现

- 需要封装获取token并解析的方法，返回token的用户id
- 公共字段填充（createTime、updateTime之类的），使用Mybatis（在handler中implements MetaObjectHandler，每次插入的时候都会调用这些方法）
  - 用注解表示字段在什么情况下需要自动填充 （在entity中）
- 评论内容不能为空（前后端都要判断）

### bug

- 某些条件下不能发评论，薏米挖干奶

## 个人信息查询

个人信息可以区分为展示给任何人的，以及只能展示给自己的？

### 接口

| 请求方式 | 请求地址       | 请求头          |
| -------- | -------------- | --------------- |
| GET      | /user/userInfo | 需要token请求头 |

不需要参数

响应格式:

```json
{
	"code":200,
	"data": { 
		"avatar": "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F3bf9c263bc0f2ac5c3a7feb9e218d07475573ec8.gi",
		"email":"23412332@qq.com",
		"id":"1",
		"nickname":"sg333",
		"gender":"1"
	},
	"msg":"操作成功"
}
```

### 实现

- 如果Service类中extends IService\<\>，则ServiceImpl中必须extends ServiceImpl\<entityMapper, entity\>，并且要@Service

- 需要在SecurityConfig中限制userInfo的访问，使得必须带token

## 头像上传

### 华为云obs的使用

```java
@SpringBootTest(classes = ObsTest.class)
public class ObsTest {
    @Test
    public void test()
    {
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        try {
            InputStream is = new FileInputStream("/home/clay/Desktop/89796924_p0.png");
            obsClient.putObject(bucketName, "objectname", is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Value("${huaweicloud-obs.end-point}")
    private String endPoint;
    @Value("${huaweicloud-obs.access-key}")
    private String ak;
    @Value("${huaweicloud-obs.secret-key}")
    private String sk;
    @Value("${huaweicloud-obs.bucket-name}")
    private String bucketName;
}

```



### 接口设计

| 请求方式 | 请求地址      | 请求头    |
| -------- | ------------- | --------- |
| POST     | /uploadAvatar | 需要token |

参数：

​	img,值为要上传的文件

请求头：

​	Content-Type ：multipart/form-data;

响应格式:

~~~~json
{
    "code": 200,
    "data": "文件访问链接",
    "msg": "操作成功"
}
~~~~

### 实现

- 注意文件类型判断
- 先上传，再获取url

## 用户信息更新

### 接口

| 请求方式 | 请求地址       | 请求头          |
| -------- | -------------- | --------------- |
| PUT      | /user/userInfo | 需要token请求头 |

参数

请求体中json格式数据：

~~~~json
{
    "avatar":"https://sg-blog-oss.oss-cn-beijing.aliyuncs.com/2022/01/31/948597e164614902ab1662ba8452e106.png",
    "email":"23412332@qq.com",
    "id":"1",
    "nickName":"sg333",
    "sex":"1"
}
~~~~

考虑修改前端，根据avatar来获取图片url

响应格式:

~~~~json
{
	"code":200,
	"msg":"操作成功"
}
~~~~

