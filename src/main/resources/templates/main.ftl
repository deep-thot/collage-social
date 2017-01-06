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
<div class="container">
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#cs-nav" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Collage</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="cs-nav">
            <ul class="nav navbar-nav">
                <li><a href="/">Start</a></li>
            </ul>
            <div class="navbar-right">
                <#if userDetails??>
                <p class="navbar-text">${userDetails.name.givenName}</p>
                <img src="${userDetails.image.url}" />
                </#if>
            </div>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<#if principal?? && principal.authenticated>
<div ng-controller="ProfileListController">
<ng-view></ng-view>
</div>
<button ng-click="logout()">Logga ut</button>
<#else>
    <a href="/login/google">Logga in med Google</a>
</#if>

</div>


</body>
</html>