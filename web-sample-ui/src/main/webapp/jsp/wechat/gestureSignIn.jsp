<%@ include file="/inc/resource.inc" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <title>H5lock</title>
    <style type="text/css"> 
        .title {
            color: #000000;
        }
        
        body {
        	text-align: center;
		    background-image: url(../images/html5/bg.png);
		    z-index: 0; 
		}
    </style>
</head>
<body>
<script type="text/javascript" src="${js}/html5/H5sign.js"></script>
<script type="text/javascript">
    new H5lock({chooseType: 3}).init();
</script>
</body>
</html>