<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
</head>
<body>
    <p>Dear ${firstName} ${lastName},</p>
    <p>Thank you for registering. </p>
    <p>However, in order to activate your account, we need you to click on the link below and confirm this email.</p>
    <p>${messageBody}</p>
    <P><a href=${url}> Confirm my email </a></p>
    <p>The above link will expire in ${expiration} ${temporal}.</p>
</body>
</html>