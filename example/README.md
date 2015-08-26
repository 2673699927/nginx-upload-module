# 使用示例

nginx 配置 upload.conf

处理上传文件的后端 upload.php

命令行测试上传
curl -v --form file=@/tmp/upload.jpg http://dev.upload.com/upload.php

PHP测试断点续传
分别执行test1.php test2.php test3.php
如果测试不过，可根据nginx日志查看出错原因
