 #### No content to map to Object due to end of input
 最后查明问题的原因，有请求调用该接口时没有提供JSON参数，参数部分完全是空的；

 如果提供的参数有值，但不是JSON接口格式，则不会报这个错误。