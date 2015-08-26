<?php
$url = 'http://dev.upload.com/upload.php';
$filename = "/tmp/upload.jpg";

// 唯一ID
$sessionid =  '1111215056';//uniqid();
$length = filesize($filename);

// 分成3段上传
$chunkSize = intval($length/3);
$fp = fopen($filename, 'r');

// 要上传的文件片段
fseek($fp, $chunkSize);
$posts = fread($fp, $chunkSize);
fclose($fp);

$headers = array(
    "Content-Type: application/octet-stream",
    "Content-Disposition: attachment; name=\"file1\"; filename=\"" .basename($filename). "\"",
    "Content-Length: $chunkSize",
    "X-Content-Range: bytes $chunkSize-".(2*$chunkSize-1)."/$length",
    "Session-ID: " . $sessionid
);

// 登录信息
$cookie = "User=loginUser;";
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_POSTFIELDS,$posts);
curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
curl_setopt($ch, CURLOPT_COOKIE, $cookie);
curl_setopt($ch, CURLOPT_HEADER, 1);
curl_exec($ch);
