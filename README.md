### 																		商户转账接口说明文档



###### 一、使用说明

- 商户到GoodPay注册成为商户，并生成secrtKey。

- 用户在商户平台(社区)对币种UIGI、GoodCoin、GoodCandy进行转入转出接口。

- 本文阅读对象：商户(地球村、GoodEX)等社区平台集成技术架构师，研发工程师，测试工程师，系统运维工程师。

- java版demo下载地址：https://github.com/joinly/demo.git

  

###### 二、调用接口规则

​	商户调用转账接口，调用API必须遵循以下规则：

| 规则     | 说明                                                   |
| :------- | ------------------------------------------------------ |
| 传输方式 | HTTP、有条件可以使用HTTPS                              |
| 提交方式 | 采用POST方法提交                                       |
| 数据格式 | 提交和返回数据都为JSON格式                             |
| 字符编码 | 统一采用UTF-8字符编码                                  |
| 签名算法 | MD5                                                    |
| 签名要求 | 请求和接收数据均需要校验签名，详情参考第三点签名说明。 |



###### 三、签名

- 第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。

  特别注意以下重要规则：

  ◆ 参数名ASCII码从小到大排序（字典序）；

  ◆ 如果参数的值为空不参与签名；

  ◆ 参数名区分大小写；

  ◆ 验证调用返回或GoodPay主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。

  ◆ GoodPay相关转账接口可能增加字段，验证签名时必须支持增加的扩展字段。

- 第二步，在stringA最后拼接上secrtKey得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。

  举例：

  假设传送的参数如下：

  ```java
username："13560443784"
  mchUsername： "15916264616"
  coinType： "UIGI"
  transAmount： 8888
  body： "地球村转入"
  ```
  
  1. 对参数按照key=value的格式，并按照参数名ASCII字典序排序如下：

     ```java
   stringA="username=13560443784&mchUsername=15916264616&coinType=UIGI&transAmount=8888&body=地球村转入"; 
     ```
  
     

  2. 拼接API密钥：

     ```java
   stringSignTemp=stringA+"&secrtKey=s77R/2k4dOiWDYOlCp8XHw==" //注：secrtKey为商户平台设置的密钥key
     sign=MD5(stringSignTemp).toUpperCase()="D6A223DAC09770E2E158EE1417BC2908" //注：MD5签名方式
     ```
  
      

  3. 转成json串

     ```json
   {"username":"13560443784","mchUsername":"15916264616","coinType":"UIGI","transAmount":888,"body":"地球村转入","sign":"D6A223DAC09770E2E158EE1417BC2908"}
     ```



######  四、资金转入转出接口

1.  接口地址：
   转入：http://xxxxxx.com/api/mchTransInApply/into

   转出：http://xxxxx.com/api/mchTransOutApply/out

2.  请求Method：POST

3.  请求Headers：Content-Type ＝ application/json

4. 输入参数

   | 参数        | 类型   | 是否必填 | 最大长度 | 描述                                | 示例值                           |
   | ----------- | ------ | -------- | -------- | ----------------------------------- | -------------------------------- |
   | username    | String | 是       | 20       | 用户名                              | 13560443784                      |
   | mchUsername | String | 是       | 20       | 商户名                              | 15916264616                      |
   | coinType    | String | 是       | 30       | 币种：UIGI、GoodCoin等              | UIGI                             |
   | transAmount | String | 是       | 30       | 转入金额                            | 8888.88                          |
   | wallet      | String | 否       | 30       | 钱包: EARTH、GOODEX                 | EARTH                            |
   | body        | String | 否       | 100      | 转入描述                            | 用户把资金由地球村转入           |
   | requestTime | String | 否       | 30       | 请求时间，格式：yyyy-MM-dd HH:mm:ss | 2020-02-27 10:39:01              |
   | orderNo     | String | 是       | 36       | 社区订单号                          | 215479329277739008               |
   | notifyUrl   | String | 否       | 200      | 结果通知url                         | http://xxxx.com/earth/notify     |
   | sign        | String | 是       | 50       | 签名                                | F933AAB3A2D0F70A40DC716BED694501 |

   

5.  举例

   ```json
   {
   	"username": "13560443784",
   	"mchUsername": "15916264616",
   	"coinType": "UIGI",
   	"transAmount": "8888.88",
   	"wallet": "EARTH",
   	"body": "地球村转入",
   	"orderNo": "202002271039004853",
   	"requestTime": "2020-02-22 17:44:06",
   	"notifyUrl": "http://localhost:8083/earth/notify",
   	"sign": "F933AAB3A2D0F70A40DC716BED694501"
   }
   ```

   

6. 返回结果

   | 参数 | 类型   | 是否必填 | 最大长度 | 描述                                                         | 示例值           |
   | ---- | ------ | -------- | -------- | ------------------------------------------------------------ | ---------------- |
   | code | int    | 是       |          | 状态码: 200表示成功 400表示失败，<br /> 此字段是通信标识，表示接口层的请求结果，并非资金状态 | 200              |
   | msg  | String | 是       | 30       | 提示语                                                       | 商户申请转入成功 |
   | data | String | 否       |          | 返回数据                                                     | 暂无             |

   举例

   ```json
   {
   	"code": 200,
   	"msg": "商户申请转入成功",
   	"data": null
   }
   ```

   

###### 五、goodpay处理结果通知接口

1.  接口地址：由商户提供   

   例：http://localhost:8083/earth/notify

2.  请求Method：POST

3.  请求Headers：Content-Type ＝ application/json

4. 输入参数

   | 参数        | 类型   | 是否必填 | 最大长度 | 描述                                  | 示例值              |
   | ----------- | ------ | -------- | -------- | ------------------------------------- | ------------------- |
   | username    | String | 是       | 20       | 用户名                                | 13560443784         |
   | mchUsername | String | 是       | 20       | 商户名                                | 15916264616         |
   | coinType    | String | 是       | 30       | 币种：UIGI、GoodCoin等                | UIGI                |
   | transAmount | String | 是       | 30       | 转入金额                              | 8888.88             |
   | transTime   | String | 否       | 30       | 转账时间，格式：yyyy-MM-dd HH:mm:ss   | 2020-02-27 10:39:01 |
   | transNo     | String | 否       | 36       | 转账流水号                            | 202002271039004844  |
   | resultCode  | String | 否       | 30       | 转账状态：SUCCESS＝成功，FAIL ＝ 失败 | SUCCESS             |
   | resultMsg   | String | 否       | 200      | 转账错误描述, 只有转账失败才有        | 商户资金不足        |
   | orderNo     | String | 是       | 36       | 社区订单号                            | 202002271039004853  |

   

5.  举例

   ```json
   {
   	"username": "13560443784",
       "mchUsername": "15916264616",
   	"coinType": "UIGI",
   	"transAmount": "888.00",
   	"transTime": "2020-02-27 18:35:03",
   	"transNo": "202002271835039348",
   	"orderNo": "202002271039004853",
   	"sign": "668AC128879F6FFF77FAF3EE91B08E81",
   	"resultCode": "SUCCESS",
   	"resultMsg": ""
   }
   ```

   

6. 返回结果

   | 参数 | 类型   | 是否必填 | 最大长度 | 描述                                                         | 示例值   |
   | ---- | ------ | -------- | -------- | ------------------------------------------------------------ | -------- |
   | code | int    | 是       |          | 状态码: 200表示成功 500表示失败，<br /> 此字段是通信标识，表示接口层的请求结果，并非资金状态 | 200      |
   | msg  | String | 是       | 30       | 提示语                                                       | 接收成功 |
   | data | String | 否       | 30       | 返回接收成功/失败 ; success/fail                             | success  |

   举例

   ```json
   {
   	"code": 200,
   	"msg": "成功",
   	"data": "success"
   }
   ```

   

