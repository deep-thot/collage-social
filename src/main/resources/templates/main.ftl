<!doctype html>
<html ng-app="collageApp">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-resource.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <script src="app.js" ></script>
    <script src="service.js" ></script>
    <script src="list-controller.js" ></script>
</head>
<body>
<#if principal?? && principal.authenticated>
<div ng-controller="ProfileListController">
<ng-view></ng-view>
</div>
<button ng-click="logout()">Logga ut</button>
<#else>
    <a href="/login/google">Logga in med Google</a>
</#if>




</body>
</html>