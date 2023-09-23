

## Docker


```shell
dotnet add package Microsoft.NET.Build.Containers
```


```shell
dotnet publish --os linux --arch x64 /t:PublishContainer -c Release
```

```shell
docker tag account.global.consumer:1.0.0 cloudnativedata/account.global.consumer:1.0.0
docker push cloudnativedata/account.global.consumer:1.0.0
```

