
## Docker

```shell
dotnet publish --os linux --arch x64 /t:PublishContainer -c Release
```

```shell
docker tag account.global.service:1.0.0 cloudnativedata/account.global.service:1.0.0
docker push cloudnativedata/account.global.service:1.0.0
```
