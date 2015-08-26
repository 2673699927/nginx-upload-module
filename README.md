# nginx 上传模块
  * 说明:
    nginx 上传模块，提供大文件上传，断点续传，上传速度控制等功能
    此代码在nginx_upload_module-2.2.0.tar.gz 基础上打补丁 [davromaniak.txt](http://paste.davromaniak.eu/index.php?show=110) 并修复result变量未使用的问题

  * 安装:
    ./configure --add-module={module_dir} && make && make install

  * 配置:
    http://www.grid.net.ru/nginx/upload.en.html

  * 断点续传:
    http://www.grid.net.ru/nginx/resumable_uploads.en.html

  * 使用示例:
    见example文件夹

