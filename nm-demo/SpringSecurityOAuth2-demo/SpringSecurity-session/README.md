# SpringSecurity-session

在`SpringSecurity-demo`基础上添加`session`管理。因为我们想实现登录场景为两种，一种是`PC`端，一种是`APP`端。但是`APP`端登录，可以长期有效，只要不退出或者不换手机，或者指定一个有效时间，但是这个时间一定比`PC`端有效时间长的多。一般`PC`端只保持 30分钟有效，而`APP`最起码也是以天或者月为单位的。

当然了，不用session管理也可以，直接给token设置一个过期时间，每次访问都去设置`token`过期时间。这样也能实现，但是引入`session`这个概念，或许效果会更好，`session`我们不采用单机存储，我们采用Redis存储。

这里的`session`主要是针对`PC`端设计。

