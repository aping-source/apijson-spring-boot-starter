# Spring Boot Starters for [APIJSON](https://gitee.com/Tencent/APIJSON/)

## 有效的包

- `apijson-spring-boot-starter`

## 使用 Gradle
```gradle
dependencies {
    compile "io.yunjiao:apijson-spring-boot-starter:$version"
}
```
## 使用 Maven
```xml
<dependency>
    <groupId>io.yunjiao</groupId>
    <artifactId>apijson-spring-boot-starter</artifactId>
    <version>${version}</version>
</dependency>
```
详细信息参考`apijson-spring-boot-examples`项目

## FAQ

```text
原因：
字符 getMethodArguments(methodArgs) 对应的远程函数 getMethodArguments(JSONMap request, String methodArgs) 不在后端 io.yunjiao.apijson.example.seed.fastjson2.CustomFastjson2FunctionParser 内，也不在父类中！如果需要则先新增对应方法！
请检查函数名和参数数量是否与已定义的函数一致！
```
在`function`表中，找到字段`name`是`getMethodArguments`，`getMethodDefinition`，`getMethodRequest`的记录，删除后在启动应用

```text
原因：
远程函数 getCurrentUserId 的实际返回值类型 Serializable 与 Function 表中的配置的 Long 不匹配！
```

在`function`表中，找到字段`name`是`getCurrentUserId`的记录，将字段`returnType`修改成`Serializable`，修改后在启动应用
