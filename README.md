# Aop实验


|    修改日期    | 修改者  | 修改描述 |
| :--------: | :--: | :--: |
| 2020-06-01 | 王洪宾  |      |

理论知识，参考[AOP面向切面编程](https://github.com/zytc2009/BigTeam_learning/blob/master/Android%E6%8A%80%E6%9C%AF%E7%82%B9/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F/AOP/AOP%E9%9D%A2%E5%90%91%E5%88%87%E9%9D%A2%E7%BC%96%E7%A8%8B.md)

实践内容：

1.在生命周期方法，增加日志打印（已完成）

2.拦截已有方法，修改返回值（已完成）

3.拦截方法，根据条件选择执行（已完成）

4.通过APT生成统一的数据解析代码（已完成）

   核心是注解处理 

   实验内容：用原生的json解析替换第三方解析，但是又不想对开发产生较大工作量，所以代码结构上做了调整，定义统一解析接口，通过APT实现解析的处理，已测试通过！ 

   实验结果：原生解析确实比fast要快很多，主要原因是因为java反射比较慢



参考文章：

1. [ButterKnife 原理解析](https://www.jianshu.com/p/39fc66aa3297)
2. [秒懂Android注解处理器](https://blog.csdn.net/shusheng0007/article/details/90734159)
3. [自定义注解之运行时注解](https://www.cnblogs.com/a8457013/p/9965551.html)

